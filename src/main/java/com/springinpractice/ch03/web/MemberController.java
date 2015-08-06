/* 
 * Copyright (c) 2013 Manning Publications Co.
 * 
 * Book: http://manning.com/wheeler/
 * Blog: http://springinpractice.com/
 * Code: https://github.com/springinpractice
 */
package com.springinpractice.ch03.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mssyeung.CookieHelper;
import com.springinpractice.ch03.model.Member;

@Controller
public final class MemberController {
	public Logger logger = LoggerFactory.getLogger(MemberController.class);
	public List<Member> members = new ArrayList<Member>();
	
	
	public MemberController() {
		members.add(new Member("John", "Lennon"));
		members.add(new Member("Paul", "McCartney"));
		members.add(new Member("George", "Harrison"));
		members.add(new Member("Ringo", "Starr"));
	}
	
	
	@RequestMapping(value = "/memberlist.do", method = RequestMethod.POST)
	public String loginHandler() { 
		
		logger.debug("handling member list request");
		
		return "redirect:/list.do";
		
	}
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public String list(HttpServletRequest req, HttpServletResponse rep, Model model) {

		CookieHelper.viewCookier(req, rep,  "mfaRememberMe");
		
		model.addAttribute(members);
		return "roster/list";
	}
	//http://localhost:8080/spring-mvc-form/member.do?id=0
	@RequestMapping(value = "/member.do", method = RequestMethod.GET)
	public String member(@RequestParam("id") Integer id, Model model) {
		model.addAttribute(members.get(id));
		return "roster/member";
	}
}
