package org.psg;

/**
 * Created by user on 08.04.16.
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


//@SpringBootApplication
//@EntityScan(basePackages = { "org.psg.model" })
//@EnableJpaRepositories(basePackages = { "org.psg.repository" })
//
////Read more: http://mrbool.com/rest-server-with-spring-data-spring-boot-and-postgresql/34023#ixzz45JcPtDYW
//@Configuration
//@EnableAutoConfiguration


@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {


    public static void main(String[] args) {


        SpringApplication.run(Application.class, args);
    }

}
