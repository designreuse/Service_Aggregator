package com.imp.saas.web;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.imp.saas.domain.Provider;
import com.imp.saas.repository.ProviderRepository;

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

  @RequestMapping(value = "/addProvider", method = RequestMethod.POST)
  @Transactional
  public String addEmployee(@ModelAttribute
  Provider provider)
  {
    try
    {
      providerRepository.save(provider);
    }
    catch (Exception e)
    {
      return "redirect:/databaseException";
    }
    return "redirect:/{tenantid}";
  }
}
