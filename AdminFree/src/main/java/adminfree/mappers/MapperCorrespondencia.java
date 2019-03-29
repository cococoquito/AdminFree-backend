package adminfree.mappers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adminfree.constants.CommonConstant;
import adminfree.constants.TiposDocumentos;
import adminfree.dtos.configuraciones.ItemDTO;
import adminfree.dtos.correspondencia.CampoEntradaDetalleDTO;
import adminfree.dtos.correspondencia.CampoEntradaValueDTO;
import adminfree.dtos.correspondencia.CampoFiltroDTO;
import adminfree.dtos.correspondencia.ConsecutivoDTO;
import adminfree.dtos.correspondencia.ConsecutivoEdicionValueDTO;
import adminfree.dtos.correspondencia.DocumentoDTO;
import adminfree.dtos.correspondencia.TransferenciaDTO;
import adminfree.dtos.correspondencia.WelcomeNomenclaturaDTO;
import adminfree.dtos.correspondencia.WelcomeUsuarioDTO;
import adminfree.dtos.transversal.SelectItemDTO;
import adminfree.enums.Numero;
import adminfree.enums.TipoCampo;
import adminfree.utilities.Util;

/**
 * Mapper que contiene las implementaciones JDBC para el modulo de correspondencia
 *
 * @author Carlos Andres Diaz
 *
 */
public class MapperCorrespondencia extends Mapper {

	/** Son los tipos de mapper que soporta este modulo */
	public static final int GET_DTL_NOMENCLATURA_CAMPOS = 1;
	public static final int GET_SECUENCIA_NOMENCLATURA = 2;
	public static final int GET_WELCOME_NOMENCLATURAS = 3;
	public static final int GET_WELCOME_USUARIOS = 4;
	public static final int GET_DOCUMENTOS = 5;
	public static final int GET_DATOS_DOC_ELIMINAR = 6;
	public static final int GET_CONSECUTIVOS_ANIO_ACTUAL = 7;
	public static final int GET_CONSECUTIVO = 8;
	public static final int GET_CONSECUTIVO_VALUES = 9;
	public static final int GET_DATOS_DOCUMENTO_DESCARGAR = 10;
	public static final int GET_CAMPOS_FILTRO = 11;
	public static final int GET_ITEMS_SELECT_FILTRO = 12;
	public static final int GET_TRANSFERENCIAS = 13;
	public static final int GET_USUARIOS_TRANSFERIR = 14;
	public static final int GET_VALUES_EDICION = 15;

	/** Objecto statica que se comporta como una unica instancia */
	private static MapperCorrespondencia instance;

	/**
	 * Constructor del Mapper no instanciable
	 */
	private MapperCorrespondencia() {
	}

