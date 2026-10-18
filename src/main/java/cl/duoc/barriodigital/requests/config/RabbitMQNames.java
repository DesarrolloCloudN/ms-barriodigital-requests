package cl.duoc.barriodigital.requests.config;

// Nombres de exchanges, colas, DLQ y routing keys de BarrioDigital (EP2).
// Centralizados aquí para que ningún otro archivo escriba estos literales a mano.
public final class RabbitMQNames {

	private RabbitMQNames() {
	}

	public static final String EXCHANGE_DIRECT = "cmd.direct";
	public static final String EXCHANGE_TOPIC = "cmd.topic";
	public static final String EXCHANGE_DLX = "cmd.dead.dlx";

	public static final String QUEUE_EMAIL = "q.cmd.email";
	public static final String QUEUE_EMAIL_DLQ = "q.cmd.email.dlq";
	public static final String ROUTING_KEY_EMAIL = "email.send";
	public static final String ROUTING_KEY_EMAIL_TOPIC = "email.send.final";
	public static final String BINDING_PATTERN_EMAIL = "email.*";

	public static final String QUEUE_CREW = "q.cmd.crew";
	public static final String QUEUE_CREW_DLQ = "q.cmd.crew.dlq";
	public static final String ROUTING_KEY_CREW = "crew.ticket";

	public static final String QUEUE_CERTIFICATE = "q.cmd.certificate";
	public static final String QUEUE_CERTIFICATE_DLQ = "q.cmd.certificate.dlq";
	public static final String ROUTING_KEY_CERTIFICATE = "certificate.gen";

	// Retención y limpieza (Etapa 14 de la guía): topes para que el broker no crezca sin control.
	// Una cola principal sin tope podría acumular mensajes indefinidamente si notify se cae;
	// con x-max-length el broker descarta los más antiguos (que de todas formas ya no son útiles
	// pasado ese volumen) en vez de agotar memoria/disco.
	public static final int MAX_LENGTH_COLA_PRINCIPAL = 1000;
	public static final int MAX_LENGTH_DLQ = 500;
	public static final long DLQ_MESSAGE_TTL_MS = 7L * 24 * 60 * 60 * 1000; // 7 días

}
