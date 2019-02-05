package adminfree.dtos.correspondencia;

import java.io.Serializable;

/**
 * DTO que se utiliza para ver el detalle de la nomenclatura para los procesos
 * de negocio de solicitar o editar consecutivos de correspondencia
 * 
 * @author Carlos Andres Diaz
 *
 */
public class NomenclaturaDetalleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador de la nomenclatura */
	private Long id;

	/** nombre abreviada de la nomenclatura */
	private String nomenclatura;

	/** Descripcion de la nomenclatura */
	private String descripcion;

	/** Consecutivo inicial asociada a la nomenclatura */
	private Integer consecutivoInicial;

	/** Es el ultimo consecutivo solicitado */
	private Integer ultimoConsecutivoSolicitado;

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
	 * consecutivoInicial
	 */
	public void setConsecutivoInicial(Integer consecutivoInicial) {
		this.consecutivoInicial = consecutivoInicial;
	}

	/**
	 * Metodo que permite obtener el valor del atributo ultimoConsecutivoSolicitado
	 */
	public Integer getUltimoConsecutivoSolicitado() {
		return ultimoConsecutivoSolicitado;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * ultimoConsecutivoSolicitado
	 */
	public void setUltimoConsecutivoSolicitado(Integer ultimoConsecutivoSolicitado) {
		this.ultimoConsecutivoSolicitado = ultimoConsecutivoSolicitado;
	}

}
