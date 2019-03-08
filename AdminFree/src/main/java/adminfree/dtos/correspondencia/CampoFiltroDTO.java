package adminfree.dtos.correspondencia;

import java.io.Serializable;

/**
 * Contiene los valores de un campo que se utiliza en los
 * componentes que tiene filtro de busqueda
 * 
 * @author Carlos Andres Diaz
 */
public class CampoFiltroDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del campo */
	private Long idCampo;

	/** Es el nombre del campo */
	private String nombreCampo;

	/** Es el tipo de campo */
	private Integer tipoCampo;

	/**
	 * Metodo que permite obtener el valor del atributo idCampo
	 */
	public Long getIdCampo() {
		return idCampo;
	}

	/**
	 * Metodo que permite obtener el valor del atributo nombreCampo
	 */
	public String getNombreCampo() {
		return nombreCampo;
	}

	/**
	 * Metodo que permite obtener el valor del atributo tipoCampo
	 */
	public Integer getTipoCampo() {
		return tipoCampo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idCampo
	 */
	public void setIdCampo(Long idCampo) {
		this.idCampo = idCampo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param nombreCampo
	 */
	public void setNombreCampo(String nombreCampo) {
		this.nombreCampo = nombreCampo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param tipoCampo
	 */
	public void setTipoCampo(Integer tipoCampo) {
		this.tipoCampo = tipoCampo;
	}
}
