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

import com.project.model.Gst;
import com.project.model.Project;
import com.project.model.StartUp;
import com.project.dao.StartUpDao;

@Repository
public class StartUpDaoImple implements StartUpDao {

	@Autowired
	private JdbcTemplate jt;
	
	public void setJt(JdbcTemplate jt) {
		this.jt = jt;
	}

	@Override
	public boolean add(StartUp startup) {
	
			//for date
			/*String date = "'";
			date += startup.getDate();
			date += "'";*/
			//
			
			//for gstId
			//String gstId = startup.getGstId();
			//
	
			
			//setAutoCommit(false);
			String sql = "select * from gst where gst_id = ? and pan =?";
			Gst st = jt.queryForObject(sql,new Object [] {startup.getGstId(),startup.getPan()},new RowMapper<Gst>(){

				@Override
				public Gst mapRow(ResultSet rs, int rowNum) throws SQLException {
					Gst temp = new Gst();
					
					temp.setGstId(rs.getString(1));
					temp.setPan(rs.getString(3));
					return temp;
				}
			
			});
			/**//*
			
			if(st == null)
				return false;*/
			
		System.out.println("here");
			//jdbcTemplate.update(q,new Object[] {student.getRno(),student.getStudentName(),student.getMarks(),student.getJdate()});
			String flag = "yes";
			 sql = "insert into login values(?,?,?)";
			
			int i = jt.update(sql,new Object[] {
					startup.getEmail(),
					startup.getPassword(),
					1
			});
			
			//System.out.println(sql);
			
			sql = "insert into user values(?,?,?,?,?,?)";
			System.out.println(sql);
			int j = jt.update(sql,new Object[]
					{
						startup.getName(),
						startup.getGstId(),
						startup.getContactNo(),
						startup.getEmail(),
						startup.getPassword(),
						flag
					});
			
			sql = "insert into startup(description,no_of_Employee,email,flag)"
					+ " values(?,?,?,?)";
			
			 System.out.println(sql);
			int k = jt.update(sql,new Object[] {
				startup.getDiscription(),
				startup.getNoOfEmployee(),
				startup.getEmail(),
				flag
			});
	
			//for startUpId
			
			
			sql = "select * from startup where email = ?";
			StartUp st1 = jt.queryForObject(sql,new Object[] {startup.getEmail()}, new RowMapper<StartUp>(){

				@Override
				public StartUp mapRow(ResultSet rs, int rowNum) throws SQLException {
					StartUp temp = new StartUp();
					temp.setStartUpId(rs.getInt(5));
					return temp;
				}
			});
			
			if(st1 == null)
				return false;
			
			//System.out.println(st1.toString());
			
			int sid = st1.getStartUpId();
			System.out.println(sid);
			sql = "insert into gst_startup values(?,?,?)";
			int z = jt.update(sql, new Object [] {
				startup.getGstId(),
				sid,
				flag
				
			});

		return true;
	}

	@Override
	public StartUp selectByEmail(String email) {
		
		return null;
	}

	@Override
	public List<Project> selectAll() {
		List<Project> list = new ArrayList<Project>();
		
		String sql= "select * from project";
		list = jt.query(sql, new ResultSetExtractor<List<Project>>(){

			@Override
			public List<Project> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Project> li = new ArrayList<Project>();
				
				while(rs.next())
				{
					Project st = new Project();
					st.setProjectName(rs.getString(2));
					st.setProjetcId(rs.getInt(1));
					li.add(st);
				}
				return li;
			}
	
		});
		
		return list;
	}

}
