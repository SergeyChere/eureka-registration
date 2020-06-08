package config;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class EurekaMyTestConfig {

    @Value("${spring.application.name}")
    String vipAddress;

    @Bean
    public void getEureka() {
        EurekaClient eurekaClient = EurekaConfiguration.getEurekaClient();
        ExampleEurekaClient.sendRequestToServiceUsingEureka(eurekaClient, vipAddress);
        eurekaClient.shutdown();
    }
}
