package adminfree.mappers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adminfree.constants.CommonConstant;
import adminfree.dtos.correspondencia.CampoEntradaDetalleDTO;
import adminfree.dtos.correspondencia.NomenclaturaDetalleDTO;
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
	public static final int GET_DTL_NOMENCLATURA = 1;
	public static final int GET_DTL_NOMENCLATURA_CAMPOS = 2;
	public static final int GET_SECUENCIA_NOMENCLATURA = 3;
	public static final int GET_WELCOME_NOMENCLATURAS = 4;
	public static final int GET_WELCOME_USUARIOS = 5;

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
			case MapperCorrespondencia.GET_DTL_NOMENCLATURA:
				result = getDtlNomenclatura(res);
				break;

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
		}
		return result;
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
			usuario.setEstado(Util.getEstadoNombre(res.getInt(Numero.CUATRO.value)));
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

	/**
	 * Metodo para configurar el detalle de la nomenclatura
	 */
	private NomenclaturaDetalleDTO getDtlNomenclatura(ResultSet res) throws Exception {
		NomenclaturaDetalleDTO nomenclatura = null;
		if (res.next()) {
			nomenclatura = new NomenclaturaDetalleDTO();
			nomenclatura.setId(res.getLong(Numero.UNO.value));
			nomenclatura.setNomenclatura(res.getString(Numero.DOS.value));
			nomenclatura.setDescripcion(res.getString(Numero.TRES.value));
			nomenclatura.setConsecutivoInicial(res.getInt(Numero.CUATRO.value));
			nomenclatura.setUltimoConsecutivoSolicitado(res.getInt(Numero.CINCO.value));
		}
		return nomenclatura;
	}
}
