package adminfree.dtos.correspondencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO que es utilizado para los procesos de negocio de solicitar consecutivos
 * de correspondencia y validar los campos de entrada de informacion (paso 2)
 *
 * @author Carlos Andres Diaz
 */
public class SolicitudConsecutivoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del cliente autenticado */
	private Long idCliente;

	/** Identificador de la nomenclatura seleccionada */
	private Long idNomenclatura;

	/** Identificador del usuario quien solicita el consecutivo */
	private Long idUsuario;

	/** Son los valores ingresados para la solicitud o edicion del consecutivo */
	private List<CampoEntradaValueDTO> valores;

	/**
	 * Metodo que permite agregar un valor para esta solicitud
	 */
	public void agregarValor(CampoEntradaValueDTO valor) {
		if (this.valores == null) {
			this.valores = new ArrayList<>();
		}
		this.valores.add(valor);
	}

	/**
	 * Metodo que permite obtener el valor del atributo idCliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idNomenclatura
	 */
	public Long getIdNomenclatura() {
		return idNomenclatura;
	}

	/**
	 * Metodo que permite obtener el valor del atributo valores
	 */
	public List<CampoEntradaValueDTO> getValores() {
		return valores;
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
	 * idNomenclatura
	 */
	public void setIdNomenclatura(Long idNomenclatura) {
		this.idNomenclatura = idNomenclatura;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param valores
	 */
	public void setValores(List<CampoEntradaValueDTO> valores) {
		this.valores = valores;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idUsuario
	 */
	public Long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * idUsuario
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
}
