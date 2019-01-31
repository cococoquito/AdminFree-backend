package adminfree.dtos.correspondencia;

import java.io.Serializable;
import java.util.List;

import adminfree.dtos.configuraciones.ItemDTO;

/**
 * DTO que se utiliza para consultar los campos relacionados
 * a la nomenclatura seleccionada cuando se solicita o edita
 * los consecutivos de correspondencia
 *
 * @author Carlos Andres Diaz
 *
 */
public class CampoEntradaDetalleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del campo */
	private Long id;

	/** Identificador de la siguiente tabla NOMENCLATURAS_CAMPOS_ENTRADA.ID_NOME_CAMPO */
	private Long idCampoNomenclatura;

	/** nombre del campo */
	private String nombre;

	/** descripcion del campo */
	private String descripcion;

	/** identifica el tipo de campo */
	private Integer tipoCampo;

	/** son las restricciones que tiene este campo */
	private List<String> restricciones;

	/** Son los items para este campo, solo aplica para lista desplegable */
	private List<ItemDTO> items;

	/**
	 * Metodo que permite obtener el valor del atributo id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Metodo que permite obtener el valor del atributo nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo que permite obtener el valor del atributo descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Metodo que permite obtener el valor del atributo tipoCampo
	 */
	public Integer getTipoCampo() {
		return tipoCampo;
	}

	/**
	 * Metodo que permite obtener el valor del atributo restricciones
	 */
	public List<String> getRestricciones() {
		return restricciones;
	}

	/**
	 * Metodo que permite obtener el valor del atributo items
	 */
	public List<ItemDTO> getItems() {
		return items;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	 * tipoCampo
	 */
	public void setTipoCampo(Integer tipoCampo) {
		this.tipoCampo = tipoCampo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * restricciones
	 */
	public void setRestricciones(List<String> restricciones) {
		this.restricciones = restricciones;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param items
	 */
	public void setItems(List<ItemDTO> items) {
		this.items = items;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idCampoNomenclatura
	 */
	public Long getIdCampoNomenclatura() {
		return idCampoNomenclatura;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * idCampoNomenclatura
	 */
	public void setIdCampoNomenclatura(Long idCampoNomenclatura) {
		this.idCampoNomenclatura = idCampoNomenclatura;
	}
}
