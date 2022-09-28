package com.syhc;

import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication(scanBasePackages = {"com.syhc"})
@ImportResource(locations = {"classpath:applicationContext-core.xml"})
@ServletComponentScan//启动扫描加载Servlet
public class CodeGenerationServiceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        Configurator.initialize("log4j2", "classpath:code_generation_service_log4j2.xml");
        return application.sources(CodeGenerationServiceApplication.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(CodeGenerationServiceApplication.class, args);
    }

}
