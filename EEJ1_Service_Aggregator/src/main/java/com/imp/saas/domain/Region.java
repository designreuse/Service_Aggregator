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
@Table(name = "REGION")
@GenericGenerator(name = "Region", strategy = "org.hibernate.id.enhanced.TableGenerator", parameters = {
		@Parameter(name = "segment_value", value = "REGION"),
		@Parameter(name = "increment_size", value = "10"),
		@Parameter(name = "optimizer", value = "pooled") })
public class Region {
	@Id
	@GeneratedValue(generator = "Region", strategy = GenerationType.IDENTITY)
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
