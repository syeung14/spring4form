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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.foliofn.entities.jdbc.repository.MfaLoginDeviceRepository;
import com.foliofn.mvc.mfa.model.DeviceFormModel;
import com.foliofn.mvc.mfa.model.MfaDeviceInfo;


@Controller
public class MfaLoginSettingsController {
	private static final Logger logger = Logger.getLogger(MfaLoginSettingsController.class);	

	
	@Autowired
	MfaLoginDeviceRepository mfaLoginRepo;
	
	@RequestMapping(value = "/mfasettings", method = RequestMethod.GET)
	public ModelAndView displayMfaSettings() {
		logger.debug("display mfa settings ()");
		
		return new ModelAndView("mfa/chg_acc_changeMfaSettingsForm");
		
	}
	@RequestMapping(value = "/mfa", method = RequestMethod.GET)
	public String displayMfa() {
		logger.debug("display mfa settings ()");
		
		MfaDeviceInfo dInfo =new MfaDeviceInfo.Builder(UUID.randomUUID().toString(), "lcarrier").build();
		
		return "/mfa/chg_acc_changeMfaSettingsForm";
		
	}
	@RequestMapping(value = "/mfamodel", method = RequestMethod.GET)
	public String displayMfa(Model model) {
		
		logger.debug("display mfa settings ()");
		List<String> devices = mfaLoginRepo.findAllDeviceByLoginId("lcarrier");
		
		MfaDeviceInfo dInfo =new MfaDeviceInfo.Builder(UUID.randomUUID().toString(), "lcarrier").build();
		
		model.addAttribute("device", devices);
		model.addAttribute("thisDevice", devices.get(0));
		model.addAttribute("cmdData", dInfo);
		
		return "mfa/chg_acc_changeMfaSettingsForm";
	}
	
	@RequestMapping(value = "/mfamodel2", method = RequestMethod.GET)
	public ModelAndView getAdvisorAccountLandingPage(ModelMap model ) {
		return new ModelAndView("/servlets/ProcessAction/mfa/chg_acc_changeMfaSettingsForm");
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createDevice() {
		
		mfaLoginRepo.createDevice("lcarrier", UUID.randomUUID().toString() );
		
		logger.debug("-----created--");
		return "redirect:/mfamodel";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteDevice(HttpServletRequest request,HttpServletResponse response, DeviceFormModel model) {
		String deviceId = model.getThisDeviceId();
		
		logger.debug("showUpdateUserForm() : {}" + deviceId);
		
		mfaLoginRepo.deleteDevice("", deviceId);
		
		return "redirect:/mfamodel";
	}
	
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public String delDevice(HttpServletRequest request,HttpServletResponse response, DeviceFormModel model) {
		
		mfaLoginRepo.deleteDevice("", "");
		
		return "redirect:/mfamodel";
	}
}
