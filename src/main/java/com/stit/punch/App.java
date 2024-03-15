package com.stit.punch;

import javax.sql.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {
	"com.stit.svc",
	"com.stit.ctrl",
	"com.stit.utils"}
)

/**
 * profile : dev / test / prod dev: ng serve --open test: ng build and deploy to
 * local tomcat for testing... prod: 客戶端
 */
@Import({
	WebConfig.class,
	SecurityConfProd.class,
	SecurityConfDev.class,
	TomcatConfig.class
})
public class App {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(App.class);

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	CommandLineRunner values() {
		return args -> {
			log.info("----------------------------------------------");
			log.info("dbUrl:" + this.dbUrl);
		  log.info("Application ready...");
		};
	}

} // end class
