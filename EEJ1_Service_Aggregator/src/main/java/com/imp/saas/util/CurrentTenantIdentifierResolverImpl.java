package com.imp.saas.util;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Class responsible to identify the current tenant ,Current tenant is
 * identified from the URL if there is no tenant then default tenant is set
 * 
 * @author rakesh.singhania
 */
@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver
{

  private static final String DEFAULT_TENANT_ID = "ServicePlatform";

  @Override
  public String resolveCurrentTenantIdentifier()
  {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    if (requestAttributes != null)
    {
      String identifier = (String) requestAttributes.getAttribute("CURRENT_TENANT_IDENTIFIER",
        RequestAttributes.SCOPE_REQUEST);
      if (identifier != null)
      {
        return identifier;
      }
    }
    return DEFAULT_TENANT_ID;
  }

  /**
   * CurrentTenantIdentifierResolver implementation returns true for its
   * validateExistingCurrentSessions method, Hibernate will make sure any
   * existing sessions that are found in scope have a matching tenant
   * identifier.
   */
  @Override
  public boolean validateExistingCurrentSessions()
  {
    return true;
  }
}
