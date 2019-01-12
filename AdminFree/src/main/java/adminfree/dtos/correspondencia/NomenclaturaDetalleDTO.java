package adminfree.dtos.correspondencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contiene el detalle de la nomenclatura para el modulo de correspondencia
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

	/** Cantidad de consecutivos solicitados para la nomenclatura */
	private Integer cantidadConsecutivos;

	/** Lista de campos de entrada informacion asociada a la nomenclatura */
	private List<NomenclaturaDetalleCamposDTO> campos;

	/**
	 * Metodo que permite agregar un campo para esta nomenclatura
	 */
	public void agregarCampo(NomenclaturaDetalleCamposDTO campo) {
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
	 * Metodo que permite obtener el valor del atributo consecutivoInicial
	 */
	public Integer getConsecutivoInicial() {
		return consecutivoInicial;
	}

	/**
	 * Metodo que permite obtener el valor del atributo cantidadConsecutivos
	 */
	public Integer getCantidadConsecutivos() {
		return cantidadConsecutivos;
	}

	/**
	 * Metodo que permite obtener el valor del atributo campos
	 */
	public List<NomenclaturaDetalleCamposDTO> getCampos() {
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
	 * consecutivoInicial
	 */
	public void setConsecutivoInicial(Integer consecutivoInicial) {
		this.consecutivoInicial = consecutivoInicial;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * cantidadConsecutivos
	 */
	public void setCantidadConsecutivos(Integer cantidadConsecutivos) {
		this.cantidadConsecutivos = cantidadConsecutivos;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param campos
	 */
	public void setCampos(List<NomenclaturaDetalleCamposDTO> campos) {
		this.campos = campos;
	}
}
