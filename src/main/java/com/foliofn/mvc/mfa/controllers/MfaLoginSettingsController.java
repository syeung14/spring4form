package com.foliofn.mvc.mfa.controllers;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.foliofn.entities.jdbc.repository.MfaLoginDeviceRepository;
import com.foliofn.mvc.mfa.model.MfaDeviceInfo;
import com.foliofn.verification.entities.VerificationUser;
import com.foliofn.verification.mfa.domain.MfaUserInfo;
import com.foliofn.verification.mfa.domain.UserDeviceInfo;
import com.foliofn.verification.repositories.VerificationUserRepository;
import com.mssyeung.CookieHelper;


@Controller
public class MfaLoginSettingsController {
	private static final Logger logger = Logger.getLogger(MfaLoginSettingsController.class);	
	
	@Autowired
	MfaLoginDeviceRepository mfaLoginRepo;
	
	@Autowired
	private VerificationUserRepository userRepository;
	
	
	@RequestMapping(value ="/db", method= RequestMethod.GET) 
	@SuppressWarnings("unused")
	public String dbTest() {
		
		boolean rest = mfaLoginRepo.isCurrentDeviceActive("874abf8d-ab39-47c6-8ca4-d1da07413ea7", "lcarrier");
		rest = mfaLoginRepo.aresOtherDeviceActive("874abf8d-ab39-47c6-8ca4-d1da07413ea7", "lcarrier");
		
		VerificationUser vUser = userRepository.findByUsername("lcarrier");
		
		return "redirect:/mfasettings";
	}
	
	@RequestMapping(value ="/dbGetCookie", method= RequestMethod.GET) 
	@SuppressWarnings("unused")
	public String dbGetCookie() {
		String deviceId = "7f0bcdd5-e821-4d9c-b83f-808784b45a1a";
				
		UserDeviceInfo uInfo = mfaLoginRepo.getUserLatestCookie("lcarrier", deviceId);
		
		
		return "redirect:/index";
	}
	
	@RequestMapping(value ="/dbGetUser", method= RequestMethod.GET) 
	@SuppressWarnings("unused")
	public String dbGetUser() {
		String deviceId = "7f0bcdd5-e821-4d9c-b83f-808784b45a1a";
		
		MfaUserInfo uInfo = mfaLoginRepo.getUserInfo("lcarrier");
		
		
		return "redirect:/index";
	}
	
	
	@RequestMapping(value = "/mfasettings", method = RequestMethod.GET)
	public ModelAndView displayMfaSettings(Model model) {
		logger.debug("display mfa settings ()");
		
		List<String> otherDevices = mfaLoginRepo.findAllDeviceByLoginId("lcarrier");
		
		if (otherDevices.size() >0) {
			model.addAttribute("otherDevices",otherDevices );
		} else model.addAttribute("otherDevices",null);
		
		model.addAttribute("currentDevice", "xxxxxxxxxxxx");
		
		return new ModelAndView("mfa/chg_acc_changeMfaSettingsForm");
		
	}
	
	@RequestMapping(value = "/forgetDevice", method = RequestMethod.POST)
	public String deleteDevice(/*HttpServletRequest request,HttpServletResponse response,*/ 
			@RequestParam String forgetType) {
		
		
		logger.debug("showUpdateUserForm() : {}" + forgetType);
		if ("forgetAll".equals(forgetType)) {
			mfaLoginRepo.deactivateDevice("lcarrier");
			
		} else if ("forgetThisOne".equals( forgetType)) {
			
		}
		
		return "redirect:/mfasettings";
	}
	
	@RequestMapping(value = "/mfa", method = RequestMethod.GET)
	public String displayMfa(HttpServletRequest request,HttpServletResponse response) {
		logger.debug("display mfa settings ()");
		
		
		MfaDeviceInfo dInfo =new MfaDeviceInfo.Builder(UUID.randomUUID().toString(), "lcarrier").build();
		
		return "/mfa/chg_acc_changeMfaSettingsForm";
		
	}
	@RequestMapping(value = "/mfamodel", method = RequestMethod.GET)
	public String displayMfa(Model model,HttpServletRequest req,HttpServletResponse rep) {
		
		logger.debug("display mfa settings ()");
		List<String> devices = mfaLoginRepo.findAllDeviceByLoginId("lcarrier");
		
		MfaDeviceInfo dInfo =new MfaDeviceInfo.Builder(UUID.randomUUID().toString(), "lcarrier").build();

		CookieHelper.viewCookier(req, rep, "mfaRememberMe");

		
		model.addAttribute("device", devices);
		model.addAttribute("thisDevice", devices.size() > 0? devices.get(0): "nothing");
		model.addAttribute("cmdData", dInfo);
		
		return "mfa/chg_acc_changeMfaSettingsForm";
	}
	
	@RequestMapping(value = "/mfamodel2", method = RequestMethod.GET)
	public String getAdvisorAccountLandingPage(HttpServletRequest req,HttpServletResponse rep, ModelMap model ) {

		if (! CookieHelper.viewCookier(req, rep, "mfaRememberMe"))
			CookieHelper.attachCookie(rep, "mfaRememberMe");
		
		return "redirect:https://localhost:8443/sip/main/roster/list.do";
	}
	@RequestMapping(value = "/sub/mfamodel2", method = RequestMethod.GET)
	public String getSubMfaModel2(HttpServletRequest req,HttpServletResponse rep, ModelMap model ) {
		
		if (! CookieHelper.viewCookier(req, rep, "mfaRememberMe"))
			CookieHelper.attachCookie(rep, "mfaRememberMe");
		
//		return "roster/springform";
//		return "redirect:https://localhost:8443/sip/main/roster/list.do";
		return "redirect:/mfasettings";
	}

	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createDevice(HttpServletResponse response) {
		
		
		String uuid = UUID.randomUUID().toString();
		
		uuid = "2ffd8361-edc6-4abd-ab49-d6bc79695af4";
		mfaLoginRepo.createDevice("lcarrier", uuid );

		
		logger.debug("-----created--");
		return "redirect:https://localhost:8443/sip/main/roster/list.do";
	}
	
}