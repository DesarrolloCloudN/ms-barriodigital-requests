package cl.duoc.barriodigital.requests.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

// Trámite de un vecino; su estado cambia según la máquina de EstadoTramite.
@Entity
@Table(name = "TRAMITES")
public class Tramite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Id del tipo de trámite (viene del microservicio catalog).
	@Column(name = "TIPO_TRAMITE_ID", nullable = false)
	private Long tipoTramiteId;

	@Column(name = "VECINO_ID", nullable = false)
	private String vecinoId;

	@Column(name = "VECINO_NOMBRE", nullable = false)
	private String vecinoNombre;

	@Column(name = "DESCRIPCION", length = 2000, nullable = false)
	private String descripcion;

	// Se guarda como texto (STRING) para que sea legible en la BD.
	@Enumerated(EnumType.STRING)
	@Column(name = "ESTADO", nullable = false)
	private EstadoTramite estado;

	@Column(name = "RESPONSABLE_ASIGNADO")
	private String responsableAsignado;

	@Column(name = "OBSERVACIONES", length = 2000)
	private String observaciones;

	@Column(name = "FECHA_INGRESO", nullable = false)
	private LocalDateTime fechaIngreso;

	@Column(name = "FECHA_ACTUALIZACION", nullable = false)
	private LocalDateTime fechaActualizacion;

	// Constructor vacío que exige JPA; no se usa directamente en el código.
	protected Tramite() {
	}

	// Al crear un trámite queda en estado INGRESADO con la fecha actual.
	public Tramite(Long tipoTramiteId, String vecinoId, String vecinoNombre, String descripcion) {
		this.tipoTramiteId = tipoTramiteId;
		this.vecinoId = vecinoId;
		this.vecinoNombre = vecinoNombre;
		this.descripcion = descripcion;
		this.estado = EstadoTramite.INGRESADO;
		LocalDateTime ahora = LocalDateTime.now();
		this.fechaIngreso = ahora;
		this.fechaActualizacion = ahora;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTipoTramiteId() {
		return tipoTramiteId;
	}

	public void setTipoTramiteId(Long tipoTramiteId) {
		this.tipoTramiteId = tipoTramiteId;
	}

	public String getVecinoId() {
		return vecinoId;
	}

	public void setVecinoId(String vecinoId) {
		this.vecinoId = vecinoId;
	}

	public String getVecinoNombre() {
		return vecinoNombre;
	}

	public void setVecinoNombre(String vecinoNombre) {
		this.vecinoNombre = vecinoNombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public EstadoTramite getEstado() {
		return estado;
	}

	public void setEstado(EstadoTramite estado) {
		this.estado = estado;
	}

	public String getResponsableAsignado() {
		return responsableAsignado;
	}

	public void setResponsableAsignado(String responsableAsignado) {
		this.responsableAsignado = responsableAsignado;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public LocalDateTime getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDateTime fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public LocalDateTime getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

}
