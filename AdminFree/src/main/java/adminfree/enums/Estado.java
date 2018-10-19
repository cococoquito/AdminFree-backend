package adminfree.enums;

/**
 * Enums utilitaria que define constantes para los estados
 *
 * @author Carlos andres diaz
 *
 */
public enum Estado {

	/** Constante que representa el ESTADO ACTIVO */
	ACTIVO(Numero.UNO.getValor(), "Activo"),

	/** Constante que representa el ESTADO INACTIVO */
	INACTIVO(Numero.DOS.getValor(), "Inactivo"),

	/** Constante que representa el ESTADO ANULADO */
	ANULADO(Numero.TRES.getValor(), "Anulado"),

	/** Constante que representa el ESTADO BORRADO */
	BORRADO(Numero.CUATRO.getValor(), "Borrado"),

	/** Constante que representa el ESTADO CERRADO */
	CERRADO(Numero.CINCO.getValor(), "Cerrado");

	private Integer id;
	private String nombre;

	private Estado(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	/**
	 * Metodo que permite obtener el valor del atributo id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Metodo que permite obtener el valor del atributo nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
