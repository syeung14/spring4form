package com.foliofn.mvc.mfa.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springinpractice.ch03.model.Member;


@Controller
public class FormTestController {
	public Logger logger = LoggerFactory.getLogger(FormTestController.class);
	
//	@RequestMapping(value = "/form", method = RequestMethod.GET)
	@RequestMapping(value = "/roster", method = RequestMethod.GET)
	public String showLogin(@ModelAttribute("member") Member criteria )     {
		
		logger.info("form value: " + criteria.getFirstName());
		
		return "roster/springform";
	}
	
}
