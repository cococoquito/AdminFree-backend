package adminfree.dtos.archivogestion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Contiene los datos comunes entre la serie y subserie
 *
 * @author Carlos Andres Diaz
 */
public class Documental implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Codigo de la serie o subserie */
	private String codigo;

	/** Nombre de la serie o subserie */
	private String nombre;

	/** Tiempo en archivo gestion (AG)*/
	private Integer tiempoArchivoGestion;

	/** Tiempo en archivo central (AC) */
	private Integer tiempoArchivoCentral;

	/** Conservacion total (CT)*/
	private boolean conservacionTotal;

	/** Microfilmacion / digitalizacion (M) */
	private boolean microfilmacion;

	/** Seleccion (S)*/
	private boolean seleccion;

	/** Eliminacion (E)*/
	private boolean eliminacion;

	/** Procedimiento de la serie o sub-serie */
	private String procedimiento;

	/** Fecha de creacion de la serie o sub-serie */
	private Date fechaCreacion;

	/** Identificador del usuario para la creacion de la serie o sub-serie */
	private Integer idUsuarioCreacion;

	/** Identificador del cliente asociado a esta serie */
	private Integer idCliente;

	/** Identifica que tipo de accion se va realizar sobre la serie o subserie */
	private String tipoEvento;

	/** Son los tipos documentales que soporta la serie o subserie */
	private List<TipoDocumentalDTO> tiposDocumentales;

	/**
	 * Metodo que permite agregar un tipo documental para la serie o subserie
	 */
	public void agregarTipoDocumental(TipoDocumentalDTO tipoDoc) {
		if (this.tiposDocumentales == null) {
			this.tiposDocumentales = new ArrayList<>();
		}
		this.tiposDocumentales.add(tipoDoc);
	}

	/**
	 * Metodo que permite obtener el valor del atributo codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Metodo que permite obtener el valor del atributo nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo que permite obtener el valor del atributo procedimiento
	 */
	public String getProcedimiento() {
		return procedimiento;
	}

	/**
	 * Metodo que permite obtener el valor del atributo fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idUsuarioCreacion
	 */
	public Integer getIdUsuarioCreacion() {
		return idUsuarioCreacion;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idCliente
	 */
	public Integer getIdCliente() {
		return idCliente;
	}

	/**
	 * Metodo que permite obtener el valor del atributo tipoEvento
	 */
	public String getTipoEvento() {
		return tipoEvento;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param codigo
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param procedimiento
	 */
	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param fechaCreacion
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idUsuarioCreacion
	 */
	public void setIdUsuarioCreacion(Integer idUsuarioCreacion) {
		this.idUsuarioCreacion = idUsuarioCreacion;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idCliente
	 */
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param tipoEvento
	 */
	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	/**
	 * Metodo que permite obtener el valor del atributo tiposDocumentales
	 */
	public List<TipoDocumentalDTO> getTiposDocumentales() {
		return tiposDocumentales;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param tiposDocumentales
	 */
	public void setTiposDocumentales(List<TipoDocumentalDTO> tiposDocumentales) {
		this.tiposDocumentales = tiposDocumentales;
	}

	/**
	 * Metodo que permite obtener el valor del atributo tiempoArchivoGestion
	 */
	public Integer getTiempoArchivoGestion() {
		return tiempoArchivoGestion;
	}

	/**
	 * Metodo que permite obtener el valor del atributo tiempoArchivoCentral
	 */
	public Integer getTiempoArchivoCentral() {
		return tiempoArchivoCentral;
	}

	/**
	 * Metodo que permite obtener el valor del atributo conservacionTotal
	 */
	public boolean isConservacionTotal() {
		return conservacionTotal;
	}

	/**
	 * Metodo que permite obtener el valor del atributo microfilmacion
	 */
	public boolean isMicrofilmacion() {
		return microfilmacion;
	}

	/**
	 * Metodo que permite obtener el valor del atributo seleccion
	 */
	public boolean isSeleccion() {
		return seleccion;
	}

	/**
	 * Metodo que permite obtener el valor del atributo eliminacion
	 */
	public boolean isEliminacion() {
		return eliminacion;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param tiempoArchivoGestion
	 */
	public void setTiempoArchivoGestion(Integer tiempoArchivoGestion) {
		this.tiempoArchivoGestion = tiempoArchivoGestion;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param tiempoArchivoCentral
	 */
	public void setTiempoArchivoCentral(Integer tiempoArchivoCentral) {
		this.tiempoArchivoCentral = tiempoArchivoCentral;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param conservacionTotal
	 */
	public void setConservacionTotal(boolean conservacionTotal) {
		this.conservacionTotal = conservacionTotal;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param microfilmacion
	 */
	public void setMicrofilmacion(boolean microfilmacion) {
		this.microfilmacion = microfilmacion;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param seleccion
	 */
	public void setSeleccion(boolean seleccion) {
		this.seleccion = seleccion;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param eliminacion
	 */
	public void setEliminacion(boolean eliminacion) {
		this.eliminacion = eliminacion;
	}
}
