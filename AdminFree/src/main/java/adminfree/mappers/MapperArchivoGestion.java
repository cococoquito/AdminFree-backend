package adminfree.mappers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import adminfree.constants.CommonConstant;
import adminfree.dtos.archivogestion.SerieDocumentalDTO;
import adminfree.dtos.archivogestion.SubSerieDocumentalDTO;
import adminfree.dtos.archivogestion.TipoDocumentalDTO;
import adminfree.enums.Numero;

/**
 * Mapper que contiene las implementaciones JDBC para el modulo de archivo de gestion
 *
 * @author Carlos Andres Diaz
 *
 */
@SuppressWarnings("unchecked")
public class MapperArchivoGestion extends Mapper {

	/** Son los tipos de mapper que soporta este modulo */
	public static final int GET_TIPOS_DOCUMENTALES = 1;
	public static final int GET_SERIES_DOCUMENTALES = 2;
	public static final int GET_SUBSERIES_SERIES = 3;
	public static final int GET_TIPOS_DOC_SERIES = 4;
	public static final int GET_TIPOS_DOC_SUBSERIES = 5;

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
		Object result = null;
		switch (this.tipoMapper) {

			case MapperArchivoGestion.GET_SERIES_DOCUMENTALES:
				result = getSeriesDocumentales(res, parametro);
				break;

			case MapperArchivoGestion.GET_SUBSERIES_SERIES:
				getSubSeriesSeries(res, parametro);
				break;

			case MapperArchivoGestion.GET_TIPOS_DOC_SERIES:
				getTiposDocSeries(res, parametro);
				break;

			case MapperArchivoGestion.GET_TIPOS_DOC_SUBSERIES:
				getTiposDocSubSeries(res, parametro);
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

			case MapperArchivoGestion.GET_TIPOS_DOCUMENTALES:
				result = getTiposDocumentales(res);
				break;
		}
		return result;
	}

	/**
	 * Metodo que permite configurar los tipos documentales de cada subserie documental
	 */
	private void getTiposDocSubSeries(ResultSet res, Object parametro) throws Exception {

		// el parametro son las series para configurar sus tipos documentales
		List<SerieDocumentalDTO> series = (List<SerieDocumentalDTO>) parametro;

		// se recorre cada tipo documental
		List<SubSerieDocumentalDTO> subSeries;
		TipoDocumentalDTO tipoDoc;
		Long idSubSerie;
		while (res.next()) {

			// datos del tipo documental
			tipoDoc = new TipoDocumentalDTO();
			idSubSerie = res.getLong(Numero.UNO.valueI);
			tipoDoc.setId(res.getInt(Numero.DOS.valueI));
			tipoDoc.setNombre(res.getString(Numero.TRES.valueI));

			// se configura este tipo doc en la subserie correspondiente
			forserie:
			for (SerieDocumentalDTO serie : series) {
				subSeries = serie.getSubSeries();
				if (subSeries != null) {
					for (SubSerieDocumentalDTO subSerie : subSeries) {
						if (subSerie.getIdSubSerie().equals(idSubSerie)) {
							subSerie.agregarTipoDocumental(tipoDoc);
							break forserie;
						}
					}
				}
			}
		}
	}

	/**
	 * Metodo que permite configurar los tipos documentales de cada serie documental
	 */
	private void getTiposDocSeries(ResultSet res, Object parametro) throws Exception {

		// el parametro son las series para configurar sus tipos documentales
		List<SerieDocumentalDTO> series = (List<SerieDocumentalDTO>) parametro;

		// se recorre cada tipo documental
		TipoDocumentalDTO tipoDoc;
		Long idSerie;
		while (res.next()) {

			// datos del tipo documental
			tipoDoc = new TipoDocumentalDTO();
			idSerie = res.getLong(Numero.UNO.valueI);
			tipoDoc.setId(res.getInt(Numero.DOS.valueI));
			tipoDoc.setNombre(res.getString(Numero.TRES.valueI));

			// se configura este tipo doc en la serie correspondiente
			for (SerieDocumentalDTO serie : series) {
				if (serie.getIdSerie().equals(idSerie)) {
					serie.agregarTipoDocumental(tipoDoc);
					break;
				}
			}
		}
	}

	/**
	 * Metodo que permite configurar las subseries de cada serie documental
	 */
	private void getSubSeriesSeries(ResultSet res, Object parametro) throws Exception {

		// params: 0=series documentales, 1=StringBuilder para concatenar los ids de cada subserie
		List<Object> params = (List<Object>) parametro;
		List<SerieDocumentalDTO> series = (List<SerieDocumentalDTO>) params.get(Numero.ZERO.valueI);
		StringBuilder idsSubSerie = (StringBuilder) params.get(Numero.UNO.valueI);

		// se recorre cada subserie
		final int ZERO = Numero.ZERO.valueI.intValue();
		final int UNO = Numero.UNO.valueI.intValue();
		SubSerieDocumentalDTO subserie;
		while (res.next()) {

			// datos generales de la subserie
			subserie = new SubSerieDocumentalDTO();
			subserie.setIdSubSerie(res.getLong(Numero.UNO.valueI));
			subserie.setIdSerie(res.getLong(Numero.DOS.valueI));
			subserie.setCodigo(res.getString(Numero.TRES.valueI));
			subserie.setNombre(res.getString(Numero.CUATRO.valueI));
			subserie.setAG(res.getInt(Numero.CINCO.valueI));
			if (res.wasNull()) {
				subserie.setAG(null);
			}
			subserie.setAC(res.getInt(Numero.SEIS.valueI));
			if (res.wasNull()) {
				subserie.setAC(null);
			}
			if (res.getInt(Numero.SIETE.valueI) == UNO) {
				subserie.setCT(true);
			}
			if (res.getInt(Numero.OCHO.valueI) == UNO) {
				subserie.setM(true);
			}
			if (res.getInt(Numero.NUEVE.valueI) == UNO) {
				subserie.setS(true);
			}
			if (res.getInt(Numero.DIEZ.valueI) == UNO) {
				subserie.setE(true);
			}
			subserie.setProcedimiento(res.getString(Numero.ONCE.valueI));

			// se configura esta subserie en la serie correspondiente
			for (SerieDocumentalDTO serie : series) {
				if (serie.getIdSerie().equals(subserie.getIdSerie())) {
					serie.agregarSubSerie(subserie);
					break;
				}
			}

			// se concatena el id de la subserie en el parametro
			if (idsSubSerie.length() > ZERO) {
				idsSubSerie.append(CommonConstant.COMA);
			}
			idsSubSerie.append(subserie.getIdSubSerie());
		}
	}

	/**
	 * Metodo que permite configurar las series documentales
	 */
	private Object getSeriesDocumentales(ResultSet res, Object parametro) throws Exception {

		// lista de series documentales construidas para retornar
		List<SerieDocumentalDTO> series = new ArrayList<>();

		// el parametro es para concatenar cada id de las series consultadas
		StringBuilder ids = (StringBuilder) parametro;

		// se utilizan para hacer comparaciones dentro del FOR
		final int ZERO = Numero.ZERO.valueI.intValue();
		final int UNO = Numero.UNO.valueI.intValue();

		// se recorre cada registro consultado
		SerieDocumentalDTO serie;
		while (res.next()) {

			// datos generales de la serie documental
			serie = new SerieDocumentalDTO();
			serie.setIdSerie(res.getLong(Numero.UNO.valueI));
			serie.setCodigo(res.getString(Numero.DOS.valueI));
			serie.setNombre(res.getString(Numero.TRES.valueI));
			serie.setAG(res.getInt(Numero.CUATRO.valueI));
			if (res.wasNull()) {
				serie.setAG(null);
			}
			serie.setAC(res.getInt(Numero.CINCO.valueI));
			if (res.wasNull()) {
				serie.setAC(null);
			}
			if (res.getInt(Numero.SEIS.valueI) == UNO) {
				serie.setCT(true);
			}
			if (res.getInt(Numero.SIETE.valueI) == UNO) {
				serie.setM(true);
			}
			if (res.getInt(Numero.OCHO.valueI) == UNO) {
				serie.setS(true);
			}
			if (res.getInt(Numero.NUEVE.valueI) == UNO) {
				serie.setE(true);
			}
			serie.setProcedimiento(res.getString(Numero.DIEZ.valueI));

			// se agrega la serie en la lista
			series.add(serie);

			// se concatena el id en el parametro
			if (ids.length() > ZERO) {
				ids.append(CommonConstant.COMA);
			}
			ids.append(serie.getIdSerie());
		}
		return series;
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
