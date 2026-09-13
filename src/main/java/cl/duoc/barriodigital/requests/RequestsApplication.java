package cl.duoc.barriodigital.requests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Esta es la clase que arranca el microservicio de "requests" (trámites).
// @SpringBootApplication le dice a Spring que configure todo automáticamente.
@SpringBootApplication
public class RequestsApplication {

	// Punto de entrada del programa: acá empieza a correr la aplicación.
	public static void main(String[] args) {
		SpringApplication.run(RequestsApplication.class, args);
	}

}
