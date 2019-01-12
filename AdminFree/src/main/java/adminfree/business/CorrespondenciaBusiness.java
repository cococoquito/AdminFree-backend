package adminfree.business;

import java.sql.Connection;
import java.sql.Types;
import java.util.Calendar;

import adminfree.constants.CommonConstant;
import adminfree.constants.SQLCorrespondencia;
import adminfree.dtos.correspondencia.NomenclaturaDetalleDTO;
import adminfree.mappers.MapperCorrespondencia;
import adminfree.persistence.CommonDAO;
import adminfree.persistence.ValueSQL;

/**
 * 
 * Clase que contiene los procesos de negocio para el modulo de Correspondencia
 * 
 * @author Carlos Andres Diaz
 *
 */
public class CorrespondenciaBusiness extends CommonDAO {

	/**
	 * Metodo que permite obtener el detalle de una nomenclatura
	 * 
	 * @param idNomenclatura, identificador de la nomenclatura
	 * @return DTO con los datos de la nomenclatura
	 */
	public NomenclaturaDetalleDTO getDetalleNomenclatura(Long idNomenclatura, Connection connection) throws Exception {
		return (NomenclaturaDetalleDTO) find(connection,
				SQLCorrespondencia.GET_DTL_NOMENCLATURA.replace(
						CommonConstant.INTERROGACION_1,
						Calendar.getInstance().get(Calendar.YEAR) + ""),
				MapperCorrespondencia.get(MapperCorrespondencia.GET_DTL_NOMENCLATURA), 
				ValueSQL.get(idNomenclatura, Types.BIGINT));
	}
}
