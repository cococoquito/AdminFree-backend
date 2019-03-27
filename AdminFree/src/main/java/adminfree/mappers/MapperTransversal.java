package adminfree.mappers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import adminfree.constants.CommonConstant;
import adminfree.dtos.transversal.SelectItemDTO;
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
	public static final int GET_ITEMS_USUARIOS = 4;

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
	 * @param parametros que necesita el mapper para ser procesado
	 * @return objecto con sus datos configurado de acuerdo al Mapper
	 */
	@Override
	public Object executeParams(ResultSet res, Object parametros) throws Exception {
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
			case MapperTransversal.COUNT:
				result = getCount(res);
				break;
	
			case MapperTransversal.GET_ID:
				result = getId(res);
				break;
	
			case MapperTransversal.GET_SOLO_UN_STRING:
				result = getSoloUnString(res);
				break;

			case MapperTransversal.GET_ITEMS_USUARIOS:
				result = getItemsUsuarios(res);
				break;
			}
		return result;
	}

	/**
	 * Mapper para configurar los items para las listas desplegables
	 * de los usuarios parametrizados en el sistema
	 */
	private List<SelectItemDTO> getItemsUsuarios(ResultSet res) throws Exception {
		List<SelectItemDTO> items = new ArrayList<>();
		SelectItemDTO item = new SelectItemDTO();
		item.setId(CommonConstant.ID_ADMINISTRADOR.longValue());
		item.setLabel(CommonConstant.ADMINISTRADOR);
		items.add(item);
		while (res.next()) {
			item = new SelectItemDTO();
			item.setId(res.getLong(Numero.UNO.valueI));
			item.setLabel(res.getString(Numero.DOS.valueI));
			items.add(item);
		}
		return items;
	}

	/**
	 * Mapper para configurar el COUNT de un SELECT
	 */
	private Object getCount(ResultSet res) throws Exception {
		if (res.next()) {
			return res.getLong(Numero.UNO.valueI);
		}
		return Numero.ZERO.valueL;
	}

	/**
	 * Mapper para obtener el identificador de una entidad
	 */
	private Long getId(ResultSet res) throws Exception {
		if (res.next()) {
			return res.getLong(Numero.UNO.valueI);
		}
		return null;
	}

	/**
	 * Mapper para obtener solo un valor texto
	 */
	private String getSoloUnString(ResultSet res) throws Exception {
		if (res.next()) {
			return res.getString(Numero.UNO.valueI);
		}
		return null;
	}
}
