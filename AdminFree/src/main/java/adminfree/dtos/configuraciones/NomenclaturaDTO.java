package adminfree.dtos.configuraciones;

import java.io.Serializable;
import java.util.List;

/**
 *
 * DTO que contiene los atributos de la nomenclatura
 *
 * @author Carlos Andres Diaz
 *
 */
public class NomenclaturaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador de la nomenclatura */
	private Long id;

	/** Nombre abreviado de la nomenclatura */
	private String nomenclatura;

	/** Descripcion de la nomenclatura */
	private String descripcion;

	/** Identificador del cliente */
	private Long idCliente;

	/** Nro donde inicia el consecutivo a generar */
	private Integer consecutivoInicial;

	/** Son los campos asociados a la nomenclatura se utiliza para la creacion */
	private List<Long> idsCampos;

	/**
	 * Metodo que permite obtener el valor del atributo id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Metodo que permite obtener el valor del atributo nomenclatura
	 */
	public String getNomenclatura() {
		return nomenclatura;
	}

	/**
	 * Metodo que permite obtener el valor del atributo descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idCliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}

	/**
	 * Metodo que permite obtener el valor del atributo consecutivoInicial
	 */
	public Integer getConsecutivoInicial() {
		return consecutivoInicial;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * nomenclatura
	 */
	public void setNomenclatura(String nomenclatura) {
		this.nomenclatura = nomenclatura;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * idCliente
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * consecutivoInicial
	 */
	public void setConsecutivoInicial(Integer consecutivoInicial) {
		this.consecutivoInicial = consecutivoInicial;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idsCampos
	 */
	public List<Long> getIdsCampos() {
		return idsCampos;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * idsCampos
	 */
	public void setIdsCampos(List<Long> idsCampos) {
		this.idsCampos = idsCampos;
	}
}
