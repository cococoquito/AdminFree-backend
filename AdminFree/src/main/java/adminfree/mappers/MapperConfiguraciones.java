package adminfree.mappers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adminfree.constants.CommonConstant;
import adminfree.dtos.configuraciones.CampoEntradaDTO;
import adminfree.dtos.configuraciones.CampoEntradaEdicionDTO;
import adminfree.dtos.configuraciones.ClienteDTO;
import adminfree.dtos.configuraciones.ItemDTO;
import adminfree.dtos.configuraciones.NomenclaturaCampoDTO;
import adminfree.dtos.configuraciones.NomenclaturaDTO;
import adminfree.dtos.configuraciones.RestriccionDTO;
import adminfree.dtos.seguridad.CredencialesDTO;
import adminfree.dtos.seguridad.UsuarioDTO;
import adminfree.enums.Numero;
import adminfree.utilities.Util;

/**
 * Mapper que contiene las implementaciones JDBC para el modulo de configuraciones
 *
 * @author Carlos Andres Diaz
 *
 */
public class MapperConfiguraciones extends Mapper {

	/** Son los tipos de mapper que soporta este modulo */
	public static final int GET_CLIENTES = 1;
	public static final int GET_CLIENTE = 2;
	public static final int GET_USUARIOS_CLIENTE = 3;
	public static final int GET_RESTRICCIONES = 4;
	public static final int GET_CAMPOS_ENTRADA = 5;
	public static final int GET_DETALLE_CAMPO_ENTRADA = 6;
	public static final int GET_ITEMS = 7;
	public static final int GET_DETALLE_CAMPO_EDITAR = 8;
	public static final int GET_NOMENCLATURAS = 9;
	public static final int GET_DETALLE_NOMENCLATURA = 10;

	/** Objecto statica que se comporta como una unica instancia */
	private static MapperConfiguraciones instance;

	/**
	 * Constructor del Mapper no instanciable
	 */
	private MapperConfiguraciones() {
	}

	/**
	 * Retorna una instancia de este tipo de Mapper
	 */
	public static MapperConfiguraciones get(int tipoMapper) {
		if (instance == null) {
			instance = new MapperConfiguraciones();
		}
		instance.tipoMapper = tipoMapper;
		return instance;
	}

	/**
	 * Metodo que es ejecutado para MAPPEAR los datos de acuerdo a un ResultSet
	 *
	 * @param res, resultado de acuerdo a la consulta
	 * @param parametros que necesita el mapper para ser procesado
	 * @return objecto con sus datos configurado de acuerdo al Mapper
	 */
	@Override
	public Object executeParams(ResultSet res, Object parametro) throws Exception {
		return null;
	}

