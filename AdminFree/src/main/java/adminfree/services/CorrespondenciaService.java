package adminfree.services;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import adminfree.business.CorrespondenciaBusiness;
import adminfree.dtos.configuraciones.ItemDTO;
import adminfree.dtos.correspondencia.ActivarAnularConsecutivoDTO;
import adminfree.dtos.correspondencia.CampoEntradaDetalleDTO;
import adminfree.dtos.correspondencia.CampoFiltroDTO;
import adminfree.dtos.correspondencia.ConsecutivoDetalleDTO;
import adminfree.dtos.correspondencia.ConsecutivoEdicionDTO;
import adminfree.dtos.correspondencia.DocumentoDTO;
import adminfree.dtos.correspondencia.FiltroConsecutivosDTO;
import adminfree.dtos.correspondencia.InitConsecutivosAnioActualDTO;
import adminfree.dtos.correspondencia.InitMisConsecutivosDTO;
import adminfree.dtos.correspondencia.InitSolicitarConsecutivoDTO;
import adminfree.dtos.correspondencia.SolicitudConsecutivoDTO;
import adminfree.dtos.correspondencia.SolicitudConsecutivoResponseDTO;
import adminfree.dtos.correspondencia.TransferirConsecutivoDTO;
import adminfree.dtos.correspondencia.WelcomeInitDTO;
import adminfree.dtos.transversal.MessageResponseDTO;
import adminfree.dtos.transversal.PaginadorResponseDTO;
import adminfree.dtos.transversal.SelectItemDTO;
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
	 * Servicio que soporta el proceso de negocio para la descarga
	 * de un documento de correspondencia en AWS-S3
	 *
	 * @param idCliente, se utiliza para identificar el cliente que tiene el documento
	 * @param idDocumento, se utiliza para consultar los datos del documento
	 * @return DTO con los datos del documento incluyendo el contenido
	 */
	public DocumentoDTO descargarDocumento(Integer idCliente, Long idDocumento) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a descargar del documento
			return new CorrespondenciaBusiness().descargarDocumento(idCliente, idDocumento, connection);
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
	public PaginadorResponseDTO getConsecutivosAnioActual(FiltroConsecutivosDTO filtro) throws Exception {
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

	/**
	 * Servicio que permite obtener los datos iniciales para el
	 * submodulo de Mis Consecutivos de correspondencia solicitados
	 * para el anio actual
	 *
	 * @param idCliente, identificador del cliente asociado al usuario
	 * @param idUsuario, identificador del usuario autenticado en el sistema
	 * @return DTO con los datos iniciales
	 */
	public InitMisConsecutivosDTO getInitMisConsecutivos(Long idCliente, Integer idUsuario) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a consultar los datos para el submodulo de mis consecutivos solicitados
			return new CorrespondenciaBusiness().getInitMisConsecutivos(idCliente, idUsuario, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite consultar el detalle de un consecutivo
	 *
	 * @param filtro, DTO que contiene los identificadores del cliente y del consecutivo
	 * @return DTO con los datos del consecutivo
	 */
	public ConsecutivoDetalleDTO getDetalleConsecutivo(ConsecutivoDetalleDTO filtro) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a consultar el detalle del consecutivo
			return new CorrespondenciaBusiness().getDetalleConsecutivo(filtro, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite obtener los campos para los filtros de busqueda
	 *
	 * @param idCliente, identificador del cliente que tiene los campos
	 * @return Lista de campos con sus atributos configurados
	 */
	public List<CampoFiltroDTO> getCamposFiltro(Long idCliente) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a consultar los campos filtro
			return new CorrespondenciaBusiness().getCamposFiltro(idCliente, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite obtener los items para los filtros tipo LISTA DESPLEGABLE
	 *
	 * @param idsCampos, lista de identificadores de los campos a consultar sus items
	 * @return lista de items con sus atributos construido
	 */
	public List<ItemDTO> getItemsSelectFiltro(List<Long> idsCampos) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a consultar los items
			return new CorrespondenciaBusiness().getItemsSelectFiltro(idsCampos, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite ACTIVAR o ANULAR un consecutivo de correspondencia
	 *
	 * @param parametro, DTO que contiene los datos necesarios para el proceso
	 */
	public void activarAnularConsecutivo(ActivarAnularConsecutivoDTO parametro) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a cambiar el estado del consecutivo
			new CorrespondenciaBusiness().activarAnularConsecutivo(parametro, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite transferir un consecutivo hacia otro usuario
	 *
	 * @param parametro, DTO con los datos necesarios para el proceso
	 * @return DTO con los consecutivos paginado de acuerdo al filtro
	 */
	public TransferirConsecutivoDTO transferirConsecutivo(TransferirConsecutivoDTO parametro) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a transferir el consecutivo
			return new CorrespondenciaBusiness().transferirConsecutivo(parametro, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite obtener los usuarios para el proceso de transferir consecutivo
	 *
	 * @param idCliente, identificador del cliente asociado a los usuarios
	 * @param idUsuario, se deben consultar todos los usuarios activos excepto este usuario
	 * @return Lista de usuarios activos en el sistema
	 */
	public List<SelectItemDTO> getUsuariosTransferir(Integer idCliente, Integer idUsuario) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a consultar los usuarios ACTIVOS
			return new CorrespondenciaBusiness().getUsuariosTransferir(idCliente, idUsuario, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite consultar y retornar los datos del consecutivo para su edicion
	 *
	 * @param filtro, DTO que contiene los valores para el filtro de busqueda
	 * @return DTO Con los atributos del consecutivo configurados
	 */
	public ConsecutivoEdicionDTO getConsecutivoEdicion(ConsecutivoEdicionDTO filtro) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a consultar los atributos del consecutivo para su edicion
			return new CorrespondenciaBusiness().getConsecutivoEdicion(filtro, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}
}
