package com.imp.saas.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imp.saas.handler.DatabaseHandler;
import com.imp.saas.ws.bean.CreateDBResponse;
import com.imp.saas.ws.bean.InputData;

@Controller
@RequestMapping(value = "/webservice")
public class DatabaseController {

	@Autowired
	DatabaseHandler databaseHandler;
	
	@RequestMapping("hello")
	public String test(){
		return "Gjhgashdas";
	}

	@RequestMapping(value = "/createDatabase", method=RequestMethod.POST)
	@ResponseBody
	public CreateDBResponse createDatabase(@RequestBody InputData inputData) {

		String result = databaseHandler.createDatabase(inputData.getDbName(),
				inputData.getDbUserName(), inputData.getDbPassword(),
				inputData.getDbURL());
		return new CreateDBResponse(result);

	}
}
