package adminfree.constants;

/**
 * Los mensajes a retornar al cliente se utilizan por medio de
 * KEYS, pero alguna veces se necesitan mandar los mensajes
 * con el texto de que sucedio en el proceso de negocio, esta
 * constante contiene estos tipos de mensajes

 * @author Carlos Andres Diaz
 */
public class BusinessMessages {

	/**
	 * Metodo que permite construir el mensaje para el proceso de negocio de validar
	 * si el valor de un campo ya existe en otro consecutivo de correspondencia
	 */
	public static String getMsjValorExisteOtroConsecutivo(String value, String nombreCampo) {
		StringBuilder msj = new StringBuilder("El valor <strong>'");
		msj.append(value);
		msj.append("'</strong> del campo <strong>");
		msj.append(nombreCampo);
		msj.append("</strong> ya se encuentra registrado en otro consecutivo");
		return msj.toString();
	}
}
