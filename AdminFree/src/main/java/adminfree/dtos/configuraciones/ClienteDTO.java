package adminfree.dtos.configuraciones;

import java.io.Serializable;
import java.util.Date;

import adminfree.dtos.seguridad.AutenticacionDTO;

/**
 * 
 * DTO que contiene los atributos de los clientes del sistema
 * 
 * @author Carlos Andres Diaz
 *
 */
public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Es el identificador del cliente */
	private Long id;

	/** Nombre del cliente */
	private String nombre;

	/** telefonos del cliente */
	private String telefonos;

	/** correos del cliente */
	private String emails;

	/** fecha de activacion del cliente sobre el sistema */
	private Date fechaActivacion;

	/** fecha de Inaactivacion del cliente sobre el sistema */
	private Date fechaInactivacion;

	/** Estado que se encuentra el cliente */
	private Integer estado;

	/** Es el nombre del Estado que se encuentra el cliente */
	private String estadoNombre;

	/** Identifica que tipo de accion se va ralizar sobre el cliente */
	private String tipoEvento;
	
	/** Se utiliza para la autenticacion del ADMINISTRADOR */
	private AutenticacionDTO credenciales;

	/**
	 * Metodo que permite obtener el valor del atributo id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Metodo que permite obtener el valor del atributo nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo que permite obtener el valor del atributo telefonos
	 */
	public String getTelefonos() {
		return telefonos;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * telefonos
	 */
	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}

	/**
	 * Metodo que permite obtener el valor del atributo fechaActivacion
	 */
	public Date getFechaActivacion() {
		return fechaActivacion;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * fechaActivacion
	 */
	public void setFechaActivacion(Date fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	/**
	 * Metodo que permite obtener el valor del atributo fechaInactivacion
	 */
	public Date getFechaInactivacion() {
		return fechaInactivacion;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * fechaInactivacion
	 */
	public void setFechaInactivacion(Date fechaInactivacion) {
		this.fechaInactivacion = fechaInactivacion;
	}

	/**
	 * Metodo que permite obtener el valor del atributo estado
	 */
	public Integer getEstado() {
		return estado;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param estado
	 */
	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	/**
	 * Metodo que permite obtener el valor del atributo emails
	 */
	public String getEmails() {
		return emails;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param emails
	 */
	public void setEmails(String emails) {
		this.emails = emails;
	}

	/**
	 * Metodo que permite obtener el valor del atributo tipoEvento
	 */
	public String getTipoEvento() {
		return tipoEvento;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * tipoEvento
	 */
	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	/**
	 * Metodo que permite obtener el valor del atributo estadoNombre
	 */
	public String getEstadoNombre() {
		return estadoNombre;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * estadoNombre
	 */
	public void setEstadoNombre(String estadoNombre) {
		this.estadoNombre = estadoNombre;
	}

	/**
	 * Metodo que permite obtener el valor del atributo credenciales
	 */
	public AutenticacionDTO getCredenciales() {
		return credenciales;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param credenciales
	 */
	public void setCredenciales(AutenticacionDTO credenciales) {
		this.credenciales = credenciales;
	}
}
