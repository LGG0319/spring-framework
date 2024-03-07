package myspring.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("myspring")
public class AppConfig {


	@Bean
	public MessageSource messageSource(){
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename("message");
		source.setDefaultEncoding("UTF-8");
		source.setCacheSeconds(10);
		return source;
	}
}


