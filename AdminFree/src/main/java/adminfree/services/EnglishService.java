package adminfree.services;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import adminfree.business.EnglishBusiness;
import adminfree.dtos.english.ChapterDTO;
import adminfree.dtos.english.SentenceDTO;
import adminfree.dtos.english.SerieDTO;
import adminfree.utilities.CerrarRecursos;

/**
 * Clase que contiene todos los servicios para el modulo de Learning English
 *
 * @author Carlos Andres Diaz
 *
 */
@Service
public class EnglishService {

	/** DataSource para las conexiones de la BD de AdminFree */
	@Autowired
	private DataSource learningEnglishDS;

	/**
	 * Service que permite crear una serie en el sistema
	 * @param serie, DTO que contiene los datos de la serie a crear
	 */
	public void createSerie(SerieDTO serie) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD para el esquema LEARNING_ENGLISH
			connection = this.learningEnglishDS.getConnection();

			// se procede a crear la serie
			new EnglishBusiness().createSerie(serie, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Service que permite cargar las series parametrizadas en el sistema
	 */
	public List<SerieDTO> getSeries() throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD para el esquema LEARNING_ENGLISH
			connection = this.learningEnglishDS.getConnection();

			// se procede listar las series
			return new EnglishBusiness().getSeries(connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Service que permite obtener los detalles de una serie
	 * @param idSerie, identificador de la serie
	 * @return DTO con el detalle de la serie
	 */
	public SerieDTO getDetailSerie(Long idSerie) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD para el esquema LEARNING_ENGLISH
			connection = this.learningEnglishDS.getConnection();

			// se procede a consultar los detalles de la serie
			return new EnglishBusiness().getDetailSerie(idSerie, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Service que permite agregar una nueva temporada
	 * @param idSerie, identificador de la serie
	 * @return DTO con el detalle de la serie
	 */
	public SerieDTO addSeason(Long idSerie) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD para el esquema LEARNING_ENGLISH
			connection = this.learningEnglishDS.getConnection();

			// se procede agregar una nueva temporada
			return new EnglishBusiness().addSeason(idSerie, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Service que permite agregar un capitulo a una temporada
	 * @param chapter, DTO con los datos del capitulo
	 * @return DTO con el detalle de la serie
	 */
	public SerieDTO addChapter(ChapterDTO chapter) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD para el esquema LEARNING_ENGLISH
			connection = this.learningEnglishDS.getConnection();

			// se procede agregar el nuevo capitulo
			return new EnglishBusiness().addChapter(chapter, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Service que permite consultar el detalle del capitulo
	 * @param idChapter, identificador del capitulo
	 * @return DTO con los datos del capitulo
	 */
	public ChapterDTO getDetailChapter(Long idChapter) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD para el esquema LEARNING_ENGLISH
			connection = this.learningEnglishDS.getConnection();

			// se procede a consultar el detalle del capitulo
			return new EnglishBusiness().getDetailChapter(idChapter, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Service que permite crear una sentencia en el sistema
	 * @param sentence, DTO con los datos de la sentencia
	 * @return detalle del capitulo con todas sus sentencias
	 */
	public ChapterDTO createSentence(SentenceDTO sentence) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD para el esquema LEARNING_ENGLISH
			connection = this.learningEnglishDS.getConnection();

			// se procede a insertar la sentencia
			return new EnglishBusiness().createSentence(sentence, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Service que permite editar una sentencia en el sistema
	 * @param sentence, DTO con los datos de la sentencia
	 * @return detalle del capitulo con todas sus sentencias
	 */
	public ChapterDTO editSentence(SentenceDTO sentence) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD para el esquema LEARNING_ENGLISH
			connection = this.learningEnglishDS.getConnection();

			// se procede a editar la sentencia
			return new EnglishBusiness().editSentence(sentence, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}
}
