package adminfree.dtos.correspondencia;

import java.io.Serializable;
import java.util.List;

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

	/** Identificador de la nomenclatura */
	private Long idNomenclatura;

	/** Nombre de la nomenclatura */
	private String nomenclatura;

	/** Es la descripcion de la nomenclatura */
	private String nomenclaturaDesc;

	/** nombre del usuario quien solicito el consecutivo */
	private String usuario;

	/** Es el cargo del usuario */
	private String usuarioCargo;

	/** Fecha en la que solicitaron el consecutivo */
	private String fechaSolicitud;

	/** Fecha en la que anularon el consecutivo (si aplica) */
	private String fechaAnulacion;

	/** Estado en la que se encuentra el consecutivo */
	private Integer idEstado;

	/** Nombre del Estado en la que se encuentra el consecutivo */
	private String estado;

	/** Lista de transferencias que se han realizado para este consecutivo */
	private List<TransferenciaDTO> transferencias;

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

	/**
	 * Metodo que permite obtener el valor del atributo estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Metodo que permite obtener el valor del atributo fechaAnulacion
	 */
	public String getFechaAnulacion() {
		return fechaAnulacion;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param fechaAnulacion
	 */
	public void setFechaAnulacion(String fechaAnulacion) {
		this.fechaAnulacion = fechaAnulacion;
	}

	/**
	 * Metodo que permite obtener el valor del atributo usuarioCargo
	 */
	public String getUsuarioCargo() {
		return usuarioCargo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param usuarioCargo
	 */
	public void setUsuarioCargo(String usuarioCargo) {
		this.usuarioCargo = usuarioCargo;
	}

	/**
	 * Metodo que permite obtener el valor del atributo transferencias
	 */
	public List<TransferenciaDTO> getTransferencias() {
		return transferencias;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param transferencias
	 */
	public void setTransferencias(List<TransferenciaDTO> transferencias) {
		this.transferencias = transferencias;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idNomenclatura
	 */
	public Long getIdNomenclatura() {
		return idNomenclatura;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idNomenclatura
	 */
	public void setIdNomenclatura(Long idNomenclatura) {
		this.idNomenclatura = idNomenclatura;
	}
}
