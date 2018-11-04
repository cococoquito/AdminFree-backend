package adminfree.utilities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.UUID;

import adminfree.enums.Numero;

/**
 * Clase para generacion de TOKEN del sistema
 * 
 * @author Carlos Andres Diaz
 *
 */
public class EstrategiaCriptografica {

	/** constante que representa la encriptacion por MD5* */
	private static String ENCRIPTACION_MD5 = "MD5";
	private static EstrategiaCriptografica instance;
	
	/**
	 * Metodo que permite retornar una instancia unica de esta clase
	 */
	public static EstrategiaCriptografica get() {
		if (instance == null) {
			instance = new EstrategiaCriptografica();
		}
		return instance;
	}
	
	/**
	 * No se puede instanciar desde fuera
	 */
	private EstrategiaCriptografica() {}

	/**
	 * Metodo que permite encriptar la clave de ingreso de ADMINFREE
	 * 
	 * @param clave, es el password de ingreso
	 * @param securityPostPass, se utiliza para el poss security de clave
	 * @return clave encriptada de acuerdo al negocio
	 */
	public String encriptarPassword(String clave, String securityPostPass) throws Exception {
		return encriptarMD5(encriptarMD5(clave) + securityPostPass);
	}

	/**
	 * Metodo que permite generar un TOKEN
	 */
	public String generarToken() throws Exception {
		return encriptarMD5(generateUUID());
	}
	
	/**
	 * Metodo que permite generar un nuevo TOKEN de autenticacion
	 * 
	 * @param user, es el usuario de las credenciales de autenticacion
	 * @param clave, es la clave de las credenciales de autenticacion
	 * @param postToken, es postToken del TOKEN a generar
	 * @return nuevo TOKEN listo a ser utilizado
	 */
	public String generarTokenAuth(String user, String clave, String postToken) throws Exception {
		StringBuilder auth = new StringBuilder();
		auth.append(user).append(clave).append(postToken);
		return encriptarMD5(auth.toString());
	}

	/**
	 * Metodo que permite la encriptacion una cadena
	 * 
	 * @param entrada, es la cadena a encriptar
	 * @return cadena encriptada por MD5
	 */
	private String encriptarMD5(String entrada) throws Exception {
		MessageDigest md = MessageDigest.getInstance(ENCRIPTACION_MD5);
		byte[] messageDigest = md.digest(entrada.getBytes());
		BigInteger number = new BigInteger(1, messageDigest);
		String hashtext = number.toString(16);
		final String ZERO = Numero.ZERO.value.toString();
		while (hashtext.length() < 32) {
			hashtext = ZERO + hashtext;
		}
		return hashtext;
	}

	/**
	 * Metodo que permite generar UUID
	 */
	private String generateUUID() {
		return UUID.randomUUID().toString();
	}
}
