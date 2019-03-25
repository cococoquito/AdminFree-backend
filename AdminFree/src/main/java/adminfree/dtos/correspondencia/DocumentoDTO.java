package adminfree.dtos.correspondencia;

import java.io.Serializable;

/**
 * DTO que contiene los atributos de un documento para el cargue o lectura de
 * los archivos asociados a un consecutivo
 * 
 * @author Carlos Andres Diaz
 *
 */
public class DocumentoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del documento */
	private Long id;

	/** Identificador del cliente */
	private Integer idCliente;

	/** Nombre del documento */
	private String nombreDocumento;

	/** Tipo de documento PDF, excel, word */
	private String tipoDocumento;

	/** Es el tamanio del documento */
	private String sizeDocumento;

	/** Fecha en la que se realizo el cargue del documento */
	private String fechaCargue;

	/** ATRIBUTOS UTILIZADOS SOLAMENTE PARA JAVA - NO SE ENVIA ANGULAR */
	/** Contenido del archivo */
	private byte[] contenido;

	/** Identificador del consecutivo */
	private String idConsecutivo;

	/**
	 * Metodo que permite obtener el valor del atributo idCliente
	 */
	public Integer getIdCliente() {
		return idCliente;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idConsecutivo
	 */
	public String getIdConsecutivo() {
		return idConsecutivo;
	}

	/**
	 * Metodo que permite obtener el valor del atributo nombreDocumento
	 */
	public String getNombreDocumento() {
		return nombreDocumento;
	}

	/**
	 * Metodo que permite obtener el valor del atributo tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * Metodo que permite obtener el valor del atributo sizeDocumento
	 */
	public String getSizeDocumento() {
		return sizeDocumento;
	}

	/**
	 * Metodo que permite obtener el valor del atributo contenido
	 */
	public byte[] getContenido() {
		return contenido;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idCliente
	 */
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idConsecutivo
	 */
	public void setIdConsecutivo(String idConsecutivo) {
		this.idConsecutivo = idConsecutivo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param nombreDocumento
	 */
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param tipoDocumento
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param sizeDocumento
	 */
	public void setSizeDocumento(String sizeDocumento) {
		this.sizeDocumento = sizeDocumento;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param contenido
	 */
	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}

	/**
	 * Metodo que permite obtener el valor del atributo id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Metodo que permite obtener el valor del atributo fechaCargue
	 */
	public String getFechaCargue() {
		return fechaCargue;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param fechaCargue
	 */
	public void setFechaCargue(String fechaCargue) {
		this.fechaCargue = fechaCargue;
	}
}
