package com.bashkarsampath.app.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bashkarsampath.app.services.user.CustomOauth2UserCustomRole;

import lombok.var;

@Controller
public class HomeController {

	@ModelAttribute("user")
	private Model user(Model model) {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomOauth2UserCustomRole customOauth2UserCustomRole = (CustomOauth2UserCustomRole) authentication
				.getPrincipal();
		model.addAttribute("role", customOauth2UserCustomRole.getAuthorities().toArray()[0]);
		return model;
	}
	
	@RequestMapping("/home")
	public String getHome(Model model, @ModelAttribute("user") Model user) {
		return "home";
	}

	@RequestMapping("/admin")
	public String getAdminHome(Model model, @ModelAttribute("user") Model user) {
		return "home";
	}

	@RequestMapping("/ceo")
	public String getCeoHome(Model model, @ModelAttribute("user") Model user) {
		return "home";
	}

	@RequestMapping("/manager")
	public String getManagerHome(Model model, @ModelAttribute("user") Model user) {
		return "home";
	}

	@RequestMapping("/supervisor")
	public String getSupervisorHome(Model model, @ModelAttribute("user") Model user) {
		return "home";
	}

	@RequestMapping("/creator")
	public String getCreatorHome(Model model, @ModelAttribute("user") Model user) {
		return "home";
	}

	@RequestMapping("/analyst")
	public String getAnalystHome(Model model, @ModelAttribute("user") Model user) {
		return "home";
	}

	@RequestMapping("/employee")
	public String getEmployeeHome(Model model, @ModelAttribute("user") Model user) {
		return "home";
	}
}
