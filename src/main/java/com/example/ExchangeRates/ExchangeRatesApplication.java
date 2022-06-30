package com.example.ExchangeRates;

import com.example.ExchangeRates.DTO.Currency;
import com.example.ExchangeRates.DTO.RateRecord;
import com.example.ExchangeRates.gui.JavaFxApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.ExchangeRates.data")
public class ExchangeRatesApplication {

	public static void main(String[] args) {
		Application.launch(JavaFxApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	@Bean
	public Currency getCurrency(){
		return new Currency();
	}

	@Bean
	public RateRecord getRateRecord(){
		return new RateRecord();
	}

	@Bean(name = "messageSource")
	public MessageSource getMessageResource()  {
		ReloadableResourceBundleMessageSource messageResource = new ReloadableResourceBundleMessageSource();
		messageResource.setBasename("classpath:messages");
		messageResource.setDefaultEncoding("UTF-8");
		return messageResource;
	}

}
