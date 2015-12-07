package com.imp.saas.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Provider {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long provider_id;
	private String name;
	private String phone;
	private String email;
	private String region_id;
	private String aggregator_id;
	 private String address_id;
	
	public Provider(
    String name,
    String phone,
    String email,
    String region_id,
    String aggregator_id,
    String address_id)
  {
    super();
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.region_id = region_id;
    this.aggregator_id = aggregator_id;
    this.address_id = address_id;
  }

  @Override
  public String toString()
  {
    return "Provider [provider_id=" + provider_id + ", name=" + name + ", phone=" + phone
      + ", email=" + email + ", region_id=" + region_id + ", aggregator_id=" + aggregator_id
      + ", address_id=" + address_id + "]";
  }



  /**
   * @return the provider_id
   */
  public Long getProvider_id()
  {
    return provider_id;
  }


  /**
   * @param provider_id the provider_id to set
   */
  public void setProvider_id(Long provider_id)
  {
    this.provider_id = provider_id;
  }


  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }


  /**
   * @param name the name to set
   */
  public void setName(String name)
  {
    this.name = name;
  }


  /**
   * @return the phone
   */
  public String getPhone()
  {
    return phone;
  }


  /**
   * @param phone the phone to set
   */
  public void setPhone(String phone)
  {
    this.phone = phone;
  }


  /**
   * @return the email
   */
  public String getEmail()
  {
    return email;
  }


  /**
   * @param email the email to set
   */
  public void setEmail(String email)
  {
    this.email = email;
  }


  /**
   * @return the region_id
   */
  public String getRegion_id()
  {
    return region_id;
  }


  /**
   * @param region_id the region_id to set
   */
  public void setRegion_id(String region_id)
  {
    this.region_id = region_id;
  }


  /**
   * @return the aggregator_id
   */
  public String getAggregator_id()
  {
    return aggregator_id;
  }


  /**
   * @param aggregator_id the aggregator_id to set
   */
  public void setAggregator_id(String aggregator_id)
  {
    this.aggregator_id = aggregator_id;
  }


  /**
   * @return the address_id
   */
  public String getAddress_id()
  {
    return address_id;
  }


  /**
   * @param address_id the address_id to set
   */
  public void setAddress_id(String address_id)
  {
    this.address_id = address_id;
  }
	
	

}
