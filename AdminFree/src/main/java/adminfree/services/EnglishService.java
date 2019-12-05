package adminfree.services;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import adminfree.business.EnglishBusiness;
import adminfree.dtos.english.ChapterDTO;
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
	 *
	 * @param serie, DTO que contiene los datos de la serie a crear
	 * @return DTO con el identificador de la serie
	 */
	public SerieDTO crearSerie(SerieDTO serie) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD para el esquema LEARNING_ENGLISH
			connection = this.learningEnglishDS.getConnection();

			// se procede a crear la serie
			return new EnglishBusiness().crearSerie(serie, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Service para asociar la imagen a la serie
	 *
	 * @param img, es la imagen para asociar
	 * @param idSerie, identificador de la serie asociar la imagen
	 */
	public void downloadImgSerie(byte[] img ,String idSerie) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD para el esquema LEARNING_ENGLISH
			connection = this.learningEnglishDS.getConnection();

			// se procede asociar la imagen
			new EnglishBusiness().downloadImgSerie(img, idSerie, connection);
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
}
