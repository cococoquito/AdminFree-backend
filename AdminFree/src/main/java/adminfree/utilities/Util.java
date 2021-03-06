package adminfree.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import adminfree.dtos.transversal.MessageResponseDTO;
import adminfree.enums.Estado;
import adminfree.enums.Numero;
import adminfree.enums.TipoCampo;

/**
 * Clase que contiene los metodo utilitarios del sistema
 * 
 * @author Carlos Andres Diaz
 *
 */
public class Util {

	/**
	 * Metodo que permite construir el formato de acuerdo al prefijo
	 */
	public static String setPrefijoZeros(String prefijo, String nro) {
		StringBuilder formato = new StringBuilder(prefijo);
		formato.delete(Numero.ZERO.valueI, nro.length());
		formato.append(nro);
		return formato.toString();
	}

	/**
	 * Metodo que permite validar si un valor es null o vacio
	 */
	public static boolean isNull(String valor) {
		return valor == null || valor.trim().length() == Numero.ZERO.valueI.intValue();
	}

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
	 * Metodo que permite obtener el nombre de un tipo de campo
	 */
	public static String getTipoCampoNombre(Integer id) {
		if (id != null) {
			TipoCampo[] tipos = TipoCampo.values();
			for (TipoCampo tipo : tipos) {
				if (tipo.id.equals(id)) {
					return tipo.nombre;
				}
			}
		}
		return null;
	}

	/**
	 * Metodo que permite construir el attachment para la descarga de documentos
	 */
	public static String getAttachmentDocument(String name) {
		StringBuilder attachment = new StringBuilder("attachment;filename=");
		attachment.append(name);
		return attachment.toString();
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
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponseDTO(HttpStatus.OK.getReasonPhrase()));
	}

	/**
	 * Metodo que permite construir el response de respuesta BAD REQUEST
	 */
	public static ResponseEntity<Object> getResponseBadRequest(String bussinesMessage) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponseDTO(bussinesMessage));
	}

	/**
	 * Metodo que permite construir el response de respuesta INTERNAL_SERVER_ERROR 
	 * 
	 * @param metodo, metodo donde se origino el error
	 * @param error, mensaje de la exception lanzada
	 */
	public static ResponseEntity<Object> getResponseError(String metodo, String error) {
		if (error == null || error.trim().length() == Numero.ZERO.valueI.intValue()) {
			error = "Exception lanzada por NullPointerException.";
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponseDTO(metodo + error));
	}
}
