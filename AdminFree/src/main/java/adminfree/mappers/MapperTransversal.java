package adminfree.mappers;

import java.sql.ResultSet;

import adminfree.enums.Numero;

/**
 * Mapper transversal que contiene las implementaciones JDBC comunes
 * 
 * @author Carlos Andres Diaz
 *
 */
public class MapperTransversal extends Mapper {

	/** Son los tipos de mapper que soporta este modulo */
	public static final int COUNT = 1;
	public static final int GET_ID = 2;
	public static final int GET_SOLO_UN_STRING = 3;

	/** Objecto statica que se comporta como una unica instancia */
	private static MapperTransversal instance;

	/**
	 * Constructor del Mapper no instanciable
	 */
	private MapperTransversal() {
	}

	/**
	 * Retorna una instancia de este tipo de Mapper
	 */
	public static MapperTransversal get(int tipoMapper) {
		if (instance == null) {
			instance = new MapperTransversal();
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
			case MapperTransversal.COUNT:
				result = getCount(res);
				break;
	
			case MapperTransversal.GET_ID:
				result = getId(res);
				break;
	
			case MapperTransversal.GET_SOLO_UN_STRING:
				result = getSoloUnString(res);
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
	 * Mapper para obtener el identificador de una entidad
	 */
	private Long getId(ResultSet res) throws Exception {
		if (res.next()) {
			return res.getLong(Numero.UNO.value);
		}
		return null;
	}

	/**
	 * Mapper para obtener solo un valor texto
	 */
	private String getSoloUnString(ResultSet res) throws Exception {
		if (res.next()) {
			return res.getString(Numero.UNO.value);
		}
		return null;
	}
}
