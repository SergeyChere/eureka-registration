package com.redirect.redirect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@PropertySource("classpath:application.properties")
@RequestMapping("/")
public class RedirectApplication {

	private static ConfigurableApplicationContext applicationContext;
	private static RestTemplate restTemplate = new RestTemplate();

	@Value("${conductor.url}")
	private String url;

	@Scheduled(fixedRate = 10000)
	public void healthCheck() {
		try {
			restTemplate.getForEntity(url, String.class);
			System.out.println("hello"+ System.currentTimeMillis());
		} catch (Exception exc) {
			System.err.println(exc);
			applicationContext.close();
		}
	}

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "redirect-client-app");
		applicationContext = SpringApplication.run(RedirectApplication.class, args);
	}
}
