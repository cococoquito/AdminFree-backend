package adminfree.mappers;

import java.sql.ResultSet;

import adminfree.dtos.correspondencia.NomenclaturaDetalleCamposDTO;
import adminfree.dtos.correspondencia.NomenclaturaDetalleDTO;
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
		}
		return result;
	}

	/**
	 * Metodo para configurar el detalle de la nomenclatura
	 */
	private NomenclaturaDetalleDTO getDtlNomenclatura(ResultSet res) throws Exception {
		NomenclaturaDetalleDTO nomenclatura = null;
		while (res.next()) {
			if (nomenclatura == null) {
				// datos basicos de la nomenclatura
				nomenclatura = new NomenclaturaDetalleDTO();
				nomenclatura.setId(res.getLong(Numero.UNO.value));
				nomenclatura.setNomenclatura(res.getString(Numero.DOS.value));
				nomenclatura.setDescripcion(res.getString(Numero.TRES.value));
				nomenclatura.setConsecutivoInicial(res.getInt(Numero.CUATRO.value));
				nomenclatura.setCantidadConsecutivos(res.getInt(Numero.CINCO.value));

				// campos asociados a la nomenclatura
				configurarCampo(nomenclatura, res);
			} else {
				configurarCampo(nomenclatura, res);
			}
		}
		return nomenclatura;
	}

	/**
	 * Metodo que permite configurar la restriccion para un campo de entrada
	 */
	private void configurarCampo(NomenclaturaDetalleDTO nomenclatura, ResultSet res) throws Exception {
		Long idCampo = res.getLong(Numero.SEIS.value);
		if (idCampo != null && idCampo > Numero.ZERO.value) {
			NomenclaturaDetalleCamposDTO campo = new NomenclaturaDetalleCamposDTO();
			campo.setId(idCampo);
			campo.setNombre(res.getString(Numero.SIETE.value));
			campo.setDescripcion(res.getString(Numero.OCHO.value));
			campo.setTipoCampo(Util.getTipoCampoNombre(res.getInt(Numero.NUEVE.value)));
			nomenclatura.agregarCampo(campo);
		}
	}
}
