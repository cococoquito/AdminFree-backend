package adminfree.services;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import adminfree.business.EnglishBusiness;
import adminfree.dtos.english.SeriesDTO;
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
	private DataSource adminFreeDS;

	/**
	 * Service que permite crear una serie en el sistema
	 *
	 * @param serie, DTO que contiene los datos de la serie a crear
	 * @return DTO con el identificador de la serie
	 */
	public SeriesDTO crearSerie(SeriesDTO serie) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a crear la serie
			return new EnglishBusiness().crearSerie(serie, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}
}
