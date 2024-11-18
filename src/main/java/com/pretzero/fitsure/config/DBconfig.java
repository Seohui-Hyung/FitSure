package com.pretzero.fitsure.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.pretzero.fitsure.model.dao")
public class DBconfig {

}
