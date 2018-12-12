package adminfree.persistence;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adminfree.constants.CommonConstant;
import adminfree.dtos.configuraciones.CampoEntradaDTO;
import adminfree.dtos.configuraciones.ClienteDTO;
import adminfree.dtos.configuraciones.ItemDTO;
import adminfree.dtos.configuraciones.RestriccionDTO;
import adminfree.dtos.seguridad.CredencialesDTO;
import adminfree.dtos.seguridad.UsuarioDTO;
import adminfree.enums.Mapper;
import adminfree.enums.Numero;
import adminfree.utilities.Util;

/**
 * Clase que contiene los metodos MAPPER para las consultas JDBC
 * 
 * @author Carlos Andres Diaz
 *
 */
public class MapperJDBC {

	/** Objecto statica que se comporta como una unica instancia */
	private static MapperJDBC instance;

	/** Es el tipo de MAPPER a ejecutar */
	private Mapper tipoMapper;

	/**
	 * Constructor del Mapper donde recibe el tipo de mapper a ejecutar
	 */
	private MapperJDBC() {
	}

	/**
	 * Retorna una instancia de este tipo de Clase
	 */
	public static MapperJDBC get(Mapper tipoMapper) {
		if (instance == null) {
			instance = new MapperJDBC();
		}
		instance.tipoMapper = tipoMapper;
		return instance;
	}

	/**
	 * Metodo que es ejecutado para MAPPEAR los datos de acuerdo a un ResultSet
	 * 
	 * @param res, resultado de acuerdo a la consulta
	 * @return objecto con sus datos configurado de acuerdo al Mapper
	 */
	public Object execute(ResultSet res) throws Exception {
		// encapsula el resultado con sus atributo configurados
		Object result = null;

		// se ejecuta el metodo de acuerdo al tipo de MAPPER
		switch (this.tipoMapper) {
			case COUNT:
				result = getCount(res);
				break;

			case GET_CLIENTES:
				result = getClientes(res);
				break;

			case GET_CLIENTE:
				result = getCliente(res);
				break;

			case GET_DATOS_ADMIN_AUTH:
				result = getDatosAdminAuth(res);
				break;

			case GET_DATOS_USER_AUTH:
				result = getDatosUserAuth(res);
				break;

			case GET_USUARIOS_CLIENTE:
				result = getUsuariosCliente(res);
				break;

			case GET_ID:
				result = getId(res);
				break;

			case GET_SOLO_UN_STRING:
				result = getSoloUnString(res);
				break;

			case GET_RESTRICCIONES:
				result = getRestricciones(res);
				break;

			case GET_CAMPOS_ENTRADA:
				result = getCamposEntrada(res);
				break;

			case GET_DETALLE_CAMPO_ENTRADA:
				result = getDetalleCampoEntrada(res);
				break;
		}
		return result;
	}

	/**
	 * Mapper para configurar el COUNT de un SELECT
	 */
	private Object getCount(ResultSet res) throws Exception {
		if (res.next()) {
			return res.getLong(Numero.UNO.value);
		}
		return Numero.ZERO.value.longValue();
	}

	/**
	 * Mapper para configurar los atributos de los CLIENTES
	 */
	private Object getClientes(ResultSet res) throws Exception {
		List<ClienteDTO> resultado = new ArrayList<>();
		ClienteDTO cliente;
		while (res.next()) {
			// se configura el cliente con su credenciales
			cliente = new ClienteDTO();
			CredencialesDTO credenciales = new CredencialesDTO();
			cliente.setCredenciales(credenciales);

			// se configura los atributos del cliente
			cliente.setId(res.getLong(Numero.UNO.value));
			credenciales.setToken(res.getString(Numero.DOS.value));
			cliente.setNombre(res.getString(Numero.TRES.value));
			cliente.setTelefonos(res.getString(Numero.CUATRO.value));
			cliente.setEmails(res.getString(Numero.CINCO.value));
			cliente.setFechaActivacion(res.getDate(Numero.SEIS.value));
			cliente.setFechaInactivacion(res.getDate(Numero.SIETE.value));
			cliente.setEstado(res.getInt(Numero.OCHO.value));
			cliente.setEstadoNombre(Util.getEstadoNombre(cliente.getEstado()));
			credenciales.setUsuario(res.getString(Numero.NUEVE.value));
			resultado.add(cliente);
		}
		return resultado;
	}

