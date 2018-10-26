package adminfree.utilities;

import adminfree.enums.Estado;

/**
 * Clase que contiene los metodo utilitarios del sistema
 * 
 * @author Carlos Andres Diaz
 *
 */
public class Util {

	/**
	 * Metodo que permite obtener el nombre de un estado de acuerdo a su ID
	 */
	public static String getEstadoNombre(Integer id) {
		if (id != null) {
			Estado[] estados = Estado.values();
			for (Estado estado : estados) {
				if (estado.id.equals(id)) {
					return estado.nombre;
				}
			}
		}
		return null;
	}
}
