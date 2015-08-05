package com.springinpractice.ch03.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springinpractice.ch03.model.Member;

@Controller
public class MemberSearchController {

	@RequestMapping(value = "/member/search", method = RequestMethod.GET)
	public String list(Model model, Member member, HttpServletRequest request
			 ,	@RequestParam String lastName, @RequestParam int age) {

		Member criteria = new Member();
		
//		String lastName = request.getParameter("lastName");
//		criteria.setLastName(lastName);
//		Member t = new Member(lastName, lastName);
		
		model.addAttribute("memberList", member);

		return "roster/memberSearch";
	}
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String showLogin( )     {
		
		return "roster/memberSearch";
	}
}
