package com.imp.saas.ws.bean;

/**
 * Bean for fetching DB response
 * @author rakesh.singhania
 *
 */
public class DBResponse {

	private String result;

	public DBResponse(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
