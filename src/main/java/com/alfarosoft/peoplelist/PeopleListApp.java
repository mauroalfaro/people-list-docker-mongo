package com.alfarosoft.peoplelist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.alfarosoft.peoplelist")
public class PeopleListApp {

    public static void main(String[] args){
        SpringApplication.run(PeopleListApp.class, args);
    }
}
