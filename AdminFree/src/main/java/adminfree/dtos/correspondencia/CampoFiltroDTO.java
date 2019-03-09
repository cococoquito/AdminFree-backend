package adminfree.dtos.correspondencia;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import adminfree.dtos.configuraciones.ItemDTO;

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

	/** es el valor ingresado para los componentes input o select item */
	private Object inputValue;

	/** es la fecha inicial para el componente fecha */
	private Date dateInicial;

	/** es la fecha final para el componente fecha */
	private Date dateFinal;

	/** Son los items para los campos select items */
	private List<ItemDTO> items;

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

	/**
	 * Metodo que permite obtener el valor del atributo inputValue
	 */
	public Object getInputValue() {
		return inputValue;
	}

	/**
	 * Metodo que permite obtener el valor del atributo dateInicial
	 */
	public Date getDateInicial() {
		return dateInicial;
	}

	/**
	 * Metodo que permite obtener el valor del atributo dateFinal
	 */
	public Date getDateFinal() {
		return dateFinal;
	}

	/**
	 * Metodo que permite obtener el valor del atributo items
	 */
	public List<ItemDTO> getItems() {
		return items;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param inputValue
	 */
	public void setInputValue(Object inputValue) {
		this.inputValue = inputValue;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param dateInicial
	 */
	public void setDateInicial(Date dateInicial) {
		this.dateInicial = dateInicial;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param dateFinal
	 */
	public void setDateFinal(Date dateFinal) {
		this.dateFinal = dateFinal;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param items
	 */
	public void setItems(List<ItemDTO> items) {
		this.items = items;
	}
}
