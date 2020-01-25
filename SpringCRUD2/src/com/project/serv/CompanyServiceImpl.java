package com.project.serv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.model.Company;
import com.project.model.Funding;
import com.project.model.Login;
import com.project.model.Project;
import com.project.dao.CompanyDao;
import com.project.serv.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	CompanyDao companyDao;
	
	
	public void setCompanydao(CompanyDao companydao) {
		this.companyDao = companydao;
	}

	@Override
	public boolean insert(Company comp) {
		return companyDao.insert(comp);
	}

	@Override
	public boolean compLogin(Company comp) {
		
		return false;
	}

	@Override
	public boolean addProject(Project pro, Login lg) {
		
		return companyDao.addProject(pro,lg);
	}

	
}
