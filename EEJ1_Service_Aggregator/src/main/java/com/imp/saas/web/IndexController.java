package com.imp.saas.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * handler for index page
 * @author rakesh.singhania
 *
 */
@Controller
@RequestMapping("/")
public class IndexController {

	@RequestMapping
	public String index() {
		return "index";
	}
}
