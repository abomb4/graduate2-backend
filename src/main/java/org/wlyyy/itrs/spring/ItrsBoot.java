package org.wlyyy.itrs.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.wlyyy.itrs")
@MapperScan(basePackages = "org.wlyyy.itrs.dao")
@EnableAutoConfiguration
public class ItrsBoot {

    public static void main(String[] args) {
        SpringApplication.run(ItrsBoot.class);
    }
}
