package adminfree.persistence;

/**
 * Clase que contiene los atributos de un valor para SQL
 * 
 * @author Carlos Andres Diaz
 *
 */
public class ValueSQL {

	/** Es el valor del Value SQL */
	private Object valor;

	/** Es el tipo de dato para este valor cuando es NULA */
	private Integer tipoDato;
	
	/**
	 * Retorna una instancia de este tipo de Clase
	 */
	public static ValueSQL get(Object valor, Integer tipoDato) {
		return new ValueSQL(valor, tipoDato);
	}

	private ValueSQL(Object valor, Integer tipoDato) {
		this.valor = valor;
		this.tipoDato = tipoDato;
	}

	/**
	 * Metodo que permite obtener el valor del atributo valor
	 */
	public Object getValor() {
		return valor;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param valor
	 */
	public void setValor(Object valor) {
		this.valor = valor;
	}

	/**
	 * Metodo que permite obtener el valor del atributo tipoDato
	 */
	public Integer getTipoDato() {
		return tipoDato;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param tipoDato
	 */
	public void setTipoDato(Integer tipoDato) {
		this.tipoDato = tipoDato;
	}
}
