package cl.duoc.gerentes_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
@OpenAPIDefinition(
		info = @Info(
				title = "API Regifood 'Gerentes'",
				version = "1.0",
				description = "API que gestiona el CRUD completo y metodos personalizados de " +
						"todos los Gerentes de Tasty Feast en Chile",
				contact = @Contact(
						name = "Vicente Lopez",
						email = "vic.lopez@estudiante.duoc.cl"
				)
		)
)
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class GerentesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerentesServiceApplication.class, args);
	}

}
