package adminfree.dtos.configuraciones;

import java.io.Serializable;

/**
 * DTO que contiene los atributos del campo que le pertenece a una nomenclatura
 * especifica
 *
 * 
 * @author Carlos Andres Diaz
 *
 */
public class NomenclaturaCampoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del registro */
	private Long id;

	/** identificador del campo */
	private Long idCampo;

	/** Es el nombre del campo */
	private String nombreCampo;

	/** Es el tipo de campo */
	private String tipoCampo;

	/**
	 * Identifica si este campo para la nomenclatura tiene consecutivos asociados
	 */
	private boolean tieneConsecutivo;

	/**
	 * Metodo que permite obtener el valor del atributo id
	 */
	public Long getId() {
		return id;
	}

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
	public String getTipoCampo() {
		return tipoCampo;
	}

	/**
	 * Metodo que permite obtener el valor del atributo tieneConsecutivo
	 */
	public boolean isTieneConsecutivo() {
		return tieneConsecutivo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param idCampo
	 */
	public void setIdCampo(Long idCampo) {
		this.idCampo = idCampo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * nombreCampo
	 */
	public void setNombreCampo(String nombreCampo) {
		this.nombreCampo = nombreCampo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * tipoCampo
	 */
	public void setTipoCampo(String tipoCampo) {
		this.tipoCampo = tipoCampo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * tieneConsecutivo
	 */
	public void setTieneConsecutivo(boolean tieneConsecutivo) {
		this.tieneConsecutivo = tieneConsecutivo;
	}
}
