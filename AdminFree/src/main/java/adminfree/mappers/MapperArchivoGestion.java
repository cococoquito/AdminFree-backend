package adminfree.mappers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import adminfree.dtos.archivogestion.TipoDocumentalDTO;
import adminfree.enums.Numero;

/**
 * Mapper que contiene las implementaciones JDBC para el modulo de archivo de gestion
 *
 * @author Carlos Andres Diaz
 *
 */
public class MapperArchivoGestion extends Mapper {

	/** Son los tipos de mapper que soporta este modulo */
	public static final int GET_TIPOS_DOCUMENTALES = 1;

	/** Objecto statica que se comporta como una unica instancia */
	private static MapperArchivoGestion instance;

	/**
	 * Constructor del Mapper no instanciable
	 */
	private MapperArchivoGestion() {}

	/**
	 * Retorna una instancia de este tipo de Mapper
	 */
	public static MapperArchivoGestion get(int tipoMapper) {
		if (instance == null) {
			instance = new MapperArchivoGestion();
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

			case MapperArchivoGestion.GET_TIPOS_DOCUMENTALES:
				result = getTiposDocumentales(res);
				break;
		}
		return result;
	}

	/**
	 * Metodo para configurar los tipos documentales
	 */
	private Object getTiposDocumentales(ResultSet res) throws Exception {
		List<TipoDocumentalDTO> tipos = null;
		TipoDocumentalDTO tipo;
		while (res.next()) {
			tipo = new TipoDocumentalDTO();
			tipo.setId(res.getInt(Numero.UNO.valueI));
			tipo.setNombre(res.getString(Numero.DOS.valueI));
			if (tipos == null) {
				tipos = new ArrayList<>();
			}
			tipos.add(tipo);
		}
		return tipos;
	}
}
