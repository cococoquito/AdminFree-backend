package adminfree.mappers;

import java.sql.ResultSet;

/**
 * Clase abstracta que contiene los metodos que debe implementar los mapper
 * 
 * @author Carlos Andres Diaz
 *
 */
public abstract class Mapper {

	/** Es el tipo de MAPPER a ejecutar */
	protected int tipoMapper;

	/**
	 * Metodo que es ejecutado para MAPPEAR los datos de acuerdo a un ResultSet
	 *
	 * @param res, resultado de acuerdo a la consulta
	 * @return objecto con sus datos configurado de acuerdo al Mapper
	 */
	public abstract Object execute(ResultSet res) throws Exception;

	/**
	 * Metodo que es ejecutado para MAPPEAR los datos de acuerdo a un ResultSet
	 *
	 * @param res, resultado de acuerdo a la consulta
	 * @param parametros que necesita el mapper para ser procesado
	 * @return objecto con sus datos configurado de acuerdo al Mapper
	 */
	public abstract Object executeParams(ResultSet res, Object parametros) throws Exception;
}
