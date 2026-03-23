package com.TeachCode.E_commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(
    basePackages = {"com.TeachCode.E_commerce"}    
)
@EntityScan(basePackages = {"com.TeachCode.E_commerce"}) 
@EnableJpaRepositories(basePackages = {"com.TeachCode.E_commerce"})
public class EcommerceApplication {

 public static void main(String[] args) {
     SpringApplication.run(EcommerceApplication.class, args);
 }
}
