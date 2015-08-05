package com.foliofn.mvc.mfa.controllers;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mkyong.form.model.User;

@Controller
public class LoginController {
    public static final String ACCOUNT_ATTRIBUTE = "account";

	
	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	public String showLogin( )     {
		
		return "login";
	}
	
    @RequestMapping(method = RequestMethod.POST)
    public String handleLogin(@RequestParam String username, @RequestParam String password,
            RedirectAttributes redirect, HttpSession session) throws AuthenticationException {
        try {
            User account = new User();
            account.setName(username);
            account.setPassword(password);
            
            session.setAttribute(ACCOUNT_ATTRIBUTE, account);
            return "forward:/memberlist.do";
        } catch (Exception ae) {
            redirect.addFlashAttribute("exception", ae);
            return "redirect:/login";
        }
    }	
	
}
