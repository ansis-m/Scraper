package com.chemapps.ChemScraper;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/records").setViewName("records");
        registry.addViewController("/form").setViewName("form");
        registry.addViewController("/search").setViewName("search");

        // registry.addViewController("/groups").setViewName("groups");
        // registry.addViewController("/group").setViewName("group");
        // registry.addViewController("/courses").setViewName("courses");
        // registry.addViewController("/course").setViewName("course");
    }


}
