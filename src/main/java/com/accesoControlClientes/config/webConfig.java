package com.accesoControlClientes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class webConfig implements WebMvcConfigurer {

    //LISTENER para implementar diferentes idiomas
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("es"));
        return localeResolver;
    }

    //Detecta el cambio de idioma usando un parámetro en la URL (?lang=en)
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    //Permite cambiar el idioma
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor()); //cambia dinamicamente el lenguaje
    }

    //MAPEO DE URLS
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry){
//        registry.addViewController("/login").setViewName("login");
//    }
}
