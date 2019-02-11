package adminfree.dtos.correspondencia;

import java.io.Serializable;

/**
 * DTO para transportar las nomenclaturas para la pagina de bienvenida
 * 
 * @author Carlos Andres Diaz
 */
public class WelcomeNomenclaturaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador de la nomenclatura */
	private Long idNomenclatura;

	/** Es el texto nomenclatura */
	private String nomenclatura;

	/** Descripcion de la nomenclatura */
	private String descripcion;

	/** Cantidad de consecutivos que han solicitado a esta nomenclatura */
	private Integer cantidadConsecutivos;

	/**
	 * Metodo que permite obtener el valor del atributo idNomenclatura
	 */
	public Long getIdNomenclatura() {
		return idNomenclatura;
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
	 * Metodo que permite obtener el valor del atributo cantidadConsecutivos
	 */
	public Integer getCantidadConsecutivos() {
		return cantidadConsecutivos;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * idNomenclatura
	 */
	public void setIdNomenclatura(Long idNomenclatura) {
		this.idNomenclatura = idNomenclatura;
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
	 * cantidadConsecutivos
	 */
	public void setCantidadConsecutivos(Integer cantidadConsecutivos) {
		this.cantidadConsecutivos = cantidadConsecutivos;
	}
}
