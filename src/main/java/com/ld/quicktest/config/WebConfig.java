package com.ld.quicktest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * Класс конфигурации, в котором настроены простые контроллеры для главной страницы, и страницы авторизации.
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("homePage");
        registry.addViewController("/login").setViewName("loginPage");
    }
}
