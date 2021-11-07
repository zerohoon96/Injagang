package com.SpringIsComing.injagang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class}) //https://lemontia.tistory.com/586 참고
public class InjagangApplication {

	public static void main(String[] args) {
		System.out.println("InjagangApplication.main~~~");
		SpringApplication.run(InjagangApplication.class, args);
	}

}