	/**
	 * Mapper para configurar los datos de un CLIENTE
	 */
	private Object getCliente(ResultSet res) throws Exception {
		ClienteDTO cliente = null;
		if (res.next()) {
			// se configura el cliente con su credenciales
			cliente = new ClienteDTO();
			CredencialesDTO credenciales = new CredencialesDTO();
			cliente.setCredenciales(credenciales);

			// se configura los atributos del cliente
			cliente.setId(res.getLong(Numero.UNO.value));
			credenciales.setToken(res.getString(Numero.DOS.value));
			cliente.setNombre(res.getString(Numero.TRES.value));
			cliente.setTelefonos(res.getString(Numero.CUATRO.value));
			cliente.setEmails(res.getString(Numero.CINCO.value));
			cliente.setFechaActivacion(res.getDate(Numero.SEIS.value));
			cliente.setEstado(res.getInt(Numero.SIETE.value));
			cliente.setEstadoNombre(Util.getEstadoNombre(cliente.getEstado()));
			credenciales.setUsuario(res.getString(Numero.OCHO.value));
		}
		return cliente;
	}

	/**
	 * Mapper para obtener los datos del ADMIN cuando inicia sesion
	 */
	private Object getDatosAdminAuth(ResultSet res) throws Exception {
		ClienteDTO admin = null;
		if (res.next()) {
			admin = new ClienteDTO();
			admin.setId(res.getLong(Numero.UNO.value));
			admin.setNombre(res.getString(Numero.DOS.value));
		}
		return admin;
	}

	/**
	 * Mapper para obtener los datos del USUARIO cuando inicia sesion
	 */
	private Object getDatosUserAuth(ResultSet res) throws Exception {
		UsuarioDTO user = null;
		while (res.next()) {
			if (user == null) {

				// se configura los datos del USUARIO
				user = new UsuarioDTO();
				user.setId(res.getLong(Numero.UNO.value));
				user.setNombre(res.getString(Numero.DOS.value));

				// se configura los datos del CLIENTE
				ClienteDTO cliente = new ClienteDTO();
				cliente.setId(res.getLong(Numero.TRES.value));
				cliente.setNombre(res.getString(Numero.CUATRO.value));
				user.setCliente(cliente);

				// se configura los datos del MODULO
				user.agregarModuloToken(res.getString(Numero.CINCO.value));
			} else {
				// solamente se configura los datos del MODULO
				user.agregarModuloToken(res.getString(Numero.CINCO.value));
			}
		}
		return user;
	}

	/**
	 * Mapper para obtener la lista de USUARIOS asociados a un cliente
	 */
	public Object getUsuariosCliente(ResultSet res) throws Exception {
		List<UsuarioDTO> usuarios = new ArrayList<>();
		UsuarioDTO usuario;
		String modulos;
		while (res.next()) {
			usuario = new UsuarioDTO();
			usuario.setId(res.getLong(Numero.UNO.value));
			usuario.setNombre(res.getString(Numero.DOS.value));
			usuario.setUsuarioIngreso(res.getString(Numero.TRES.value));
			usuario.setEstado(res.getInt(Numero.CUATRO.value));
			usuario.setEstadoNombre(Util.getEstadoNombre(usuario.getEstado()));
			modulos = res.getString(Numero.CINCO.value);
			if (modulos != null) {
				usuario.setModulosTokens(Arrays.asList(modulos.split(CommonConstant.PUNTO_COMA)));
			}
			usuarios.add(usuario);
		}
		return usuarios;
	}

	/**
	 * Mapper para obtener el identificador de una entidad
	 */
	public Long getId(ResultSet res) throws Exception {
		if (res.next()) {
			return res.getLong(Numero.UNO.value);
		}
		return null;
	}

