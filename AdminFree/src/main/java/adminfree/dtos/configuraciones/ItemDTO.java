package adminfree.dtos.configuraciones;

import java.io.Serializable;

/**
 * DTO que representa un ITEM para la lista desplegable
 * 
 * @author Carlos Andres Diaz
 *
 */
public class ItemDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del Item para los campos lista desplegable */
	private Long id;

	/** Identificador del campo asociado para este item */
	private Long idCampo;

	/** Valor del Item */
	private String valor;

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
	 * Metodo que permite obtener el valor del atributo idCampo
	 */
	public Long getIdCampo() {
		return idCampo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param idCampo
	 */
	public void setIdCampo(Long idCampo) {
		this.idCampo = idCampo;
	}

	/**
	 * Metodo que permite obtener el valor del atributo valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param valor
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
}
