package adminfree.dtos.correspondencia;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import adminfree.dtos.transversal.PaginadorDTO;

/**
 * DTO que contiene los atributos para los filtros de busqueda
 * de los consecutivos de correspondencia en el sistema
 * 
 * @author Carlos Andres Diaz
 *
 */
public class FiltroConsecutivosDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** paginador para la consulta los consecutivos **/
	private PaginadorDTO paginador;

	/** Es el cliente autenticado o el cliente asociado al user autenticado */
	private Long idCliente;

	/** Busqueda por nomenclaturas, separadas por comas */
	private String nomenclaturas;

	/** Busqueda por consecutivos, separadas por comas */
	private String consecutivos;

	/** Busqueda por el identificador del usuario */
	private Integer idUsuario;

	/** Busqueda por fecha de solicitud inicial */
	private Date fechaSolicitudInicial;

	/** Busqueda por fecha de solicitud final */
	private Date fechaSolicitudFinal;

	/** Busqueda por estado del consecutivo */
	private Integer estado;

	/** lista de otros filtros agregados */
	private List<CampoFiltroDTO> filtrosAgregados;

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
	 * Metodo que permite obtener el valor del atributo idCliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * idCliente
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idUsuario
	 */
	public Integer getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Metodo que permite obtener el valor del atributo estado
	 */
	public Integer getEstado() {
		return estado;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idUsuario
	 */
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param estado
	 */
	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	/**
	 * Metodo que permite obtener el valor del atributo paginador
	 */
	public PaginadorDTO getPaginador() {
		return paginador;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param paginador
	 */
	public void setPaginador(PaginadorDTO paginador) {
		this.paginador = paginador;
	}

	/**
	 * Metodo que permite obtener el valor del atributo filtrosAgregados
	 */
	public List<CampoFiltroDTO> getFiltrosAgregados() {
		return filtrosAgregados;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param filtrosAgregados
	 */
	public void setFiltrosAgregados(List<CampoFiltroDTO> filtrosAgregados) {
		this.filtrosAgregados = filtrosAgregados;
	}
}
