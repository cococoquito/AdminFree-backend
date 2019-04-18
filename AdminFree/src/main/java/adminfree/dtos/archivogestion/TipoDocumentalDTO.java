package adminfree.dtos.archivogestion;

import java.io.Serializable;

/**
 * DTO que contiene los atributos de un tipo documental para serie o subserie
 *
 * @author Carlos Andres Diaz
 */
public class TipoDocumentalDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del tipo documental */
	private Integer id;

	/** Nombre del tipo documental */
	private String nombre;

	/** Identifica que tipo de accion se va ralizar sobre el cliente */
	private String tipoEvento;

	/**
	 * Metodo que permite obtener el valor del atributo id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Metodo que permite obtener el valor del atributo nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo que permite obtener el valor del atributo tipoEvento
	 */
	public String getTipoEvento() {
		return tipoEvento;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param tipoEvento
	 */
	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
}
