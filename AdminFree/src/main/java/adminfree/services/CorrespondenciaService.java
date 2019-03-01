package adminfree.services;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import adminfree.business.CorrespondenciaBusiness;
import adminfree.dtos.correspondencia.CampoEntradaDetalleDTO;
import adminfree.dtos.correspondencia.DocumentoDTO;
import adminfree.dtos.correspondencia.FiltroConsecutivosAnioActualDTO;
import adminfree.dtos.correspondencia.InitConsecutivosAnioActualDTO;
import adminfree.dtos.correspondencia.InitSolicitarConsecutivoDTO;
import adminfree.dtos.correspondencia.SolicitudConsecutivoDTO;
import adminfree.dtos.correspondencia.SolicitudConsecutivoResponseDTO;
import adminfree.dtos.correspondencia.WelcomeInitDTO;
import adminfree.dtos.transversal.MessageResponseDTO;
import adminfree.dtos.transversal.PaginadorResponseDTO;
import adminfree.utilities.CerrarRecursos;

/**
 * Clase que contiene todos los servicios para el modulo de Correspondencia
 * 
 * @author Carlos Andres Diaz
 *
 */
@Service
public class CorrespondenciaService {

	/** DataSource para las conexiones de la BD de AdminFree */
	@Autowired
	private DataSource adminFreeDS;

	/**
	 * Servicio que permite obtener los campos de la nomenclatura
	 * 
	 * @param idNomenclatura, identificador de la nomenclatura
	 * @return DTO con los campos de la nomenclatura
	 */
	public List<CampoEntradaDetalleDTO> getCamposNomenclatura(Long idNomenclatura) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a consultar los campos de la nomenclatura
			return new CorrespondenciaBusiness().getCamposNomenclatura(idNomenclatura, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite obtener los datos iniciales para las
	 * solicitudes de consecutivos de correspondencia
	 *
	 * @param idCliente, identificador del cliente autenticado
	 * @return DTO con los datos iniciales
	 */
	public InitSolicitarConsecutivoDTO getInitSolicitarConsecutivo(Long idCliente) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a configurar los datos iniciales para este modulo
			return new CorrespondenciaBusiness().getInitSolicitarConsecutivo(idCliente, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite validar los campos de ingreso de informacion para el proceso de 
	 * solicitar o editar un consecutivo de correspondencia
	 *
	 * @param solicitud, DTO con los datos de la solicitud
	 * @return Lista de mensajes con los errores encontrados solo si lo hay
	 */
	public List<MessageResponseDTO> validarCamposIngresoInformacion(SolicitudConsecutivoDTO solicitud) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a realizar las validaciones para los campos de ingreso
			return new CorrespondenciaBusiness().validarCamposIngresoInformacion(solicitud, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite soportar el proceso de negocio de solicitar
	 * un consecutivo de correspondencia para una nomenclatura
	 *
	 * @param solicitud, DTO que contiene los datos de la solicitud
	 * @return DTO con los datos de la respuesta
	 */
	public synchronized SolicitudConsecutivoResponseDTO solicitarConsecutivo(SolicitudConsecutivoDTO solicitud) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a consultar el consecutivo de correspondencia
			return new CorrespondenciaBusiness().solicitarConsecutivo(solicitud, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite obtener los datos para la pagina de bienvenida
	 *
	 * @param idCliente, identificador del cliente autenticado
	 * @return DTO con los datos de bienvenida
	 */
	public WelcomeInitDTO getDatosBienvenida(Long idCliente) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a consultar los datos de la pagina de bienvenida
			return new CorrespondenciaBusiness().getDatosBienvenida(idCliente, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio para el cargue del documento asociado a un consecutivo
	 *
	 * @param datos, Contiene los datos del cargue del documento
	 * @return lista de documentos asociados al consecutivo
	 */
	public List<DocumentoDTO> cargarDocumento(DocumentoDTO datos) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a realizar el cargue del documento
			return new CorrespondenciaBusiness().cargarDocumento(datos, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio para eliminar un documento asociado al consecutivo
	 *
	 * @param datos, Contiene los datos del documento eliminar
	 * @return lista de documentos asociados al consecutivo
	 */
	public List<DocumentoDTO> eliminarDocumento(DocumentoDTO datos) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a eliminar el documento
			return new CorrespondenciaBusiness().eliminarDocumento(datos, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite obtener los consecutivos del anio actual de acuerdo al
	 * filtro de busqueda
	 *
	 * @param filtro, DTO que contiene los valores del filtro de busqueda
	 * @return DTO con la lista de consecutivos paginados y su cantidad total
	 */
	public PaginadorResponseDTO getConsecutivosAnioActual(FiltroConsecutivosAnioActualDTO filtro) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a consultar los consecutivos
			return new CorrespondenciaBusiness().getConsecutivosAnioActual(filtro, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite obtener los datos iniciales para el 
	 * submodulo de Consecutivos de correspondencia solicitados
	 * para el anio actual
	 *
	 * @param idCliente, identificador del cliente autenticado
	 * @return DTO con los datos iniciales
	 */
	public InitConsecutivosAnioActualDTO getInitConsecutivosAnioActual(Long idCliente) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a consultar los datos para el submodulo de consecutivos solicitados
			return new CorrespondenciaBusiness().getInitConsecutivosAnioActual(idCliente, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}
}
