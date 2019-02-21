package adminfree.dtos.correspondencia;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * DTO que contiene los atributos para el filtro de busqueda de
 * los consecutivos solicitados para el anio actual
 * 
 * @author Carlos Andres Diaz
 *
 */
public class FiltroConsecutivosAnioActualDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Es el cliente autenticado o el cliente asociado al user autenticado */
	private Long idCliente;

	/** Busqueda por nomenclaturas, separadas por comas */
	private String nomenclaturas;

	/** Busqueda por consecutivos, separadas por comas */
	private String consecutivos;

	/** Busqueda por usuarios, pueden llegar varios */
	private List<Long> usuarios;

	/** Busqueda por fecha de solicitud inicial */
	private Date fechaSolicitudInicial;

	/** Busqueda por fecha de solicitud final */
	private Date fechaSolicitudFinal;

	/** Busqueda por estado del consecutivo, puede llegar varios */
	private List<Integer> estados;

	/**
	 * Metodo que permite obtener el valor del atributo nomenclaturas
	 */
	public String getNomenclaturas() {
		return nomenclaturas;
	}

	/**
	 * Metodo que permite obtener el valor del atributo consecutivos
	 */
	public String getConsecutivos() {
		return consecutivos;
	}

	/**
	 * Metodo que permite obtener el valor del atributo usuarios
	 */
	public List<Long> getUsuarios() {
		return usuarios;
	}

	/**
	 * Metodo que permite obtener el valor del atributo fechaSolicitudInicial
	 */
	public Date getFechaSolicitudInicial() {
		return fechaSolicitudInicial;
	}

	/**
	 * Metodo que permite obtener el valor del atributo fechaSolicitudFinal
	 */
	public Date getFechaSolicitudFinal() {
		return fechaSolicitudFinal;
	}

	/**
	 * Metodo que permite obtener el valor del atributo estados
	 */
	public List<Integer> getEstados() {
		return estados;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * nomenclaturas
	 */
	public void setNomenclaturas(String nomenclaturas) {
		this.nomenclaturas = nomenclaturas;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * consecutivos
	 */
	public void setConsecutivos(String consecutivos) {
		this.consecutivos = consecutivos;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param usuarios
	 */
	public void setUsuarios(List<Long> usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * fechaSolicitudInicial
	 */
	public void setFechaSolicitudInicial(Date fechaSolicitudInicial) {
		this.fechaSolicitudInicial = fechaSolicitudInicial;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * fechaSolicitudFinal
	 */
	public void setFechaSolicitudFinal(Date fechaSolicitudFinal) {
		this.fechaSolicitudFinal = fechaSolicitudFinal;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param estados
	 */
	public void setEstados(List<Integer> estados) {
		this.estados = estados;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idCliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idCliente
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
}
