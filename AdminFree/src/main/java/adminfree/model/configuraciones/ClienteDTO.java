package adminfree.model.configuraciones;

import java.io.Serializable;
import java.util.Date;

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

	/** TOKEN que se utiliza para que el cliente se autentique en el sistema */
	private String token;

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
	 * Metodo que permite obtener el valor del atributo token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param token
	 */
	public void setToken(String token) {
		this.token = token;
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
}
