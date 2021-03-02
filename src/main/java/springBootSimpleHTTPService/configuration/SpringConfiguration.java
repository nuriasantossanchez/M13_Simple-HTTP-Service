package springBootSimpleHTTPService.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.TimeZone;

@Configuration
@EnableScheduling
@EnableAsync
public class SpringConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        System.out.println("!!!! OK : PropertySourcesPlaceholderConfigurer propertyConfigInDev !!!!!!");
        return new PropertySourcesPlaceholderConfigurer();
    }


    @Bean
    public ClientHttpRequestFactory getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory ret = new SimpleClientHttpRequestFactory();
        System.out.println("!!!! OK : getClientHttpRequestFactory !!!!!!");
        //ret.setReadTimeout(60000); // 60 sec
        return ret;
    }

    @Bean
    public RestTemplate getRestTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        System.out.println("!!!! OK : getRestTemplate !!!!!!");
        return new RestTemplate(clientHttpRequestFactory);
    }



    @Bean
    public ModelMapper getModelMapper() {
        System.out.println("!!!! OK : getModelMapper !!!!!!");
        return new ModelMapper();
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        System.out.println("!!!! OK : getObjectMapper !!!!!!");
        ObjectMapper mapper = new ObjectMapper();
        mapper.setTimeZone(TimeZone.getDefault());
        return mapper;
    }

}
