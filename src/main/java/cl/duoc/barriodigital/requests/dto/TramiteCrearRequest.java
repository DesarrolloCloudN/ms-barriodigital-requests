package cl.duoc.barriodigital.requests.dto;

/**
 * Body de {@code POST /api/requests}.
 */
// DTO (record) que representa los datos que llegan cuando se crea un
// trámite nuevo. Un record es solo una forma corta de escribir una clase
// que únicamente guarda datos (Java genera constructor y getters solo).
public record TramiteCrearRequest(
		Long tipoTramiteId,
		String vecinoId,
		String vecinoNombre,
		String descripcion
) {
}
