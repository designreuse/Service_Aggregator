package com.imp.saas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PROVIDER")
public class Provider {

	@Id
  @GeneratedValue(generator = "increment")
	@Column(name = "PROVIDER_ID")
	private Long providerId;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "PHONE")
	private String phone;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "REGION_ID")
	private Long regionId;
	
	@Column(name = "ADDRESS_ID")
	private Long addressId;

	/**
	 * @return the providerId
	 */
	public Long getProviderId() {
		return providerId;
	}

	/**
	 * @param providerId
	 *            the providerId to set
	 */
	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the regionId
	 */
	public Long getRegionId() {
		return regionId;
	}

	/**
	 * @param regionId
	 *            the regionId to set
	 */
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	/**
	 * @return the addressId
	 */
	public Long getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId
	 *            the addressId to set
	 */
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public Provider() {

	}

	@Override
	public String toString() {
		return "Provider [providerId=" + providerId + ", name=" + name
				+ ", phone=" + phone + ", email=" + email + ", regionId="
				+ regionId + ", addressId=" + addressId + "]";
	}

}
