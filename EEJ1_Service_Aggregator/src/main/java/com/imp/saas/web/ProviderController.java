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
import com.imp.saas.util.JsonDataConstants;

/**
 * Controller for CRUD operations it maps tenantid from the URL to identify the
 * correct tenant
 * 
 * @author rakesh.singhania
 */
@Controller
@RequestMapping("/{tenantid}")
public class ProviderController
{


  @Autowired
  private ProviderRepository providerRepository;

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private RegionRepository regionRepository;

  private static final Logger LOGGER = Logger.getLogger(ProviderController.class);

  /**
   * Method get json object of provider data and add provider details to DB
   * @param inputJson
   * @param tenantid
   * @param model
   * @return
   */
  @RequestMapping(value = "/addProvider", method = {RequestMethod.GET, RequestMethod.POST})
  @Transactional
  @ResponseBody
  @Consumes(MediaType.APPLICATION_JSON)
  public String addProvider(@RequestBody
  String inputJson, @PathVariable
  String tenantid, Model model)
  {
    try
    {
      model.addAttribute("tenantid", tenantid);

      JSONObject jsonObject = new JSONObject(inputJson);

      Address address = new Address();
      address.setCity(jsonObject.getString(JsonDataConstants.CITY));
      address.setCountry(jsonObject.getString(JsonDataConstants.COUNTRY));
      address.setLine1(jsonObject.getString(JsonDataConstants.LINE1));
      address.setLine2(jsonObject.getString(JsonDataConstants.LINE2));
      address.setState(jsonObject.getString(JsonDataConstants.STATE));
      address.setZipCode(jsonObject.getString(JsonDataConstants.ZIP_CODE));

      address = addressRepository.save(address);

      Long regionId = new Long(1);
      Iterable<Region> regionList = regionRepository.findAll();
      for (Iterator<Region> iterator = regionList.iterator(); iterator.hasNext();)
      {
        Region region = iterator.next();
        if (jsonObject.getLong(JsonDataConstants.ZIP_CODE) > region.getStartZipCode()
          && jsonObject.getLong(JsonDataConstants.ZIP_CODE) < region.getEndZipCode())
        {
          regionId = region.getRegionId();
          break;
        }
      }

      //provider bean creation
      Provider provider = new Provider();
      provider.setAddressId(address.getAddressId());
      provider.setEmail(jsonObject.getString(JsonDataConstants.EMAIL));
      provider.setName(jsonObject.getString(JsonDataConstants.NAME));
      provider.setPhone(jsonObject.getString(JsonDataConstants.PHONE));
      provider.setRegionId(regionId);

      //Save Provide details
      provider = providerRepository.save(provider);

      LOGGER.info("provider Id :" + provider.getProviderId());

    }
    catch (Exception e)
    {
      LOGGER.error(JsonDataConstants.PROVIDER_NOT_INSERTED + e.getMessage());
      return "redirect:/databaseException";
    }
    LOGGER.debug(JsonDataConstants.PROVIDER_SUCCESSFULLY_ADDED);

    return JsonDataConstants.PROVIDER_SUCCESSFULLY_ADDED;
  }
}
