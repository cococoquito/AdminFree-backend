package adminfree.services;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import adminfree.business.ConfiguracionesBusiness;
import adminfree.constants.PropertyKey;
import adminfree.dtos.configuraciones.CampoEntradaDTO;
import adminfree.dtos.configuraciones.CampoEntradaEdicionDTO;
import adminfree.dtos.configuraciones.ClienteDTO;
import adminfree.dtos.configuraciones.GenerarTokenIngresoDTO;
import adminfree.dtos.configuraciones.ModificarCuentaUsuarioDTO;
import adminfree.dtos.configuraciones.NomenclaturaDTO;
import adminfree.dtos.configuraciones.NomenclaturaEdicionDTO;
import adminfree.dtos.configuraciones.UsuarioEdicionDTO;
import adminfree.dtos.seguridad.UsuarioDTO;
import adminfree.utilities.CerrarRecursos;

/**
 *
 * Clase que contiene todos los servicios para el modulo de Configuraciones
 *
 * @author Carlos Andres Diaz
 *
 */
@Service
public class ConfiguracionesService {

	/** DataSource para las conexiones de la BD de AdminFree */
	@Autowired
	private DataSource adminFreeDS;

	/** Contiene el postPass para la encriptacion de claves */
	@Value(PropertyKey.SECURITY_POST_PASS)
	private String securityPostPass;	

