package adminfree.mappers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adminfree.constants.CommonConstant;
import adminfree.constants.TiposDocumentos;
import adminfree.dtos.correspondencia.CampoEntradaDetalleDTO;
import adminfree.dtos.correspondencia.CampoEntradaValueDTO;
import adminfree.dtos.correspondencia.CampoFiltroDTO;
import adminfree.dtos.correspondencia.ConsecutivoDTO;
import adminfree.dtos.correspondencia.DocumentoDTO;
import adminfree.dtos.correspondencia.WelcomeNomenclaturaDTO;
import adminfree.dtos.correspondencia.WelcomeUsuarioDTO;
import adminfree.enums.Numero;
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
		}
		return result;
	}

	/**
	 * Metodo para configurar los campos para los filtros de busqueda
	 */
	private List<CampoFiltroDTO> getCamposFiltro(ResultSet res) throws Exception {
		List<CampoFiltroDTO> campos = null;
		CampoFiltroDTO campo;
		while (res.next()) {
			campo = new CampoFiltroDTO();
			campo.setIdCampo(res.getLong(Numero.UNO.value));
			campo.setNombreCampo(res.getString(Numero.DOS.value));
			campo.setTipoCampo(res.getInt(Numero.TRES.value));
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
			documento.setIdConsecutivo(Long.toString(res.getLong(Numero.UNO.value)));
			documento.setNombreDocumento(res.getString(Numero.DOS.value));
			documento.setTipoDocumento(res.getString(Numero.TRES.value));
			documento.setSizeDocumento(res.getString(Numero.CUATRO.value));
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
			value.setIdValue(res.getLong(Numero.UNO.value));
			value.setNombreCampo(res.getString(Numero.DOS.value));
			value.setDescripcionCampo(res.getString(Numero.TRES.value));
			value.setValue(res.getString(Numero.CUATRO.value));
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
			consecutivo.setIdConsecutivo(res.getLong(Numero.UNO.value));
			consecutivo.setConsecutivo(res.getString(Numero.DOS.value));
			consecutivo.setNomenclatura(res.getString(Numero.TRES.value));
			consecutivo.setUsuario(res.getString(Numero.CUATRO.value));
			consecutivo.setFechaSolicitud(res.getString(Numero.CINCO.value));
			consecutivo.setIdEstado(res.getInt(Numero.SEIS.value));
			consecutivo.setEstado(Util.getEstadoNombre(consecutivo.getIdEstado()));
			consecutivo.setNomenclaturaDesc(res.getString(Numero.SIETE.value));
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
			consecutivo.setIdConsecutivo(res.getLong(Numero.UNO.value));
			consecutivo.setConsecutivo(res.getString(Numero.DOS.value));
			consecutivo.setNomenclatura(res.getString(Numero.TRES.value));
			consecutivo.setUsuario(res.getString(Numero.CUATRO.value));
			consecutivo.setFechaSolicitud(res.getString(Numero.CINCO.value));
			consecutivo.setIdEstado(res.getInt(Numero.SEIS.value));
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
			datos.add(Long.toString(res.getLong(Numero.UNO.value)));
			datos.add(res.getString(Numero.DOS.value));
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
			documento.setId(res.getLong(Numero.UNO.value));
			documento.setNombreDocumento(res.getString(Numero.DOS.value));
			documento.setTipoDocumento(TiposDocumentos.getTipoDocumento(res.getString(Numero.TRES.value)));
			documento.setSizeDocumento(res.getString(Numero.CUATRO.value));
			documento.setFechaCargue(res.getString(Numero.CINCO.value));
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
			usuario.setIdUsuario(res.getLong(Numero.UNO.value));
			usuario.setNombreCompleto(res.getString(Numero.DOS.value));
			usuario.setCargo(res.getString(Numero.TRES.value));
			usuario.setIdEstado(res.getInt(Numero.CUATRO.value));
			usuario.setEstado(Util.getEstadoNombre(usuario.getIdEstado()));
			usuario.setCantidadConsecutivos(res.getInt(Numero.CINCO.value));
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
			nomenclatura.setIdNomenclatura(res.getLong(Numero.UNO.value));
			nomenclatura.setNomenclatura(res.getString(Numero.DOS.value));
			nomenclatura.setDescripcion(res.getString(Numero.TRES.value));
			nomenclatura.setCantidadConsecutivos(res.getInt(Numero.CUATRO.value));
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
			respuesta.add(res.getInt(Numero.UNO.value));
			respuesta.add(res.getInt(Numero.DOS.value));
			respuesta.add(res.getInt(Numero.TRES.value));
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
			campo.setId(res.getLong(Numero.UNO.value));
			campo.setNombre(res.getString(Numero.DOS.value));
			campo.setDescripcion(res.getString(Numero.TRES.value));
			campo.setTipoCampo(res.getInt(Numero.CUATRO.value));
			restricciones = res.getString(Numero.CINCO.value);
			campo.setIdCampoNomenclatura(res.getLong(Numero.SEIS.value));

			// restricciones del campo
			if (restricciones != null && !restricciones.isEmpty()) {
				campo.setRestricciones(Arrays.asList(restricciones.split(CommonConstant.PUNTO_COMA)));
			}
			detalle.add(campo);
		}
		return detalle;
	}
}
