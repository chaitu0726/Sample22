package com.project.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.project.bean.Login;
import com.project.bean.Project;
import com.project.bean.StartUp;
import com.project.serv.LoginSevice;
import com.project.serv.StartUpService;



@Controller
public class StartUpController 
{
	
	@Autowired
	private StartUpService startUpService;
	
	public void setStartUpService(StartUpService startUpService) {
		this.startUpService = startUpService;
	}

	@Autowired
	private LoginSevice loginService;
	

	public void setLoginService(LoginSevice loginService) {
		this.loginService = loginService;
	}


	public StartUpController() {
		super();
		// TODO Auto-generated constructor stub
	}


	@RequestMapping(value="/add",method = RequestMethod.POST)
	public String save(@ModelAttribute("stp") StartUp stp)
	{
		//System.out.println("hii");
		try {
		if(startUpService.add(stp))
			return "index";
		else 
			return "home";
		}catch(Exception e)
		{
			return "home";
		}
		
	}
	
	
	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String loginGet(HttpSession session)
	{
		System.out.println(session);
		try {
		if(session != null)
			if((int)session.getAttribute("role") == 1)
				return "startup_home";
			else
				return "company_home";
		return "index";
		}catch(Exception e)
		{
			return "index";
		}
	}
	
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("lg") Login lg,HttpSession session)
	{
		ModelAndView model; 
		//System.out.println("hii");
		try {
				Login lgn = loginService.login(lg);
			//
		if(lgn.getFlag() == 1)
		{
			List<Project>list = startUpService.selectAll();
			model = new ModelAndView("startup_home");
			model.addObject("lists",list);
			addUserInSession(lgn, session);
			return model;
		}
		else if(lgn.getFlag() == 2)
		{
			model = new ModelAndView("company_home");
			addUserInSession(lgn, session);
			return model;
		}
		else
		{
			model = new ModelAndView("index");
			return model;
		}
		}catch(Exception e)
		{
			model = new ModelAndView("index");
			return model;
		}
		
	}
	
	@RequestMapping(value="/home_startup",method = RequestMethod.GET)
	public ModelAndView homeGet(HttpSession session)
	{
		ModelAndView model; 
		
		try {
			if(Integer.parseInt(session.getAttribute("role").toString()) == 1)
			{
			List<Project>list = startUpService.selectAll();
			model = new ModelAndView("startup_home");
			model.addObject("lists",list);
			return model;
		}
		else if(Integer.parseInt(session.getAttribute("role").toString()) == 2)
		{
			model = new ModelAndView("company_home");
			return model;
		}
		else
		{
		
			model = new ModelAndView("bidding");
			return model;
		}
		}catch(Exception e)
		{	
			model = new ModelAndView("bidding");
			return model;
		}
		
		
	}
	
	
	private void addUserInSession(Login l,HttpSession session)
	{
		session.setAttribute("uname",l.getUsername());
		session.setAttribute("pass", l.getPassword());
		session.setAttribute("role", l.getFlag());
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session)
	{
		session.invalidate();
		return "index";
	}
}
