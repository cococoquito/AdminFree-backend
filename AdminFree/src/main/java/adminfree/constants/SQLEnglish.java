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

	/** SQL para obtener el detalle de la serie */
	public static final String GET_DETAIL_SERIE = "SELECT S.NAME,S.URL,S.IMG,GROUP_CONCAT(SE.ID_SEASON ORDER BY SE.ID_SEASON ASC SEPARATOR ',') AS SEASONS FROM SERIES S LEFT JOIN SEASONS SE ON(SE.SERIE=S.ID_SERIE)WHERE ID_SERIE=? GROUP BY S.ID_SERIE";

	/** SQL para agregar una nueva temporada */
	public static final String ADD_SEASON = "INSERT INTO SEASONS(SERIE)VALUES(?)";

	/** SQL para agregar un nuevo capitulo */
	public static final String ADD_CHAPTER = "INSERT INTO CHAPTERS(SEASON,NAME,URL)VALUES(?,?,?)";

	/** SQL para agregar una sentencia */
	public static final String ADD_SENTENCE = "INSERT INTO SENTENCES(CHAPTER,SPANISH,ENGLISH)VALUES(?,?,?)";

	/** SQL para asociar el sonido a la sentence */
	public static final String ASOCIAR_SOUND_SENTENCE = "UPDATE SENTENCES SET AUDIO=?,AUDIO_NAME=? WHERE ID_SENTENCE=?";

	/** SQL para obtener el detalle del capitulo */
	public static final String GET_DETAIL_CHAPTER = "SELECT CH.NAME,CH.URL,SE.ID_SENTENCE,SE.SPANISH,SE.ENGLISH,SE.AUDIO,SE.AUDIO_NAME FROM CHAPTERS CH LEFT JOIN SENTENCES SE ON(SE.CHAPTER=CH.ID_CHAPTER)WHERE CH.ID_CHAPTER=? ORDER BY SE.ID_SENTENCE";

	/** SQL para actualizar todos los campos de una sentencia*/
	public static final String UPDATE_SENTENCE_ALL = "UPDATE SENTENCES SET SPANISH=?,ENGLISH=?,AUDIO=?,AUDIO_NAME=? WHERE ID_SENTENCE=?";

	/** SQL para actualizar solo los campos spanish, english*/
	public static final String UPDATE_SENTENCE_ONLY_STATEMENT = "UPDATE SENTENCES SET SPANISH=?,ENGLISH=? WHERE ID_SENTENCE=?";

	/**
	 * Metodo que permite construir el SQL para obtener el detalle de la serie
	 */
	public static String getSQLChaptersSeason(String idsSeason) {
		StringBuilder sql = new StringBuilder("SELECT ID_CHAPTER,NAME,SEASON FROM CHAPTERS WHERE SEASON IN(");
		sql.append(idsSeason);
		sql.append(")ORDER BY ID_CHAPTER ASC");
		return sql.toString();
	}
}
