package com.stit.punch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.stit.punch.deser.MyDateDeserializer;
import com.stit.punch.deser.MyLocalDateDeserializer;
import com.stit.punch.deser.MyLocalDateTimeDeserializer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
//import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import java.util.List;
import java.util.TimeZone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.data.domain.AuditorAware;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.util.UrlPathHelper;
//import org.thymeleaf.spring4.SpringTemplateEngine;
//import org.thymeleaf.spring4.view.ThymeleafViewResolver;
//import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

	private Logger log = LogManager.getLogger();

	/**
	 * 重要, especial for http DELETE method, deafult is not allow. CORS = cross
	 * orgin resource sharing
	 *
	 * @param registry
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// original
		/*
		registry.addMapping("/api/**")
			.allowedOrigins("*")
			.allowedHeaders("*")
			.allowedMethods("*");
		*/
		registry
			.addMapping("/**")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
			.allowedOriginPatterns("*")
			//.allowedOrigins("*")
			.allowedHeaders("*")
			.exposedHeaders("*")    // ex. allow Angular can get response headers
			.allowCredentials(true);

	}

	/**
	 * JPA @createBy....
	 *
	 * @return
	 */
	//@Bean
	//public AuditorAware<String> auditorProvider() {
	//	return new AuditAwareImpl();
	//}
	/**
	 * matrix variables
	 *
	 * @param configurer
	 */
	@Override
	protected void configurePathMatch(PathMatchConfigurer configurer) {
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		urlPathHelper.setRemoveSemicolonContent(false);
		configurer.setUrlPathHelper(urlPathHelper);
		configurer.setUseSuffixPatternMatch(false);  // attn Date ISO  xxxx.xxxZ
	}

	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(mappingJackson2HttpMessageConverter());
		converters.add(new StringHttpMessageConverter());

		super.configureMessageConverters(converters);
	}

	/**
	 * resolve for JPA inifinite loop one2many jackson hibernate type
	 * jackson-datatype-hibernate5
	 *
	 * @return
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		// original
		/*
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = jsonConverter.getObjectMapper();

		// ---------------------------------
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
		objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));

		ISO8601DateFormat df = new ISO8601DateFormat();
		df.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
		//-----------------------------------------------
		jsonConverter.setObjectMapper(objectMapper);
		jsonConverter.setPrettyPrint(true);

		return jsonConverter;
		*/

		JavaTimeModule javaTimeModule = new JavaTimeModule();
    // Custom for LocalDate, LocalDateTime, java.util.Date
		javaTimeModule.addDeserializer(LocalDate.class, new MyLocalDateDeserializer());
		javaTimeModule.addDeserializer(LocalDateTime.class, new MyLocalDateTimeDeserializer());
		javaTimeModule.addDeserializer(Date.class, new MyDateDeserializer());

		ObjectMapper mapper
			= Jackson2ObjectMapperBuilder
				.json()
				.modules(javaTimeModule, new Jdk8Module())
				.build()
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		// java.util.Date
		// output string using tw
		SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
		mapper.setDateFormat(dateFmt);

		MappingJackson2HttpMessageConverter conv = new MappingJackson2HttpMessageConverter(mapper);
		conv.setPrettyPrint(true);

		return conv;
	}

	// thymeleaf, using auto config feature 
	/*
	@Bean
	public ClassLoaderTemplateResolver templateResolver() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setCacheable(false);
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCharacterEncoding("UTF-8");
		//templateResolver.setOrder(1); //david

		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		return templateEngine;

	}

	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();

		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setCharacterEncoding("UTF-8");

		return viewResolver;
	}
	 */
	/**
	 * index.html, redirect(必須是重新導向 redirect) login.html
	 *
	 * @param registry
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "index.html");
		//registry.addViewController("/login/form").setViewName("login");
	}

	/**
	 * Angular 5, static content index.html loaded, then *.js, *.css loading,
	 * instruct it to /static/
	 *
	 * @param registry
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
			.addResourceLocations(
				"classpath:/public/",
				"classpath:/static/",
				"classpath:/static/css",
				"classpath:/static/js",
				"classpath:/templates/",
				"classpath:/keys/");
	}

} // end class
