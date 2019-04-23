package adminfree.dtos.archivogestion;

import java.io.Serializable;
import java.util.Date;

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

	/** Tiempo en archivo gestion */
	private Integer AG;

	/** Tiempo en archivo central */
	private Integer AC;

	/** Conservacion total */
	private boolean CT;

	/** Microfilmacion / digitalizacion */
	private boolean M;

	/** Seleccion */
	private boolean S;

	/** Eliminacion */
	private boolean E;

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
	 * Metodo que permite obtener el valor del atributo aG
	 */
	public Integer getAG() {
		return AG;
	}

	/**
	 * Metodo que permite obtener el valor del atributo aC
	 */
	public Integer getAC() {
		return AC;
	}

	/**
	 * Metodo que permite obtener el valor del atributo cT
	 */
	public boolean isCT() {
		return CT;
	}

	/**
	 * Metodo que permite obtener el valor del atributo m
	 */
	public boolean isM() {
		return M;
	}

	/**
	 * Metodo que permite obtener el valor del atributo s
	 */
	public boolean isS() {
		return S;
	}

	/**
	 * Metodo que permite obtener el valor del atributo e
	 */
	public boolean isE() {
		return E;
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
	 * para el atributo @param aG
	 */
	public void setAG(Integer aG) {
		AG = aG;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param aC
	 */
	public void setAC(Integer aC) {
		AC = aC;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param cT
	 */
	public void setCT(boolean cT) {
		CT = cT;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param m
	 */
	public void setM(boolean m) {
		M = m;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param s
	 */
	public void setS(boolean s) {
		S = s;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param e
	 */
	public void setE(boolean e) {
		E = e;
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
}
