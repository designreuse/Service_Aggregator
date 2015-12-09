package com.imp.saas.web;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imp.saas.repository.ProviderRepository;

/**
 * Controller for CRUD operations it maps tenantid from the URL to identify the
 * correct tenant
 * 
 * @author rakesh.singhania
 */
@Controller
@RequestMapping("/{tenantid}")
public class ProviderController {

	@Autowired
	private ProviderRepository providerRepository;

	@RequestMapping(value = "/addProvider", method = { RequestMethod.GET,
			RequestMethod.POST })
	@Transactional
	@ResponseBody
	public String addProvider(@PathVariable String tenantid, Model model) {
		try {
			model.addAttribute("tenantid", tenantid);
			// providerRepository.save(provider);
		} catch (Exception e) {
			return "Provider not inserted in database";
		}
		return "Provider successfully added in the database";
	}
}
