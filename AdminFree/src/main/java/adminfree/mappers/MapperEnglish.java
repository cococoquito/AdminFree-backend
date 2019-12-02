package adminfree.mappers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import adminfree.dtos.english.SeriesDTO;
import adminfree.enums.Numero;

/**
 * Mapper que contiene las implementaciones JDBC para el modulo de learning english
 *
 * @author Carlos Andres Diaz
 *
 */
public class MapperEnglish extends Mapper {

	/** Son los tipos de mapper que soporta este modulo */
	public static final int GET_SERIES = 1;

	/** Objecto statica que se comporta como una unica instancia */
	private static MapperEnglish instance;

	/**
	 * Constructor del Mapper no instanciable
	 */
	private MapperEnglish() {
	}

	/**
	 * Retorna una instancia de este tipo de Mapper
	 */
	public static MapperEnglish get(int tipoMapper) {
		if (instance == null) {
			instance = new MapperEnglish();
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
			case MapperEnglish.GET_SERIES:
				result = getSeries(res);
				break;
		}
		return result;
	}

	/**
	 * Mapper para obtener la lista de SERIES parametrizados en el sistema
	 */
	private Object getSeries(ResultSet res) throws Exception {
		List<SeriesDTO> series = new ArrayList<>();
		SeriesDTO serie = null;
		while (res.next()) {
			serie = new SeriesDTO();
			serie.setId(res.getLong(Numero.UNO.valueI));
			serie.setName(res.getString(Numero.DOS.valueI));
			serie.setUrl(res.getString(Numero.TRES.valueI));
			serie.setImg(res.getBytes(Numero.CUATRO.valueI));
			series.add(serie);
		}
		return series;
	}
}
