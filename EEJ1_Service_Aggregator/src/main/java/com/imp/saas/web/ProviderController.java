package com.imp.saas.web;

import java.util.Iterator;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imp.saas.domain.Address;
import com.imp.saas.domain.Provider;
import com.imp.saas.domain.Region;
import com.imp.saas.repository.AddressRepository;
import com.imp.saas.repository.ProviderRepository;
import com.imp.saas.repository.RegionRepository;

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

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private RegionRepository regionRepository;

	//@Autowired
	//private Address address;

	//@Autowired
	//private Region region;
	
	private static final Logger LOGGER = Logger.getLogger(ProviderController.class);

	@RequestMapping(value = "/addProvider", method = { RequestMethod.GET,
			RequestMethod.POST })
	@Transactional
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON)
	public String addProvider(@RequestBody String inputJson,
			@PathVariable String tenantid, Model model) {
		try {
			model.addAttribute("tenantid", tenantid);

			JSONObject jsonObject = new JSONObject(inputJson);

			Address address = new Address();
			address.setCity(jsonObject.getString("city"));
			address.setCountry(jsonObject.getString("country"));
			address.setLine1(jsonObject.getString("line1"));
			address.setLine2(jsonObject.getString("line2"));
			address.setState(jsonObject.getString("state"));
			address.setZipCode(jsonObject.getString("zipCode"));

			address = addressRepository.save(address);

			Long regionId=new Long(1);
			Iterable<Region> regionList = regionRepository.findAll();
			for (Iterator iterator = regionList.iterator(); iterator.hasNext();) {
				Region region = (Region) iterator.next();
				if (jsonObject.getLong("zipCode") > region.getStartZipCode()
						&& jsonObject.getLong("zipCode") < region
								.getEndZipCode()) {
					regionId = region.getRegionId();
					break;
				}
			}
			
			Provider provider = new Provider();
			provider.setAddressId(address.getAddressId());
			provider.setEmail(jsonObject.getString("email"));
			provider.setName(jsonObject.getString("name"));
			provider.setPhone(jsonObject.getString("phone"));
			provider.setRegionId(regionId);
			
			provider = providerRepository.save(provider);
			
			LOGGER.info("provider Id :"+provider.getProviderId());
			
		} catch (Exception e) {
			return "Provider not inserted in database";
		}
		return "Provider successfully added in the database";
	}
}
