package com.pocket.retal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ServletComponentScan
@MapperScan(basePackages = {"com.pocket.retal.repository"})
public class PocketRentalCompanyApplication {
    public static void main(String[] args) {
        SpringApplication.run(PocketRentalCompanyApplication.class, args);
    }

}
