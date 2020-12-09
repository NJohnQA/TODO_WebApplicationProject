package com.qa.shopping.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {
	
	
	@Bean  //applied on a method to specify that it returns a bean
	@Scope("prototype")  //defines the life cycle and visibility of the bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}
	// modelMapper - job is to make object mapping easy...
	// ...by automatically determining how one object maps to another object.

}