	/**
	 * Metodo que es ejecutado para MAPPEAR los datos de acuerdo a un ResultSet
	 *
	 * @param res, resultado de acuerdo a la consulta
	 * @return objecto con sus datos configurado de acuerdo al Mapper
	 */
	@Override
	public Object execute(ResultSet res) throws Exception {
		Object result = null;
		switch (this.tipoMapper) {
			case MapperConfiguraciones.GET_CLIENTES:
				result = getClientes(res);
				break;
	
			case MapperConfiguraciones.GET_CLIENTE:
				result = getCliente(res);
				break;

			case MapperConfiguraciones.GET_USUARIOS_CLIENTE:
				result = getUsuariosCliente(res);
				break;
	
			case MapperConfiguraciones.GET_RESTRICCIONES:
				result = getRestricciones(res);
				break;

			case MapperConfiguraciones.GET_CAMPOS_ENTRADA:
				result = getCamposEntrada(res);
				break;
	
			case MapperConfiguraciones.GET_DETALLE_CAMPO_ENTRADA:
				result = getDetalleCampoEntrada(res);
				break;

			case MapperConfiguraciones.GET_ITEMS:
				result = getItems(res);
				break;
	
			case MapperConfiguraciones.GET_DETALLE_CAMPO_EDITAR:
				result = getDetalleCampoEditar(res);
				break;

			case MapperConfiguraciones.GET_NOMENCLATURAS:
				result = getNomenclaturas(res);
				break;
	
			case MapperConfiguraciones.GET_DETALLE_NOMENCLATURA:
				result = getDetalleNomenclatura(res);
				break;
			}
		return result;
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
			cliente.setId(res.getLong(Numero.UNO.valueI));
			cliente.setNombre(res.getString(Numero.DOS.valueI));
			cliente.setTelefonos(res.getString(Numero.TRES.valueI));
			cliente.setEmails(res.getString(Numero.CUATRO.valueI));
			cliente.setFechaActivacion(res.getDate(Numero.CINCO.valueI));
			cliente.setFechaInactivacion(res.getDate(Numero.SEIS.valueI));
			cliente.setEstado(res.getInt(Numero.SIETE.valueI));
			cliente.setEstadoNombre(Util.getEstadoNombre(cliente.getEstado()));
			credenciales.setUsuario(res.getString(Numero.OCHO.valueI));
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
			cliente.setId(res.getLong(Numero.UNO.valueI));
			cliente.setNombre(res.getString(Numero.DOS.valueI));
			cliente.setTelefonos(res.getString(Numero.TRES.valueI));
			cliente.setEmails(res.getString(Numero.CUATRO.valueI));
			cliente.setFechaActivacion(res.getDate(Numero.CINCO.valueI));
			cliente.setEstado(res.getInt(Numero.SEIS.valueI));
			cliente.setEstadoNombre(Util.getEstadoNombre(cliente.getEstado()));
			credenciales.setUsuario(res.getString(Numero.SIETE.valueI));
		}
		return cliente;
	}

	/**
	 * Mapper para obtener la lista de USUARIOS asociados a un cliente
	 */
	private Object getUsuariosCliente(ResultSet res) throws Exception {
		List<UsuarioDTO> usuarios = new ArrayList<>();
		UsuarioDTO usuario;
		String modulos;
		while (res.next()) {
			usuario = new UsuarioDTO();
			usuario.setId(res.getLong(Numero.UNO.valueI));
			usuario.setNombre(res.getString(Numero.DOS.valueI));
			usuario.setUsuarioIngreso(res.getString(Numero.TRES.valueI));
			usuario.setEstado(res.getInt(Numero.CUATRO.valueI));
			usuario.setEstadoNombre(Util.getEstadoNombre(usuario.getEstado()));
			modulos = res.getString(Numero.CINCO.valueI);
			usuario.setCargo(res.getString(Numero.SEIS.valueI));
			if (modulos != null) {
				usuario.setModulosTokens(Arrays.asList(modulos.split(CommonConstant.PUNTO_COMA)));
			}
			usuarios.add(usuario);
		}
		return usuarios;
	}

	/**
	 * Mapper para obtener las restricciones asociadas al tipo de campo
	 */
	private Object getRestricciones(ResultSet res) throws Exception {
		List<RestriccionDTO> restricciones = new ArrayList<>();
		RestriccionDTO restriccion;
		while (res.next()) {
			restriccion = new RestriccionDTO();
			restriccion.setId(res.getInt(Numero.UNO.valueI));
			restriccion.setDescripcion(res.getString(Numero.DOS.valueI));
			restriccion.setCompatible(res.getString(Numero.TRES.valueI));
			restriccion.setTipoCampo(res.getInt(Numero.CUATRO.valueI));
			restricciones.add(restriccion);
		}
		return restricciones;
	}

	/**
	 * Mapper para obtener los campos de entrada informacion asociado a un cliente
	 */
	private Object getCamposEntrada(ResultSet res) throws Exception {
		List<CampoEntradaDTO> campos = new ArrayList<>();
		CampoEntradaDTO campo;
		while (res.next()) {
			campo = new CampoEntradaDTO();
			campo.setId(res.getLong(Numero.UNO.valueI));
			campo.setTipoCampo(res.getInt(Numero.DOS.valueI));
			campo.setTipoCampoNombre(Util.getTipoCampoNombre(campo.getTipoCampo()));
			campo.setNombre(res.getString(Numero.TRES.valueI));
			campos.add(campo);
		}
		return campos;
	}

	/**
	 * Mapper para obtener el detalle de un campo de entrada de informacion
	 */
	private CampoEntradaDTO getDetalleCampoEntrada(ResultSet res) throws Exception {
		CampoEntradaDTO campoEntrada = null;
		while (res.next()) {

			// se configura los datos basicos
			campoEntrada = new CampoEntradaDTO();
			campoEntrada.setId(res.getLong(Numero.UNO.valueI));
			campoEntrada.setIdCliente(res.getLong(Numero.DOS.valueI));
			campoEntrada.setNombre(res.getString(Numero.TRES.valueI));
			campoEntrada.setDescripcion(res.getString(Numero.CUATRO.valueI));
			campoEntrada.setTipoCampo(res.getInt(Numero.CINCO.valueI));
			campoEntrada.setTipoCampoNombre(Util.getTipoCampoNombre(campoEntrada.getTipoCampo()));
		}
		return campoEntrada;
	}

	/**
	 * Metodo que permite obtener los items de un campos tipo select item
	 */
	private List<ItemDTO> getItems(ResultSet res) throws Exception {
		List<ItemDTO> items = new ArrayList<>();
		ItemDTO item;
		while (res.next()) {
			item = new ItemDTO();
			item.setId(res.getLong(Numero.UNO.valueI));
			item.setValor(res.getString(Numero.DOS.valueI));
			items.add(item);
		}
		return items;
	}

	/**
	 * Metodo que permite obtener el detalle del campo a editar
	 */
	public CampoEntradaEdicionDTO getDetalleCampoEditar(ResultSet res) throws Exception {
		CampoEntradaEdicionDTO detalle = null;
		if (res.next()) {
			// se configura los datos del campo
			CampoEntradaDTO campo = new CampoEntradaDTO();
			campo.setId(res.getLong(Numero.UNO.valueI));
			campo.setIdCliente(res.getLong(Numero.DOS.valueI));
			campo.setNombre(res.getString(Numero.TRES.valueI));
			campo.setDescripcion(res.getString(Numero.CUATRO.valueI));
			campo.setTipoCampo(res.getInt(Numero.CINCO.valueI));
			campo.setTipoCampoNombre(Util.getTipoCampoNombre(campo.getTipoCampo()));

			// se utiliza para configurar las banderas
			Integer nomenclaturas = res.getInt(Numero.SEIS.valueI);
			Integer consecutivos = res.getInt(Numero.SIETE.valueI);

			// se configura el detalle para la edicion
			detalle = new CampoEntradaEdicionDTO();
			detalle.setCampoEntrada(campo);
			detalle.setTieneNomenclaturas(!Numero.ZERO.valueI.equals(nomenclaturas));
			detalle.setTieneConsecutivos(!Numero.ZERO.valueI.equals(consecutivos));
		}
		return detalle;
	}

	/**
	 * Mapper para obtener las nomenclaturas
	 */
	private List<NomenclaturaDTO> getNomenclaturas(ResultSet res) throws Exception {
		List<NomenclaturaDTO> resultado = new ArrayList<>();
		NomenclaturaDTO nomenclatura;
		while (res.next()) {
			nomenclatura = new NomenclaturaDTO();
			nomenclatura.setId(res.getLong(Numero.UNO.valueI));
			nomenclatura.setNomenclatura(res.getString(Numero.DOS.valueI));
			nomenclatura.setDescripcion(res.getString(Numero.TRES.valueI));
			nomenclatura.setConsecutivoInicial(res.getInt(Numero.CUATRO.valueI));
			nomenclatura.setCantConsecutivos(res.getInt(Numero.CINCO.valueI));
			resultado.add(nomenclatura);
		}
		return resultado;
	}

	/**
	 * Mapper para obtener el detalle de la nomenclatura
	 */
	private NomenclaturaDTO getDetalleNomenclatura(ResultSet res) throws Exception {
		NomenclaturaDTO nomenclatura = null;
		while (res.next()) {
			if (nomenclatura == null) {
				// datos basicos de la nomenclatura
				nomenclatura = new NomenclaturaDTO();
				nomenclatura.setId(res.getLong(Numero.UNO.valueI));
				nomenclatura.setNomenclatura(res.getString(Numero.DOS.valueI));
				nomenclatura.setDescripcion(res.getString(Numero.TRES.valueI));
				nomenclatura.setConsecutivoInicial(res.getInt(Numero.CUATRO.valueI));
				nomenclatura.setSecuencia(res.getInt(Numero.CINCO.valueI));
				nomenclatura.setCantConsecutivos(res.getInt(Numero.SEIS.valueI));

				// campo de la nomenclatura
				configurarCampo(nomenclatura, res);
			} else {
				// solamente se configura el campo de la nomenclatura
				configurarCampo(nomenclatura, res);
			}
		}
		return nomenclatura;
	}

	/**
	 * Invocado por detalle de la nomenclatura y permite configurar el campo de la nomenclatura
	 */
	private void configurarCampo(NomenclaturaDTO datos, ResultSet res) throws Exception {
		Long idNomCampo = res.getLong(Numero.SIETE.valueI);
		if (idNomCampo != null && idNomCampo.longValue() > Numero.ZERO.valueL.longValue()) {
			NomenclaturaCampoDTO campo = new NomenclaturaCampoDTO();
			campo.setId(idNomCampo);
			campo.setIdCampo(res.getLong(Numero.OCHO.valueI));
			campo.setNombreCampo(res.getString(Numero.NUEVE.valueI));
			campo.setTipoCampo(Util.getTipoCampoNombre(res.getInt(Numero.DIEZ.valueI)));
			campo.setTieneConsecutivo(Numero.UNO.valueI.equals(res.getInt(Numero.ONCE.valueI)));
			campo.setOrden(res.getInt(Numero.DOCE.valueI));
			datos.agregarCampos(campo);
		}
	}
}
