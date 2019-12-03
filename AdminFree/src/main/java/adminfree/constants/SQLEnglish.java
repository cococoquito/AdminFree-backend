package adminfree.constants;

/**
 * Clase constante que contiene los DML Y DDL para el modulo de Learning English
 *
 * @author Carlos Andres Diaz
 */
public class SQLEnglish {

	/** SQL para la creacion de la serie */
	public static final String CREAR_SERIE = "INSERT INTO SERIES (NAME,URL) VALUES (?,?)";

	/** SQL para asociar la imagen a la serie */
	public static final String ASOCIAR_IMG_SERIE = "UPDATE SERIES SET IMG=? WHERE ID_SERIE=?";

	/** SQL para listar las series parametrizadas en el sistema */
	public static final String GET_SERIES = "SELECT ID_SERIE,NAME,IMG FROM SERIES";
}
