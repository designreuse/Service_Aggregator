package com.imp.saas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "REGION")
public class Region {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment") 
	@Column(name = "REGION_ID")
	private Long regionId;

	@Column(name = "REGION_NAME")
	private String regionName;

	@Column(name = "START_ZIPCODE")
	private Long startZipCode;

	@Column(name = "END_ZIPCODE")
	private Long endZipCode;

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
	 * @return the reasonName
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * @param reasonName
	 *            the reasonName to set
	 */
	public void setRegionName(String reasonName) {
		this.regionName = reasonName;
	}

	/**
	 * @return the startZipCode
	 */
	public Long getStartZipCode() {
		return startZipCode;
	}

	/**
	 * @param startZipCode
	 *            the startZipCode to set
	 */
	public void setStartZipCode(Long startZipCode) {
		this.startZipCode = startZipCode;
	}

	/**
	 * @return the endZipCode
	 */
	public Long getEndZipCode() {
		return endZipCode;
	}

	/**
	 * @param endZipCode
	 *            the endZipCode to set
	 */
	public void setEndZipCode(Long endZipCode) {
		this.endZipCode = endZipCode;
	}

}