	/**
	 * Mapper para obtener solo un valor texto
	 */
	public String getSoloUnString(ResultSet res) throws Exception {
		if (res.next()) {
			return res.getString(Numero.UNO.value);
		}
		return null;
	}

	/**
	 * Mapper para obtener las restricciones asociadas al tipo de campo
	 */
	public Object getRestricciones(ResultSet res) throws Exception {
		List<RestriccionDTO> restricciones = new ArrayList<>();
		RestriccionDTO restriccion;
		while (res.next()) {
			restriccion = new RestriccionDTO();
			restriccion.setId(res.getInt(Numero.UNO.value));
			restriccion.setNombre(res.getString(Numero.DOS.value));
			restriccion.setDescripcion(res.getString(Numero.TRES.value));
			restricciones.add(restriccion);
		}
		return restricciones;
	}

	/**
	 * Mapper para obtener los campos de entrada informacion asociado a un cliente
	 */
	public Object getCamposEntrada(ResultSet res) throws Exception {
		List<CampoEntradaDTO> campos = new ArrayList<>();
		CampoEntradaDTO campo;
		while (res.next()) {
			campo = new CampoEntradaDTO();
			campo.setId(res.getLong(Numero.UNO.value));
			campo.setTipoCampo(res.getInt(Numero.DOS.value));
			campo.setTipoCampoNombre(Util.getTipoCampoNombre(campo.getTipoCampo()));
			campo.setNombre(res.getString(Numero.TRES.value));
			campos.add(campo);
		}
		return campos;
	}

	/**
	 * Mapper para obtener el detalle de un campo de entrada de informacion
	 */
	public CampoEntradaDTO getDetalleCampoEntrada(ResultSet res) throws Exception {
		Long ZERO = Numero.ZERO.value.longValue();
		CampoEntradaDTO campoEntrada = null;
		while (res.next()) {
			// para la primera iteracion se debe configurar los datos basicos
			if (campoEntrada == null) {

				// se configura los datos basicos
				campoEntrada = new CampoEntradaDTO();
				campoEntrada.setId(res.getLong(Numero.UNO.value));
				campoEntrada.setIdCliente(res.getLong(Numero.DOS.value));
				campoEntrada.setNombre(res.getString(Numero.TRES.value));
				campoEntrada.setDescripcion(res.getString(Numero.CUATRO.value));
				campoEntrada.setTipoCampo(res.getInt(Numero.CINCO.value));
				campoEntrada.setTipoCampoNombre(Util.getTipoCampoNombre(campoEntrada.getTipoCampo()));

				// se configura las restricciones y los items para este campo
				configurarRestriccion(campoEntrada, res);
				configurarItem(campoEntrada, ZERO, res);
			} else {
				// para las demas iteraciones solamente se debe configurar las restriciones e items
				configurarRestriccion(campoEntrada, res);
				configurarItem(campoEntrada, ZERO, res);
			}
		}
		return campoEntrada;
	}

	/**
	 * Metodo que permite configurar la restriccion para un campo de entrada
	 */
	private void configurarRestriccion(CampoEntradaDTO campo, ResultSet res) throws Exception {
		Integer idRestriccion = res.getInt(Numero.SEIS.value);
		if (idRestriccion != null && idRestriccion > Numero.ZERO.value) {
			RestriccionDTO restriccion = new RestriccionDTO();
			restriccion.setId(idRestriccion);
			restriccion.setNombre(res.getString(Numero.SIETE.value));
			restriccion.setDescripcion(res.getString(Numero.OCHO.value));
			campo.agregarRestriccion(restriccion);
		}
	}

	/**
	 * Metodo que permite configurar un ITEM para un campo de entrada tipo select-item
	 */
	private void configurarItem(CampoEntradaDTO campo, Long ZERO, ResultSet res) throws Exception {
		Long idItem = res.getLong(Numero.NUEVE.value);
		if (idItem != null && idItem > ZERO) {
			ItemDTO item = new ItemDTO();
			item.setId(idItem);
			item.setValor(res.getString(Numero.DIEZ.value));
			campo.agregarItem(item);
		}
	}
}
