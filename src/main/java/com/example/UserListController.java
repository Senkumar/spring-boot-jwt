package com.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserListController {
	
	@RequestMapping("/users")
	public @ResponseBody String getUsers() {
		return "{\"users\":[{\"firstname\":\"Brendan\", \"lastname\":\"Gloster\"}," +
		           "{\"firstname\":\"Luke\",\"lastname\":\"Shuley\"}]}";
	}

}
