package com.mssyeung;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHelper {

	public static final String MFA_REMEMBER_ME_COOKIE = "mfaRememberMe";

	public static boolean viewCookier(HttpServletRequest req,
			HttpServletResponse rep, String name) {
		String uuid = null;

		boolean exist = false;
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				System.out.println("cookie: " + cookie.getName() + ": "
						+ cookie.getValue() + " : " + cookie.getDomain());
				
				if (!exist && cookie.getName().equals(name)) {
					exist = true;
				}
			}
		}  //
		
		return exist;
	}

	public static void attachCookie(HttpServletResponse rep, String name, String val) {
		Cookie cookie = new Cookie(name, val);
		cookie.setMaxAge(60 * 60 * 24 * 31); // make it persist longer at client
		// side.
		 cookie.setPath("/spring-mvc-form/roster");
		// cookie.setDomain("localhost");
		rep.addCookie(cookie);
		System.out.println("cookie "+name+" added");
		
	}
	public static void attachCookie(HttpServletResponse rep, String name) {
		String uuid = UUID.randomUUID().toString();
		attachCookie(rep, name, uuid);
		
	}

}