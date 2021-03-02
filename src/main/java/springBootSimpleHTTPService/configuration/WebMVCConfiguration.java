package springBootSimpleHTTPService.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan(basePackages = {"springBootSimpleHTTPService"})
@Configuration
//@EnableWebMvc
public class WebMVCConfiguration implements WebMvcConfigurer {
//public class WebMVCConfiguration extends DelegatingWebMvcConfiguration {


   @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("!!!! OK : addResourceHandlers !!!!!!");
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("!!!! OK : addCorsMappings !!!!!!");
        registry.addMapping("/**").allowedOrigins("http://localhost:8181");
    }



}
