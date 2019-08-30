package com.jazz321254.demo.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

	protected static final String LOGGER_HEADER = "========== {} ==========";

	@RequestMapping("/index")
	public String welcome(Map<String, Object> model) {
		LOGGER.info(LOGGER_HEADER, "Enter to hompage view");
		model.put("message", "Hello Spring");
		model.put("description", "This example is a quick exercise to illustrate how build to Spring boot works.");
		return "welcome";
	}
}