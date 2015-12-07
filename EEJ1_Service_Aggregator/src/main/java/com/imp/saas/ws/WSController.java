package com.imp.saas.ws;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imp.saas.exception.ConfigExceptions;
import com.imp.saas.exception.DatabaseCustomException;
import com.imp.saas.handler.WSHandler;
import com.imp.saas.ws.bean.DBResponse;
import com.imp.saas.ws.bean.InputData;

/**
 * Class responsible for creating Database at runtime
 * 
 * @author rakesh.singhania
 *
 */
@Controller
@RequestMapping(value = "/webservice")
public class WSController {

	@Autowired
	WSHandler wsHandler;

	private static final Logger LOGGER = Logger.getLogger(WSController.class);

	/**
	 * Create database at runtime it takes DBname,user name,pass as parameter in
	 * input data
	 * 
	 * @param inputData
	 * @return
	 */
	@RequestMapping(value = "createDatabase", method = RequestMethod.POST)
	@ResponseBody
	public DBResponse createDatabase(@RequestBody InputData inputData)
			throws DatabaseCustomException {

		String result = wsHandler.createDatabase(inputData.getDbName(),
				inputData.getDbUserName(), inputData.getDbPassword(),
				inputData.getDbURL());
		return new DBResponse(result);

	}

	/**
	 * Create database at runtime it takes DBname,user name,pass as parameter in
	 * input data
	 * 
	 * @param inputData
	 * @return
	 * @throws ConfigExceptions
	 */
	@RequestMapping(value = "createTenantEntryinPropertyFile", method = RequestMethod.POST)
	@ResponseBody
	public DBResponse createEntryforTenantInPropertyFile(
			@RequestBody InputData inputData) throws ConfigExceptions {

		String result = wsHandler.createEntryforTenantInPropertyFile(
				inputData.getDbName(), inputData.getDbUserName(),
				inputData.getDbPassword(), inputData.getDbHostName(),
				inputData.getDbPort());
		return new DBResponse(result);

	}

	/**
	 * This api dedicated for modifying data-source map with registered tenant
	 * at runtime
	 * 
	 * @param inputData
	 * @return
	 * @throws ConfigExceptions
	 */
	@RequestMapping(value = "updateDatasourceMap", method = RequestMethod.POST)
	@ResponseBody
	public DBResponse updateDatasourceMap(@RequestBody InputData inputData)
			throws ConfigExceptions {

		Map<String, DataSourceProperties> dataSourceMap = wsHandler.updateDatasourceMap();

		LOGGER.info("datasource map for :" + inputData.getDbName() + " is :"
				+ dataSourceMap.get(inputData.getDbName()));
		return new DBResponse("data source map successfully updated");

	}

}
