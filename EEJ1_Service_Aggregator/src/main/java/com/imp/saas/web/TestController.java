package com.imp.saas.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

	@RequestMapping(value = "hello")
	public String greet() {
		return "Hello webservice";
	}
}