	/**
	 * Retorna una instancia de este tipo de Mapper
	 */
	public static MapperCorrespondencia get(int tipoMapper) {
		if (instance == null) {
			instance = new MapperCorrespondencia();
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
	public Object executeParams(ResultSet res, Object parametros) throws Exception {
		Object result = null;
		switch (this.tipoMapper) {

			case MapperCorrespondencia.GET_USUARIOS_TRANSFERIR:
				result = getUsuariosTransferir(res, parametros);
				break;

			case MapperCorrespondencia.GET_VALUES_EDICION:
				result = getValuesEdicion(res, parametros);
				break;
		}
		return result;
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

			case MapperCorrespondencia.GET_DTL_NOMENCLATURA_CAMPOS:
				result = getDtlNomenclaturaCampos(res);
				break;

			case MapperCorrespondencia.GET_SECUENCIA_NOMENCLATURA:
				result = getSecuenciaNomenclatura(res);
				break;

			case MapperCorrespondencia.GET_WELCOME_NOMENCLATURAS:
				result = getWelComeNomenclaturas(res);
				break;

			case MapperCorrespondencia.GET_WELCOME_USUARIOS:
				result = getWelComeUsuarios(res);
				break;

			case MapperCorrespondencia.GET_DOCUMENTOS:
				result = getDocumentos(res);
				break;

			case MapperCorrespondencia.GET_DATOS_DOC_ELIMINAR:
				result = getDatosDocEliminar(res);
				break;

			case MapperCorrespondencia.GET_CONSECUTIVOS_ANIO_ACTUAL:
				result = getConsecutivosAnioActual(res);
				break;

			case MapperCorrespondencia.GET_CONSECUTIVO:
				result = getConsecutivo(res);
				break;

			case MapperCorrespondencia.GET_CONSECUTIVO_VALUES:
				result = getConsecutivoValues(res);
				break;

			case MapperCorrespondencia.GET_DATOS_DOCUMENTO_DESCARGAR:
				result = getDatosDocumentoDescargar(res);
				break;

			case MapperCorrespondencia.GET_CAMPOS_FILTRO:
				result = getCamposFiltro(res);
				break;

			case MapperCorrespondencia.GET_ITEMS_SELECT_FILTRO:
				result = getItemsSelectFiltro(res);
				break;

			case MapperCorrespondencia.GET_TRANSFERENCIAS:
				result = getTransferencias(res);
				break;
		}
		return result;
	}

	/**
	 * Metodo para configurar los values de un consecutivo para su edicion
	 *
	 * @param campos, lista de los detalle de los campos asociados al consecutivo
	 */
	@SuppressWarnings("unchecked")
	private List<ConsecutivoEdicionValueDTO> getValuesEdicion(ResultSet res, Object campos) throws Exception {

		// lista del detalle de los campos
		List<CampoEntradaDetalleDTO> camposDetalle = (List<CampoEntradaDetalleDTO>) campos;

		// lista de values a retornar
		List<ConsecutivoEdicionValueDTO> valuesResponse = new ArrayList<>();

		// variables que se utilizan en el while
		ConsecutivoEdicionValueDTO value;
		Long idCampoNomenclatura;
		Integer tipoCampo;
		List<ItemDTO> items;
		Long idItem;

		// se recorre cada valor
		while (res.next()) {

			// se configuran los identificadores del value y campo-nomenclatura
			value = new ConsecutivoEdicionValueDTO();
			value.setIdValue(res.getLong(Numero.UNO.valueI));
			idCampoNomenclatura = res.getLong(Numero.DOS.valueI);

			// se procede a buscar el campo asociado a este valor
			for (CampoEntradaDetalleDTO campo : camposDetalle) {

				// son iguales solo si tienen el mismo identificador campo-nomenclatura
				if (idCampoNomenclatura.equals(campo.getIdCampoNomenclatura())) {

					// se indica que este campo tiene un valor asociado
					campo.setTieneValor(true);
					value.setCampo(campo);

					// se configura el valor dependiendo del tipo de campo
					tipoCampo = campo.getTipoCampo();

					// para el campo de texto siempre va ser String
					if (TipoCampo.CAMPO_TEXTO.id.equals(tipoCampo)) {
						value.setValue(res.getString(Numero.TRES.valueI));
					} 

					// para lista desplegable se debe buscar su ITEM.dto
					else if (TipoCampo.LISTA_DESPLEGABLE.id.equals(tipoCampo)) {
						idItem = res.getLong(Numero.TRES.valueI);
						items = campo.getItems();
						if (items != null && !items.isEmpty()) {
							for (ItemDTO item : items) {
								if (idItem.equals(item.getId())) {
									value.setValue(item);
									break;
								}
							}
						}
					} 

					// para casilla de verificacion se debe retornar un boolean (1=true, contrario=false)
					else if (TipoCampo.CASILLA_VERIFICACION.id.equals(tipoCampo)) {
						value.setValue(CommonConstant.SI.equals(res.getString(Numero.TRES.valueI)) ? true : false);
					}

					// para campo de fecha se hace la conversion a DATE
					else if (TipoCampo.CAMPO_FECHA.id.equals(tipoCampo)) {
						value.setValue(res.getDate(Numero.TRES.valueI));
					}
					break;
				}
			}
			valuesResponse.add(value);
		}
		return valuesResponse;
	}

	/**
	 * Metodo para configurar los usuarios a transferir
	 *
	 * @param usuarios, lista de usuarios previamente configurados
	 */
	@SuppressWarnings("unchecked")
	private List<SelectItemDTO> getUsuariosTransferir(ResultSet res, Object usuarios) throws Exception {
		List<SelectItemDTO> items = (List<SelectItemDTO>) usuarios;
		SelectItemDTO item = null;
		while (res.next()) {
			item = new SelectItemDTO();
			item.setId(res.getLong(Numero.UNO.valueI));
			item.setLabel(res.getString(Numero.DOS.valueI));
			item.setDescripcion(res.getString(Numero.TRES.valueI));
			items.add(item);
		}
		return items;
	}

	/**
	 * Metodo para configurar las transferencias de un consecutivo
	 */
	private List<TransferenciaDTO> getTransferencias(ResultSet res) throws Exception {
		List<TransferenciaDTO> transferencias = null;
		TransferenciaDTO transferencia;
		while (res.next()) {
			transferencia = new TransferenciaDTO();
			transferencia.setUsuario(res.getString(Numero.UNO.valueI));
			if (CommonConstant.ADMINISTRADOR.equals(transferencia.getUsuario())) {
				transferencia.setUsuarioCargo(CommonConstant.ADMINISTRADOR_CARGO);
			} else {
				transferencia.setUsuarioCargo(res.getString(Numero.DOS.valueI));
			}
			transferencia.setFechaTransferido(res.getString(Numero.TRES.valueI));
			if (transferencias == null) {
				transferencias = new ArrayList<>();
			}
			transferencias.add(transferencia);
		}
		return transferencias;
	}

	/**
	 * Metodo para configurar los items para los select filtro
	 */
	private List<ItemDTO> getItemsSelectFiltro(ResultSet res) throws Exception {
		List<ItemDTO> items = null;
		ItemDTO item;
		while (res.next()) {
			item = new ItemDTO();
			item.setId(res.getLong(Numero.UNO.valueI));
			item.setIdCampo(res.getLong(Numero.DOS.valueI));
			item.setValor(res.getString(Numero.TRES.valueI));
			if (items == null) {
				items = new ArrayList<>();
			}
			items.add(item);
		}
		return items;
	}

	/**
	 * Metodo para configurar los campos para los filtros de busqueda
	 */
	private List<CampoFiltroDTO> getCamposFiltro(ResultSet res) throws Exception {
		List<CampoFiltroDTO> campos = null;
		CampoFiltroDTO campo;
		while (res.next()) {
			campo = new CampoFiltroDTO();
			campo.setIdCampo(res.getLong(Numero.UNO.valueI));
			campo.setNombreCampo(res.getString(Numero.DOS.valueI));
			campo.setTipoCampo(res.getInt(Numero.TRES.valueI));
			if (campos == null) {
				campos = new ArrayList<>();
			}
			campos.add(campo);
		}
		return campos;
	}

	/**
	 * Metodo para configurar los datos del documento a descargar
	 */
	private DocumentoDTO getDatosDocumentoDescargar(ResultSet res) throws Exception {
		DocumentoDTO documento = null;
		if (res.next()) {
			documento = new DocumentoDTO();
			documento.setIdConsecutivo(Long.toString(res.getLong(Numero.UNO.valueI)));
			documento.setNombreDocumento(res.getString(Numero.DOS.valueI));
			documento.setTipoDocumento(res.getString(Numero.TRES.valueI));
			documento.setSizeDocumento(res.getString(Numero.CUATRO.valueI));
		}
		return documento;
	}

	/**
	 * Metodo para configurar los valores asociado a un consecutivo
	 */
	private List<CampoEntradaValueDTO> getConsecutivoValues(ResultSet res) throws Exception {
		List<CampoEntradaValueDTO> values = null;
		CampoEntradaValueDTO value;
		while (res.next()) {
			value = new CampoEntradaValueDTO();
			value.setIdValue(res.getLong(Numero.UNO.valueI));
			value.setNombreCampo(res.getString(Numero.DOS.valueI));
			value.setDescripcionCampo(res.getString(Numero.TRES.valueI));
			value.setValue(res.getString(Numero.CUATRO.valueI));
			if (values == null) {
				values = new ArrayList<>();
			}
			values.add(value);
		}
		return values;
	}

	/**
	 * Metodo para configurar los datos generales de un consecutivo
	 */
	private ConsecutivoDTO getConsecutivo(ResultSet res) throws Exception {
		ConsecutivoDTO consecutivo = null;
		if (res.next()) {
			consecutivo = new ConsecutivoDTO();
			consecutivo.setIdConsecutivo(res.getLong(Numero.UNO.valueI));
			consecutivo.setConsecutivo(res.getString(Numero.DOS.valueI));
			consecutivo.setNomenclatura(res.getString(Numero.TRES.valueI));
			consecutivo.setUsuario(res.getString(Numero.CUATRO.valueI));
			consecutivo.setFechaSolicitud(res.getString(Numero.CINCO.valueI));
			consecutivo.setIdEstado(res.getInt(Numero.SEIS.valueI));
			consecutivo.setEstado(Util.getEstadoNombre(consecutivo.getIdEstado()));
			consecutivo.setNomenclaturaDesc(res.getString(Numero.SIETE.valueI));
			consecutivo.setFechaAnulacion(res.getString(Numero.OCHO.valueI));
			if (CommonConstant.ADMINISTRADOR.equals(consecutivo.getUsuario())) {
				consecutivo.setUsuarioCargo(CommonConstant.ADMINISTRADOR_CARGO);	
			} else {
				consecutivo.setUsuarioCargo(res.getString(Numero.NUEVE.valueI));	
			}
			consecutivo.setIdNomenclatura(res.getLong(Numero.DIEZ.valueI));
		}
		return consecutivo;
	}

	/**
	 * Metodo para configurar los consecutivos del anio actual
	 */
	private List<ConsecutivoDTO> getConsecutivosAnioActual(ResultSet res) throws Exception {
		List<ConsecutivoDTO> consecutivos = null;
		ConsecutivoDTO consecutivo;
		while (res.next()) {
			consecutivo = new ConsecutivoDTO();
			consecutivo.setIdConsecutivo(res.getLong(Numero.UNO.valueI));
			consecutivo.setConsecutivo(res.getString(Numero.DOS.valueI));
			consecutivo.setNomenclatura(res.getString(Numero.TRES.valueI));
			consecutivo.setUsuario(res.getString(Numero.CUATRO.valueI));
			consecutivo.setFechaSolicitud(res.getString(Numero.CINCO.valueI));
			consecutivo.setIdEstado(res.getInt(Numero.SEIS.valueI));
			if (consecutivos == null) {
				consecutivos = new ArrayList<>();
			}
			consecutivos.add(consecutivo);
		}
		return consecutivos;
	}

	/**
	 * Metodo para configurar los datos del documento a eliminar
	 */
	private List<String> getDatosDocEliminar(ResultSet res) throws Exception {
		List<String> datos = new ArrayList<>();
		if (res.next()) {
			datos.add(Long.toString(res.getLong(Numero.UNO.valueI)));
			datos.add(res.getString(Numero.DOS.valueI));
		}
		return datos;
	}

	/**
	 * Metodo para configurar los datos de los documentos de un consecutivo
	 */
	private List<DocumentoDTO> getDocumentos(ResultSet res) throws Exception {
		List<DocumentoDTO> documentos = null;
		DocumentoDTO documento;
		while (res.next()) {
			documento = new DocumentoDTO();
			documento.setId(res.getLong(Numero.UNO.valueI));
			documento.setNombreDocumento(res.getString(Numero.DOS.valueI));
			documento.setTipoDocumento(TiposDocumentos.getTipoDocumento(res.getString(Numero.TRES.valueI)));
			documento.setSizeDocumento(res.getString(Numero.CUATRO.valueI));
			documento.setFechaCargue(res.getString(Numero.CINCO.valueI));
			if (documentos == null) {
				documentos = new ArrayList<>();
			}
			documentos.add(documento);
		}
		return documentos;
	}

	/**
	 * Metodo para configurar los datos de los usuarios de bienvenida
	 */
	private Object getWelComeUsuarios(ResultSet res) throws Exception {
		List<WelcomeUsuarioDTO> usuarios = null;
		WelcomeUsuarioDTO usuario;
		while (res.next()) {
			usuario = new WelcomeUsuarioDTO();
			usuario.setIdUsuario(res.getLong(Numero.UNO.valueI));
			usuario.setNombreCompleto(res.getString(Numero.DOS.valueI));
			usuario.setCargo(res.getString(Numero.TRES.valueI));
			usuario.setIdEstado(res.getInt(Numero.CUATRO.valueI));
			usuario.setEstado(Util.getEstadoNombre(usuario.getIdEstado()));
			usuario.setCantidadConsecutivos(res.getInt(Numero.CINCO.valueI));
			if (usuarios == null) {
				usuarios = new ArrayList<>();
			}
			usuarios.add(usuario);
		}
		return usuarios;
	}

	/**
	 * Metodo para configurar los datos de las nomenclaturas de bienvenida
	 */
	private Object getWelComeNomenclaturas(ResultSet res) throws Exception {
		List<WelcomeNomenclaturaDTO> nomenclaturas = null;
		WelcomeNomenclaturaDTO nomenclatura;
		while (res.next()) {
			nomenclatura = new WelcomeNomenclaturaDTO();
			nomenclatura.setIdNomenclatura(res.getLong(Numero.UNO.valueI));
			nomenclatura.setNomenclatura(res.getString(Numero.DOS.valueI));
			nomenclatura.setDescripcion(res.getString(Numero.TRES.valueI));
			nomenclatura.setCantidadConsecutivos(res.getInt(Numero.CUATRO.valueI));
			if (nomenclaturas == null) {
				nomenclaturas = new ArrayList<>();
			}
			nomenclaturas.add(nomenclatura);
		}
		return nomenclaturas;
	}

	/**
	 * Metodo para configurar los datos de la secuencia de la nomenclatura
	 */
	private List<Integer> getSecuenciaNomenclatura(ResultSet res) throws Exception {
		List<Integer> respuesta = new ArrayList<>();
		if (res.next()) {
			respuesta.add(res.getInt(Numero.UNO.valueI));
			respuesta.add(res.getInt(Numero.DOS.valueI));
			respuesta.add(res.getInt(Numero.TRES.valueI));
		}
		return respuesta;
	}

	/**
	 * Metodo para configurar el detalle de los campos
	 */
	private List<CampoEntradaDetalleDTO> getDtlNomenclaturaCampos(ResultSet res) throws Exception {

		// variables utilizadas en el ciclo
		List<CampoEntradaDetalleDTO> detalle = null;
		CampoEntradaDetalleDTO campo;
		String restricciones;

		// se recorre cada campo
		while (res.next()) {
			if (detalle == null) {
				detalle = new ArrayList<>();
			}

			// datos basicos del campo
			campo = new CampoEntradaDetalleDTO();
			campo.setId(res.getLong(Numero.UNO.valueI));
			campo.setNombre(res.getString(Numero.DOS.valueI));
			campo.setDescripcion(res.getString(Numero.TRES.valueI));
			campo.setTipoCampo(res.getInt(Numero.CUATRO.valueI));
			restricciones = res.getString(Numero.CINCO.valueI);
			campo.setIdCampoNomenclatura(res.getLong(Numero.SEIS.valueI));

			// restricciones del campo
			if (restricciones != null && !restricciones.isEmpty()) {
				campo.setRestricciones(Arrays.asList(restricciones.split(CommonConstant.PUNTO_COMA)));
			}
			detalle.add(campo);
		}
		return detalle;
	}
}
