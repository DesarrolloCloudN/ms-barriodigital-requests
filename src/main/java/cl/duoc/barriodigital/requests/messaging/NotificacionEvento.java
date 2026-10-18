package cl.duoc.barriodigital.requests.messaging;

import java.time.LocalDateTime;

// Payload que viaja por RabbitMQ hacia ms-barriodigital-notify (se serializa como JSON).
public record NotificacionEvento(
		String eventId,
		String correlationId,
		Long tramiteId,
		String vecinoId,
		String vecinoNombre,
		String descripcion,
		String estado,
		String tipoEvento,
		LocalDateTime timestamp
) {
}
