package adminfree.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

	/**
	 * Metodo que permite construir el response de respuesta exitoso
	 */
	public static ResponseEntity<Object> getResponseSuccessful(Object body) {
		return ResponseEntity.status(HttpStatus.OK).body(body);
	}

	/**
	 * Metodo que permite construir el response de respuesta OK
	 */
	public static ResponseEntity<Object> getResponseOk() {
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.getReasonPhrase()));
	}

	/**
	 * Metodo que permite construir el response de respuesta BAD REQUEST
	 */
	public static ResponseEntity<Object> getResponseBadRequest(String bussinesMessage) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(bussinesMessage));
	}

	/**
	 * Metodo que permite construir el response de respuesta INTERNAL_SERVER_ERROR
	 */
	public static ResponseEntity<Object> getResponseError(String error) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(error));
	}
}
