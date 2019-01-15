package com.zhy.composite;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigListener {
	
	@Bean
    public CompositeLoader applicationStartListener(){
        return new CompositeLoader();
    }

}