	/**
	 * Servicio que permite crear un cliente en el sistema
	 *
	 * @param cliente, DTO con los datos del cliente a crear
	 * @return el nuevo cliente con el token, id y demas atributos
	 */
	public ClienteDTO crearCliente(ClienteDTO cliente) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a crear el cliente en BD
			return new ConfiguracionesBusiness().crearCliente(cliente, this.securityPostPass, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio para actualizar los datos del CLIENTE
	 *
	 * @param clienteUpdate, datos del cliente ACTUALIZAR
	 */
	public void actualizarCliente(ClienteDTO clienteUpdate) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede actualizar los datos del CLIENTE
			new ConfiguracionesBusiness().actualizarCliente(clienteUpdate, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}
	
	/**
	 * Servicio que permite ACTIVAR un cliente
	 *
	 * @return cliente con los nuevos datos de la ACTIVACION
	 * @param cliente, contiene el identificador del cliente
	 */
	public ClienteDTO activarCliente(ClienteDTO cliente) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede activar el CLIENTE
			return new ConfiguracionesBusiness().activarCliente(cliente, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite INACTIVAR un cliente
	 *
	 * @return cliente con los nuevos datos de la INACTIVACION
	 * @param cliente, contiene el identificador del cliente
	 */
	public ClienteDTO inactivarCliente(ClienteDTO cliente) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede inactivar el CLIENTE
			return new ConfiguracionesBusiness().inactivarCliente(cliente, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite ELIMINAR un cliente del sistema
	 *
	 * @param cliente, DTO que contiene el identificador del cliente ELIMINAR
	 * @return OK, de lo contrario el mensaje de error de MYSQL
	 */
	public String eliminarCliente(ClienteDTO cliente) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede ELIMINAR el CLIENTE
			return new ConfiguracionesBusiness().eliminarCliente(cliente, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite consultar los usuarios con estados (ACTIVO/INACTIVO)
	 * asociados a un cliente especifico
	 *
	 * @param filtro, contiene los datos del filtro de busqueda
	 * @return lista de Usuarios asociados a un cliente
	 */	
	public List<UsuarioDTO> getUsuariosCliente(ClienteDTO filtro) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a consultar los usuarios asociados a un cliente
			return new ConfiguracionesBusiness().getUsuariosCliente(filtro, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite validar los datos del usuario para la creacion o modificacion
	 *
	 * @param usuario, DTO con los datos del usuario a crear o modificar
	 */
	public void validarDatosUsuario(UsuarioDTO usuario) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a validar los datos del usuario
			new ConfiguracionesBusiness().validarDatosUsuario(usuario, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite crear el usuario con sus privilegios en el sistema
	 *
	 * @param usuario, DTO que contiene los datos del usuarios
	 * @return DTO con los datos del usuario creado
	 */
	public UsuarioDTO crearUsuario(UsuarioDTO usuario) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a crear el usuario con sus privilegios
			return new ConfiguracionesBusiness().crearUsuario(usuario, this.securityPostPass, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite editar los datos de un usuario
	 *
	 * @param datos, DTO que contiene los datos a modificar
	 */
	public void editarUsuario(UsuarioEdicionDTO datos) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a editar los datos del usuario
			new ConfiguracionesBusiness().editarUsuario(datos, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite cambiar el estado de un usuario
	 *
	 * @param usuario, DTO que contiene los datos del usuario a modificar
	 */
	public void modificarEstadoUsuario(UsuarioDTO usuario) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a modificar el estado del usuario
			new ConfiguracionesBusiness().modificarEstadoUsuario(usuario, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite generar un nuevo TOKEN de ingreso
	 * para el usuario o cliente que llega por parametro
	 *
	 * @param parametro, DTO que contiene el id del cliente o usuario
	 * @return DTO con el TOKEN de ingreso generada
	 */
	public GenerarTokenIngresoDTO generarClaveIngreso(GenerarTokenIngresoDTO parametro) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a generar una nueva clave de ingreso para el usuario o cliente
			return new ConfiguracionesBusiness().generarClaveIngreso(parametro, this.securityPostPass, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite soportar el proceso de negocio para
	 * la creacion del campo de entrada de informacion
	 *
	 * @param campo, DTO que contiene los datos del nuevo campo de entrada
	 * @return DTO con los datos del nuevo campo de entrada
	 */
	public CampoEntradaDTO crearCampoEntrada(CampoEntradaDTO campo) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a crear el campo de entrada de informacion
			return new ConfiguracionesBusiness().crearCampoEntrada(campo, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite obtener los campos de entrada de informacion asociado a un cliente
	 *
	 * @param idCliente, identificador del cliente que le pertenece los campos de entrada
	 * @return lista DTO con la informacion de los campos de entrada
	 */
	public List<CampoEntradaDTO> getCamposEntrada(Long idCliente) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a consultar los campos de entrada asociado al cliente
			return new ConfiguracionesBusiness().getCamposEntrada(idCliente, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite obtener el detalle de un campo de entrada de informacion
	 *
	 * @param idCampo, identificador del campo de entrada informacion
	 * @return DTO con los datos del campo de entrada de informacion
	 */
	public CampoEntradaDTO getDetalleCampoEntrada(Long idCampo) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a consultar el detalle del campo de entrada informacion
			return new ConfiguracionesBusiness().getDetalleCampoEntrada(idCampo, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que soporta el proceso de negocio para la eliminacion de un campo de entrada
	 * 
	 * @param idCampo, identificador del campo de entrada
	 */
	public void eliminarCampoEntrada(Long idCampo) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede eliminar el campo de entrada informacion
			new ConfiguracionesBusiness().eliminarCampoEntrada(idCampo, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite obtener el detalle de un campo de entrada para edicion
	 *
	 * @param idCampo, identificador del campo de entrada a editar
	 * @return DTO con los datos del campo de entrada de informacion a editar
	 */
	public CampoEntradaEdicionDTO getDetalleCampoEntradaEdicion(Long idCampo) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a obtener el detalle del campo para la edicion
			return new ConfiguracionesBusiness().getDetalleCampoEntradaEdicion(idCampo, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite editar un campo de entrada de informacion
	 *
	 * @param datos, DTO que contiene los datos a editar
	 * @return DTO con los datos basico del campo
	 */
	public CampoEntradaDTO editarCampoEntrada(CampoEntradaEdicionDTO datos) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a editar el campo de entrada de informacion
			return new ConfiguracionesBusiness().editarCampoEntrada(datos, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite validar los datos de campo de entrada
	 * esto aplica para el primer paso al momento de crear o editar el campo
	 *
	 * @param campo, contiene los datos del campo de entrada
	 */
	public void validarDatosCampoEntrada(CampoEntradaDTO campo) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se valida si los datos del campo son OK
			new ConfiguracionesBusiness().validarDatosCampoEntrada(campo, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite obtener todas las nomenclaturas asociadas a un cliente
	 *
	 * @param idCliente, identificador del cliente quien le pertenece las nomenclaturas
	 * @return lista de nomenclaturas con sus atributos configuradas
	 */
	public List<NomenclaturaDTO> getNomenclaturas(Long idCliente) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a obtener todas las nomenclaturas asociadas a un cliente
			return new ConfiguracionesBusiness().getNomenclaturas(idCliente, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite crear una nomenclatura
	 *
	 * @param nomenclatura, contiene los datos de la creacion
	 * @return Nomenclatura con el identificador generado
	 */
	public NomenclaturaDTO crearNomenclatura(NomenclaturaDTO nomenclatura) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a crear la nomenclatura
			return new ConfiguracionesBusiness().crearNomenclatura(nomenclatura, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite editar la nomenclatura
	 *
	 * @param datos, contiene los datos de la edicion
	 */
	public void editarNomenclatura(NomenclaturaEdicionDTO datos) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a editar la nomenclatura
			new ConfiguracionesBusiness().editarNomenclatura(datos, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite validar si la nomenclatura ya existe en el sistema
	 * 
	 * @param nomenclatura, DTO que contiene los datos para la validacion
	 * @throws Exception, se lanza si existe la nomenclatura parametrizada en el sistema
	 */
	public void validarExisteNomenclatura(NomenclaturaDTO nomenclatura) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se valida la existencia de la nomenclatura
			new ConfiguracionesBusiness().validarExisteNomenclatura(nomenclatura, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite eliminar una nomenclatura del sistema
	 *
	 * @param idNomenclatura, identificador de la nomenclatura
	 */
	public void eliminarNomenclatura(Long idNomenclatura) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a eliminar la nomenclatura
			new ConfiguracionesBusiness().eliminarNomenclatura(idNomenclatura, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite consultar el detalle de la nomenclatura
	 *
	 * @param idNomenclatura, identificador de la nomenclatura 
	 * @return, DTO con el detalle de la nomenclatura
	 */
	public NomenclaturaDTO getDetalleNomenclatura(Long idNomenclatura) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a consultar el detalle de la nomenclatura
			return new ConfiguracionesBusiness().getDetalleNomenclatura(idNomenclatura, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite procesar la funcionalidad de negocio de modificacion
	 * cuenta del usuario aplica para datos personales, cambio clave o usuario de ingreso
	 *
	 * @param params, contiene los DTOs para las modificaciones correspondiente
	 */
	public void modificarCuentaUsuario(ModificarCuentaUsuarioDTO params) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a modificar la cuenta del usuario
			new ConfiguracionesBusiness().modificarCuentaUsuario(params, this.securityPostPass, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}
}
