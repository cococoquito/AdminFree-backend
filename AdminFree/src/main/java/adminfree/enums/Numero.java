package adminfree.enums;

/**
 * 
 * Enums que contiene los valores númericos del sistema
 * 
 * @author Carlos Andres Diaz
 *
 */
public enum Numero {

	ZERO(0), UNO(1), DOS(2),
	TRES(3), CUATRO(4), CINCO(5),
	SEIS(6), SIETE(7), OCHO(8),
	NUEVE(9), DIEZ(10), ONCE(11),
	DOCE(12), TRECE(13), CATORCE(14),
	QUINCE(15), DIECISEIS(16), DIECISIETE(17),
	DIECIOCHO(18), DIECINUEVE(19), VEINTE(20),
	TREINTA(30), MIL(1000);

	public final Integer value;

	private Numero(Integer value) {
		this.value = value;
	}
}
