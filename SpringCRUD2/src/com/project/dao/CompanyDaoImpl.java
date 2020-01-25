package com.project.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.project.model.Company;
import com.project.model.Funding;
import com.project.model.Login;
import com.project.model.Project;
import com.project.dao.CompanyDao;

@Repository
public class CompanyDaoImpl implements CompanyDao{

	@Autowired
	JdbcTemplate jt;
	
	
	public void setJt(JdbcTemplate jt) {
		this.jt = jt;
	}

	@Override
	public boolean insert(Company comp) {
		String sql;
		String flag ="yes";
		
		sql="select * from gst where gst_id=? and pan=?";
		
		Company comp2 = jt.queryForObject(sql, new Object[] {comp.getGstId(),comp.getPan()}, new RowMapper<Company>(){

			@Override
			public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
				Company temp=new Company();
				temp.setGstId(rs.getString(1));
				temp.setPan(rs.getString(3));
				return temp;
			}});
		
		//if(comp2.getGstId()==comp.getPan() && comp.getGstId()==comp.getPan()) {
				
		
		 sql= "insert into login values(?,?,?)";
		
		int a=jt.update(sql, new Object[] { 
				
				comp.getEmail(),
				comp.getPassword(),
				2
		});
		
		System.out.println(a+"inserted into login");
		
		sql= "insert into user values(?,?,?,?,?,?)";
		
		a=jt.update(sql, new Object [] {
				
				comp.getCompName(),
				comp.getGstId(),
				comp.getContactNo(),
				comp.getEmail(),
				comp.getPassword(),
				flag
		});
		
		System.out.println(a+"inserted into user");
		
		
		
		sql="insert into company(description,email,flag) values(?,?,?)";
		
		a=jt.update(sql, new Object [] {
				
				comp.getDescription(),
				comp.getEmail(),
				flag
		});
		
		System.out.println(a+"inserted into company");
		
		
		
		sql="select * from Company where email=?";
		Company comp1 = jt.queryForObject(sql,new Object[] {comp.getEmail()}, new RowMapper<Company>(){

			@Override
			public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
				Company temp = new Company();
				temp.setCompanyId(rs.getInt(3));
				return temp;
			}
		});
		
		//if(comp1 == null);
			//return false;
		System.out.println("selected comp id from table");
		
		int cid=comp1.getCompanyId();
		
		sql="insert into gst_company values(?,?,?)";
		jt.update(sql, new Object[] {
				
				comp.getGstId(),
				cid,
				flag
		});
		
		System.out.println("inserted into gst_company");
		
		return true;
	
	}

	@Override
	public boolean compLogin(Company comp) {
		
		return false;
	}

	@Override
	public boolean addProject(Project pro, Login lg) {
		
		String sql;
		sql="select company_id,flag from company where email=?";
		
		 Project pro1=jt.queryForObject(sql, new Object[] { lg.getUsername() }, new RowMapper<Project>() {

			@Override
			public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				Project temp=new Project();		
				temp.setComapanyId(rs.getInt(1));
				temp.setFlag(rs.getString(2));
				return temp;
			}
		 });
		
		 sql= "insert into project(project_name,project_technology,project_duration,project_description,project_bid_amount,company_id,flag) "
		 		+ "values(?,?,?,?,?,?,?)";
			
			jt.update(sql, new Object [] {
					
					pro.getProjectName(),
					pro.getProjectTechnology(),
					pro.getProjectDuration(),
					pro.getProjectDescription(),
					pro.getProjectBidAmount(),
					pro1.getComapanyId(),
					pro1.getFlag()
			});
		return true;
	}
	

}
