package adminfree.dtos.correspondencia;

import java.io.Serializable;

/**
 * DTO que contiene los atributos de un consecutivo
 *
 * @author Carlos Andres Diaz
 *
 */
public class ConsecutivoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del consecutivo */
	private Long idConsecutivo;

	/** Nro del consecutivo */
	private String consecutivo;

	/** Nombre de la nomenclatura */
	private String nomenclatura;

	/** Es la descripcion de la nomenclatura */
	private String nomenclaturaDesc;

	/** nombre del usuario quien solicito el consecutivo */
	private String usuario;

	/** Fecha en la que solicitaron el consecutivo */
	private String fechaSolicitud;

	/** Estado en la que se encuentra el consecutivo */
	private Integer idEstado;

	/**
	 * Metodo que permite obtener el valor del atributo idConsecutivo
	 */
	public Long getIdConsecutivo() {
		return idConsecutivo;
	}

	/**
	 * Metodo que permite obtener el valor del atributo consecutivo
	 */
	public String getConsecutivo() {
		return consecutivo;
	}

	/**
	 * Metodo que permite obtener el valor del atributo nomenclatura
	 */
	public String getNomenclatura() {
		return nomenclatura;
	}

	/**
	 * Metodo que permite obtener el valor del atributo usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idEstado
	 */
	public Integer getIdEstado() {
		return idEstado;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idConsecutivo
	 */
	public void setIdConsecutivo(Long idConsecutivo) {
		this.idConsecutivo = idConsecutivo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param consecutivo
	 */
	public void setConsecutivo(String consecutivo) {
		this.consecutivo = consecutivo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param nomenclatura
	 */
	public void setNomenclatura(String nomenclatura) {
		this.nomenclatura = nomenclatura;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idEstado
	 */
	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	/**
	 * Metodo que permite obtener el valor del atributo fechaSolicitud
	 */
	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param fechaSolicitud
	 */
	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	/**
	 * Metodo que permite obtener el valor del atributo nomenclaturaDesc
	 */
	public String getNomenclaturaDesc() {
		return nomenclaturaDesc;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param nomenclaturaDesc
	 */
	public void setNomenclaturaDesc(String nomenclaturaDesc) {
		this.nomenclaturaDesc = nomenclaturaDesc;
	}
}
