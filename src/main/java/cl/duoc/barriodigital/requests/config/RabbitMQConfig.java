package cl.duoc.barriodigital.requests.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Declara la topología RabbitMQ de BarrioDigital: exchanges, colas, DLQ y bindings.
// Spring Boot detecta estos beans y los declara solo al arrancar (son idempotentes).
@Configuration
public class RabbitMQConfig {

	@Bean
	public DirectExchange cmdDirectExchange() {
		return new DirectExchange(RabbitMQNames.EXCHANGE_DIRECT);
	}

	@Bean
	public TopicExchange cmdTopicExchange() {
		return new TopicExchange(RabbitMQNames.EXCHANGE_TOPIC);
	}

	@Bean
	public DirectExchange cmdDeadLetterExchange() {
		return new DirectExchange(RabbitMQNames.EXCHANGE_DLX);
	}

	// Cada cola principal reenvía a su propia DLQ a través del DLX cuando se hace NACK sin reintento.
	// x-max-length aplica la política de retención: evita que la cola crezca sin límite.
	private Queue colaPrincipal(String nombre, String nombreDlq) {
		return QueueBuilder.durable(nombre)
				.withArgument("x-dead-letter-exchange", RabbitMQNames.EXCHANGE_DLX)
				.withArgument("x-dead-letter-routing-key", nombreDlq)
				.withArgument("x-max-length", RabbitMQNames.MAX_LENGTH_COLA_PRINCIPAL)
				.build();
	}

	// Las DLQ también se limpian solas: TTL para no acumular mensajes muertos para siempre,
	// más un tope duro por si el TTL no alcanza a vaciarlas a tiempo.
	private Queue colaDlq(String nombre) {
		return QueueBuilder.durable(nombre)
				.withArgument("x-message-ttl", RabbitMQNames.DLQ_MESSAGE_TTL_MS)
				.withArgument("x-max-length", RabbitMQNames.MAX_LENGTH_DLQ)
				.build();
	}

	@Bean
	public Queue colaEmail() {
		return colaPrincipal(RabbitMQNames.QUEUE_EMAIL, RabbitMQNames.QUEUE_EMAIL_DLQ);
	}

	@Bean
	public Queue colaEmailDlq() {
		return colaDlq(RabbitMQNames.QUEUE_EMAIL_DLQ);
	}

	@Bean
	public Queue colaCrew() {
		return colaPrincipal(RabbitMQNames.QUEUE_CREW, RabbitMQNames.QUEUE_CREW_DLQ);
	}

	@Bean
	public Queue colaCrewDlq() {
		return colaDlq(RabbitMQNames.QUEUE_CREW_DLQ);
	}

	@Bean
	public Queue colaCertificate() {
		return colaPrincipal(RabbitMQNames.QUEUE_CERTIFICATE, RabbitMQNames.QUEUE_CERTIFICATE_DLQ);
	}

	@Bean
	public Queue colaCertificateDlq() {
		return colaDlq(RabbitMQNames.QUEUE_CERTIFICATE_DLQ);
	}

	@Bean
	public Binding bindingEmailDirect() {
		return BindingBuilder.bind(colaEmail()).to(cmdDirectExchange()).with(RabbitMQNames.ROUTING_KEY_EMAIL);
	}

	@Bean
	public Binding bindingEmailTopic() {
		return BindingBuilder.bind(colaEmail()).to(cmdTopicExchange()).with(RabbitMQNames.BINDING_PATTERN_EMAIL);
	}

	@Bean
	public Binding bindingCrewDirect() {
		return BindingBuilder.bind(colaCrew()).to(cmdDirectExchange()).with(RabbitMQNames.ROUTING_KEY_CREW);
	}

	@Bean
	public Binding bindingCrewTopic() {
		return BindingBuilder.bind(colaCrew()).to(cmdTopicExchange()).with("crew.#");
	}

	@Bean
	public Binding bindingCertificateDirect() {
		return BindingBuilder.bind(colaCertificate()).to(cmdDirectExchange()).with(RabbitMQNames.ROUTING_KEY_CERTIFICATE);
	}

	@Bean
	public Binding bindingCertificateTopic() {
		return BindingBuilder.bind(colaCertificate()).to(cmdTopicExchange()).with("certificate.*");
	}

	// Cada DLQ se une al DLX con routing key = su propio nombre (así llegó desde x-dead-letter-routing-key).
	// Declarables agrupa varios Binding en un solo bean (List<Binding> no lo detecta RabbitAdmin por type erasure).
	@Bean
	public Declarables bindingsDlq() {
		return new Declarables(
				BindingBuilder.bind(colaEmailDlq()).to(cmdDeadLetterExchange()).with(RabbitMQNames.QUEUE_EMAIL_DLQ),
				BindingBuilder.bind(colaCrewDlq()).to(cmdDeadLetterExchange()).with(RabbitMQNames.QUEUE_CREW_DLQ),
				BindingBuilder.bind(colaCertificateDlq()).to(cmdDeadLetterExchange()).with(RabbitMQNames.QUEUE_CERTIFICATE_DLQ)
		);
	}

}
