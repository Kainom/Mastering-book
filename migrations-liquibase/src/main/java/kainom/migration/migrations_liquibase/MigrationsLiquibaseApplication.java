package kainom.migration.migrations_liquibase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication()
@EnableCaching
public class MigrationsLiquibaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(MigrationsLiquibaseApplication.class, args);
	}

}
