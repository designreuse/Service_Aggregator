package com.imp.saas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "ADDRESS")
@GenericGenerator(name = "Address", strategy = "org.hibernate.id.enhanced.TableGenerator", parameters = {
		@Parameter(name = "segment_value", value = "ADDRESS"),
		@Parameter(name = "increment_size", value = "10"),
		@Parameter(name = "optimizer", value = "pooled") })
public class Address {
	@Id
	@GeneratedValue(generator = "Address", strategy = GenerationType.IDENTITY)
	@Column(name = "ADDRESS_ID")
	private Long addressId;

	@Column(name = "LINE1")
	private String line1;
	
	@Column(name = "LINE2")
	private String line2;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "STATE")
	private String state;

	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "ZIP_CODE")
	private String zipCode;

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

	/**
	 * @return the line1
	 */
	public String getLine1() {
		return line1;
	}

	/**
	 * @param line1
	 *            the line1 to set
	 */
	public void setLine1(String line1) {
		this.line1 = line1;
	}

	/**
	 * @return the line2
	 */
	public String getLine2() {
		return line2;
	}

	/**
	 * @param line2
	 *            the line2 to set
	 */
	public void setLine2(String line2) {
		this.line2 = line2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

}
