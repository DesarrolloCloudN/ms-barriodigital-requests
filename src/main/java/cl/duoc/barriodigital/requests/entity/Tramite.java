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

// Representa un trámite que ingresó un vecino. Va cambiando de estado a
// medida que el funcionario lo va gestionando (ver EstadoTramite para la
// máquina de estados). @Entity + @Table le dicen a JPA que esta clase se
// guarda en la tabla TRAMITES de la base de datos.
@Entity
@Table(name = "TRAMITES")
public class Tramite {

	// Id autogenerado por la base de datos (autoincremental).
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Id del tipo de trámite (viene del microservicio catalog).
	@Column(name = "TIPO_TRAMITE_ID", nullable = false)
	private Long tipoTramiteId;

	// Datos del vecino que hizo el trámite.
	@Column(name = "VECINO_ID", nullable = false)
	private String vecinoId;

	@Column(name = "VECINO_NOMBRE", nullable = false)
	private String vecinoNombre;

	@Column(name = "DESCRIPCION", length = 2000, nullable = false)
	private String descripcion;

	// Estado actual del trámite dentro de la máquina de estados.
	// @Enumerated(EnumType.STRING) hace que se guarde como texto (por
	// ejemplo "INGRESADO") en vez de un número, para que sea más legible.
	@Enumerated(EnumType.STRING)
	@Column(name = "ESTADO", nullable = false)
	private EstadoTramite estado;

	// Funcionario que quedó a cargo del trámite (puede ser null al inicio).
	@Column(name = "FUNCIONARIO_ASIGNADO")
	private String funcionarioAsignado;

	// Notas u observaciones que se van agregando durante la gestión.
	@Column(name = "OBSERVACIONES", length = 2000)
	private String observaciones;

	@Column(name = "FECHA_INGRESO", nullable = false)
	private LocalDateTime fechaIngreso;

	@Column(name = "FECHA_ACTUALIZACION", nullable = false)
	private LocalDateTime fechaActualizacion;

	// Constructor vacío que pide JPA internamente para poder crear los
	// objetos cuando lee desde la base de datos. No se usa directamente.
	protected Tramite() {
		// requerido por JPA
	}

	// Constructor que se usa al crear un trámite nuevo. Siempre arranca en
	// estado INGRESADO y con la fecha de ingreso/actualización en el momento
	// en que se crea.
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

	public String getFuncionarioAsignado() {
		return funcionarioAsignado;
	}

	public void setFuncionarioAsignado(String funcionarioAsignado) {
		this.funcionarioAsignado = funcionarioAsignado;
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
