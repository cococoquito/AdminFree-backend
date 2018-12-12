package adminfree.dtos.configuraciones;

import java.io.Serializable;
import java.util.List;

/**
 * DTO que contiene los atributos para los campos de ingreso de informacion
 * 
 * @author Carlos Andres Diaz
 *
 */
public class CampoIngresoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Es el identificador del campo de ingreso */
	private Long id;

	/** Nombre del campo de ingreso */
	private String nombre;

	/** Descripcion del campo de ingreso */
	private String descripcion;

	/** Identifica el tipo de campo (input,list,check,date) */
	private Integer tipoCampo;

	/** Nombre del tipo de campo */
	private String tipoCampoNombre;

	/** Los campos de ingreso estan asociados a un cliente */
	private Long idCliente;

	/** Son las restricciones que contiene este campo */
	private List<RestriccionDTO> restricciones;

	/** Son los items para este campo, solo aplica para lista desplegable */
	private List<ItemDTO> items;

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
	 * Metodo que permite obtener el valor del atributo nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo que permite obtener el valor del atributo descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Metodo que permite obtener el valor del atributo tipoCampo
	 */
	public Integer getTipoCampo() {
		return tipoCampo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * tipoCampo
	 */
	public void setTipoCampo(Integer tipoCampo) {
		this.tipoCampo = tipoCampo;
	}

	/**
	 * Metodo que permite obtener el valor del atributo tipoCampoNombre
	 */
	public String getTipoCampoNombre() {
		return tipoCampoNombre;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * tipoCampoNombre
	 */
	public void setTipoCampoNombre(String tipoCampoNombre) {
		this.tipoCampoNombre = tipoCampoNombre;
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
	 * Metodo que permite obtener el valor del atributo restricciones
	 */
	public List<RestriccionDTO> getRestricciones() {
		return restricciones;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * restricciones
	 */
	public void setRestricciones(List<RestriccionDTO> restricciones) {
		this.restricciones = restricciones;
	}

	/**
	 * Metodo que permite obtener el valor del atributo items
	 */
	public List<ItemDTO> getItems() {
		return items;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param items
	 */
	public void setItems(List<ItemDTO> items) {
		this.items = items;
	}
}
