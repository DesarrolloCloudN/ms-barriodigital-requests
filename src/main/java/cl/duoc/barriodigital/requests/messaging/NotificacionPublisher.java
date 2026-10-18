package cl.duoc.barriodigital.requests.messaging;

import cl.duoc.barriodigital.requests.config.RabbitMQNames;
import cl.duoc.barriodigital.requests.entity.Tramite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.UUID;

// Único punto del microservicio que sabe de RabbitMQ: el resto del dominio no conoce
// exchanges ni routing keys, solo llama a estos métodos (ver TramiteService).
@Service
public class NotificacionPublisher {

	private static final Logger log = LoggerFactory.getLogger(NotificacionPublisher.class);

	private final RabbitTemplate rabbitTemplate;
	private final ObjectMapper objectMapper;

	public NotificacionPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
		this.rabbitTemplate = rabbitTemplate;
		this.objectMapper = objectMapper;
	}

	// Al ingresar un trámite: se avisa al vecino por email y se genera el comprobante de ingreso.
	public void publicarIngreso(Tramite tramite) {
		publicar(RabbitMQNames.EXCHANGE_DIRECT, RabbitMQNames.ROUTING_KEY_EMAIL, "EMAIL_INGRESO", tramite);
		publicar(RabbitMQNames.EXCHANGE_DIRECT, RabbitMQNames.ROUTING_KEY_CERTIFICATE, "CERTIFICADO_INGRESO", tramite);
	}

	// Al pasar a EN_TERRENO: se genera el ticket de visita para la cuadrilla.
	public void publicarVisitaTerreno(Tramite tramite) {
		publicar(RabbitMQNames.EXCHANGE_DIRECT, RabbitMQNames.ROUTING_KEY_CREW, "CREW_TICKET", tramite);
	}

	// Al quedar RESUELTO/RECHAZADO: aviso final al vecino (por cmd.topic, para demostrar ese tipo de
	// exchange con una routing key compatible con el patrón email.* ya enlazado a q.cmd.email) y
	// certificado de resolución.
	public void publicarResolucion(Tramite tramite) {
		publicar(RabbitMQNames.EXCHANGE_TOPIC, RabbitMQNames.ROUTING_KEY_EMAIL_TOPIC, "EMAIL_RESOLUCION", tramite);
		publicar(RabbitMQNames.EXCHANGE_DIRECT, RabbitMQNames.ROUTING_KEY_CERTIFICATE, "CERTIFICADO_RESOLUCION", tramite);
	}

	private void publicar(String exchange, String routingKey, String tipoEvento, Tramite tramite) {
		NotificacionEvento evento = new NotificacionEvento(
				UUID.randomUUID().toString(),
				UUID.randomUUID().toString(),
				tramite.getId(),
				tramite.getVecinoId(),
				tramite.getVecinoNombre(),
				tramite.getDescripcion(),
				tramite.getEstado().name(),
				tipoEvento,
				LocalDateTime.now()
		);
		try {
			String mensajeJson = objectMapper.writeValueAsString(evento);
			log.info("Publicando mensaje / Exchange: {} / Routing Key: {} / eventId: {} / correlationId: {}",
					exchange, routingKey, evento.eventId(), evento.correlationId());
			rabbitTemplate.convertAndSend(exchange, routingKey, mensajeJson);
		} catch (JacksonException | AmqpException ex) {
			// No se interrumpe el flujo de negocio por un problema de mensajería: solo se registra.
			log.error("No se pudo serializar/publicar el evento {} del trámite {}: {}", tipoEvento, tramite.getId(), ex.getMessage());
		}
	}

}
