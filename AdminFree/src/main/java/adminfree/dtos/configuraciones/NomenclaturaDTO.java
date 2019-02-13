package adminfree.dtos.configuraciones;

import java.io.Serializable;
import java.util.ArrayList;
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

	/** Identificador del cliente */
	private Long idCliente;

	/** Nombre abreviado de la nomenclatura */
	private String nomenclatura;

	/** Descripcion de la nomenclatura */
	private String descripcion;

	/** Nro donde inicia el consecutivo a generar */
	private Integer consecutivoInicial;

	/** Es la cantidad de consecutivos solicitados para esta nomenclatura */
	private Integer cantConsecutivos;

	/** Indica si la nomenclatura esta asociada a un consecutivo */
	private boolean tieneConsecutivos;

	/** Son los campos asociados de la nomenclatura */
	private List<NomenclaturaCampoDTO> campos;

	/**
	 * Metodo que permite agregar un campo
	 */
	public void agregarCampos(NomenclaturaCampoDTO campo) {
		if (this.campos == null) {
			this.campos = new ArrayList<>();
		}
		this.campos.add(campo);
	}

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
	 * Metodo que permite obtener el valor del atributo tieneConsecutivos
	 */
	public boolean isTieneConsecutivos() {
		return tieneConsecutivos;
	}

	/**
	 * Metodo que permite obtener el valor del atributo campos
	 */
	public List<NomenclaturaCampoDTO> getCampos() {
		return campos;
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
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * tieneConsecutivos
	 */
	public void setTieneConsecutivos(boolean tieneConsecutivos) {
		this.tieneConsecutivos = tieneConsecutivos;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param campos
	 */
	public void setCampos(List<NomenclaturaCampoDTO> campos) {
		this.campos = campos;
	}

	/**
	 * Metodo que permite obtener el valor del atributo cantConsecutivos
	 */
	public Integer getCantConsecutivos() {
		return cantConsecutivos;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param cantConsecutivos
	 */
	public void setCantConsecutivos(Integer cantConsecutivos) {
		this.cantConsecutivos = cantConsecutivos;
	}
}
