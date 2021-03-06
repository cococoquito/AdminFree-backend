package adminfree.services;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import adminfree.business.ArchivoGestionBusiness;
import adminfree.dtos.archivogestion.FiltroSerieDocumentalDTO;
import adminfree.dtos.archivogestion.InitAdminSeriesDocumentalesDTO;
import adminfree.dtos.archivogestion.SerieDocumentalDTO;
import adminfree.dtos.archivogestion.SubSerieDocumentalDTO;
import adminfree.dtos.archivogestion.TipoDocumentalDTO;
import adminfree.dtos.transversal.PaginadorResponseDTO;
import adminfree.utilities.CerrarRecursos;

/**
 * Clase que contiene todos los servicios para el modulo de Archivo de Gestión
 *
 * @author Carlos Andres Diaz
 *
 */
@Service
public class ArchivoGestionService {

	/** DataSource para las conexiones de la BD de AdminFree */
	@Autowired
	private DataSource adminFreeDS;

	/**
	 * Servicio que permite obtener los datos de inicio para el submodulo de series documentales
	 *
	 * @param idCliente, identificador del cliente autenticado
	 * @return Response con los datos necesarios para el submodulo
	 */
	public InitAdminSeriesDocumentalesDTO getInitAdminSeriesDocumentales(Long idCliente) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a consultar los datos iniciales para el submodulo
			return new ArchivoGestionBusiness().getInitAdminSeriesDocumentales(idCliente, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite obtener las series documentales de acuerdo al filtro de busqueda
	 *
	 * @param filtro, DTO que contiene los datos del filtro de busqueda
	 * @return DTO con los datos del response con la lista de series documentales
	 */
	public PaginadorResponseDTO getSeriesDocumentales(FiltroSerieDocumentalDTO filtro) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a consultar las series documentales
			return new ArchivoGestionBusiness().getSeriesDocumentales(filtro, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite obtener todos los tipos documentales parametrizados
	 *
	 * @param idCliente, cada cliente tiene sus propios tipos documentales
	 * @return Lista de tipos documentales asociados al cliente
	 */
	public List<TipoDocumentalDTO> getTiposDocumentales(Long idCliente) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a consultar los tipos documentales
			return new ArchivoGestionBusiness().getTiposDocumentales(idCliente, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite administrar la entidad de series documentales
	 * aplica solamente para CREAR, EDITAR, ELIMINAR
	 *
	 * @param serie, DTO con los datos de la serie documental
	 * @return Objecto con la respuesta del proceso
	 */
	public Object administrarSerieDocumental(SerieDocumentalDTO serie) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procesa la solicitud
			return new ArchivoGestionBusiness().administrarSerieDocumental(serie, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite administrar la entidad de sub-serie documental
	 * aplica solamente para CREAR, EDITAR, ELIMINAR
	 *
	 * @param subserie, DTO con los datos de la sub-serie documental
	 * @return Objecto con la respuesta del proceso
	 */
	public Object administrarSubSerieDocumental(SubSerieDocumentalDTO subserie) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procesa la solicitud
			return new ArchivoGestionBusiness().administrarSubSerieDocumental(subserie, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite obtener las subseries documentales relacionadas a una serie documental
	 *
	 * @param idSerie, identificador de la serie documental
	 * @return lista de subseries documentales relacionadas a una serie documental
	 */
	public List<SubSerieDocumentalDTO> getSubSeriesDocumental(Long idSerie) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a consultar las subseries documentales asociadas a una serie
			return new ArchivoGestionBusiness().getSubSeriesDocumental(idSerie, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}
}
