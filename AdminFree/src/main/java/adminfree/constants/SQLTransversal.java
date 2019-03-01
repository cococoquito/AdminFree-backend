package adminfree.constants;

import java.sql.Types;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import adminfree.enums.Numero;
import adminfree.persistence.ValueSQL;
import adminfree.utilities.Util;

/**
*
* Clase constante que contiene los DML Y DDL que se utilizan en todos los modulos
*
* @author Carlos Andres Diaz
*/
public class SQLTransversal {

	/** SQL para obtener los usuarios para las listas desplegables */
	public static final String GET_ITEMS_USUARIOS = "SELECT ID_USUARIO,NOMBRE FROM USUARIOS WHERE CLIENTE=?";

	/**
	 * Metodo que permite construir el LIMIT para las consultas paginadas
	 */
	public static void getLimitSQL(String skip, String rowsPage, StringBuilder sql) {
		sql.append(" LIMIT ").append(skip).append(",").append(rowsPage);
	}

	/**
	 * Metodo que permite construir el COUNT para obtener la cantidad de una consulta
	 */
	public static String getSQLCount(StringBuilder from) {
		StringBuilder sql = new StringBuilder("SELECT COUNT(*)");
		sql.append(from);
		return sql.toString();
	}

	/**
	 * Metodo que permite configurar el filtro de busqueda para
	 * las fechas de solicitud de los consecutivos
	 */
	public static void getFilterFechaSolicitud(
			Date fechaSolicitudInicial,
			Date fechaSolicitudFinal,
			StringBuilder sql) {

		// se configuran los valores por defecto
		String anioActual = Calendar.getInstance().get(Calendar.YEAR) + "";
		String mesInicial = "01";
		String mesFinal = "12";
		String diaInicial = "01";
		String diaFinal = "31";

		// Fecha inicial de la solicitud
		if (fechaSolicitudInicial != null) {
			LocalDate inicial = fechaSolicitudInicial.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			diaInicial = Util.setPrefijoZeros(CommonConstant.PREFIJO_ZEROS_2, inicial.getDayOfMonth() + "");
			mesInicial = Util.setPrefijoZeros(CommonConstant.PREFIJO_ZEROS_2, inicial.getMonthValue() + "");
		}

		// Fecha final de la solicitud
		if (fechaSolicitudFinal != null) {
			LocalDate ffinal = fechaSolicitudFinal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			diaFinal = Util.setPrefijoZeros(CommonConstant.PREFIJO_ZEROS_2, ffinal.getDayOfMonth() + "");
			mesFinal = Util.setPrefijoZeros(CommonConstant.PREFIJO_ZEROS_2, ffinal.getMonthValue() + "");
		}

		// filtro para la fecha inicial de la solicitud
		sql.append(" CON.FECHA_SOLICITUD >='");
		sql.append(anioActual).append("-");
		sql.append(mesInicial).append("-");
		sql.append(diaInicial).append(" 00:00:00'");

		// filtro para la fecha final de la solicitud
		sql.append(" AND CON.FECHA_SOLICITUD <='");
		sql.append(anioActual).append("-");
		sql.append(mesFinal).append("-");
		sql.append(diaFinal).append(" 23:59:59'");
	}

	/**
	 * Metodo que permite construir el filtro de busqueda por nomenclaturas
	 */
	public static void getFilterNomenclaturas(
			String nomenclaturas,
			List<ValueSQL> parametros,
			StringBuilder sql) {

		// se verifica si el usuario ingreso filtro por nomenclatura
		if (!Util.isNull(nomenclaturas)) {

			// el filtro es por el campo NOMENCLATURAS.NOMENCLATURA
			sql.append(" AND NOM.NOMENCLATURA IN(");

			// pueden llegar varias nomenclaturas separadas por coma
			String[] split = nomenclaturas.split(CommonConstant.COMA);

			// se recorre todas las nomenclaturas construyendo el filtro
			boolean isPrimero = true;
			for (String nomenclatura : split) {

				// El valor de la nomenclatura no puede ser vacia
				nomenclatura = nomenclatura.trim();
				if (nomenclatura.length() > Numero.ZERO.value) {

					// se configura el valor ingresado
					if (isPrimero) {
						sql.append(CommonConstant.INTERROGACION);
						isPrimero = false;
					} else {
						sql.append(CommonConstant.COMA_INTERROGACION);
					}
					parametros.add(ValueSQL.get(nomenclatura, Types.VARCHAR));
				}
			}
			sql.append(")");
		}
	}

	/**
	 * Metodo que permite construir el filtro de busqueda por numeros de consecutivos
	 */
	public static void getFilterConsecutivos(
			String consecutivos,
			List<ValueSQL> parametros,
			StringBuilder sql) {

		// se verifica si el usuario ingreso filtro por consecutivos
		if (!Util.isNull(consecutivos)) {

			// el filtro es por el campo CONSECUTIVOS_id_cliente.CONSECUTIVO
			sql.append(" AND CON.CONSECUTIVO IN(");

			// pueden llegar varios consecutivos separadas por coma
			String[] split = consecutivos.split(CommonConstant.COMA);

			// se recorre todos los consecutivos construyendo el filtro
			boolean isPrimero = true;
			for (String consecutivo : split) {

				// El valor del consecutivo no puede ser vacia
				consecutivo = consecutivo.trim();
				if (consecutivo.length() > Numero.ZERO.value) {

					// se configura el valor ingresado
					if (isPrimero) {
						sql.append(CommonConstant.INTERROGACION);
						isPrimero = false;
					} else {
						sql.append(CommonConstant.COMA_INTERROGACION);
					}
					parametros.add(ValueSQL.get(Util.setPrefijoZeros(CommonConstant.PREFIJO_ZEROS_4, consecutivo), Types.VARCHAR));
				}
			}
			sql.append(")");
		}
	}

	/**
	 * Metodo que permite construir el filtro de busqueda por Usuarios
	 */
	public static void getFilterUsuarios(List<Integer> idsUsuarios, StringBuilder sql) {

		// se verifica si hay usuarios seleccionados para el filtro
		if (idsUsuarios != null && !idsUsuarios.isEmpty()) {

			// se utiliza para concatenar todos los ids de los usuarios
			StringBuilder sqlUsuarios = null;

			// se utiliza para identificar si hay filtro para el Admin
			boolean filtroAdmin = false;

			// se recorre todos los ids de los usuarios
			for (Integer idUsuario : idsUsuarios) {

				// se valida si este usuario es el administrador
				if (!CommonConstant.ID_ADMINISTRADOR.equals(idUsuario)) {

					// se concatena solamente los usuarios que no son admin
					if (sqlUsuarios == null) {
						sqlUsuarios = new StringBuilder("(").append(idUsuario);
					} else {
						sqlUsuarios.append(",").append(idUsuario);
					}
				} else {
					filtroAdmin = true;
				}
			}

			// dependiendo del tipo de usuario se construye el filtro
			if (filtroAdmin && sqlUsuarios != null) {
				sql.append(" AND (US.ID_USUARIO IN").append(sqlUsuarios).append(") OR US.ID_USUARIO IS NULL)");
			} else if (filtroAdmin) {
				sql.append(" AND US.ID_USUARIO IS NULL");
			} else if (sqlUsuarios != null) {
				sql.append(" AND US.ID_USUARIO IN").append(sqlUsuarios).append(")");
			}
		}
	}

	/**
	 * Metodo que permite construir el filtro de busqueda por estados de los consecutivos
	 */
	public static void getFilterEstados(List<Integer> estados, StringBuilder sql) {

		// se verifica si hay estados seleccionados para el filtro
		if (estados != null && !estados.isEmpty()) {

			// el filtro es por el campo CONSECUTIVOS_id_cliente.ESTADO
			sql.append(" AND CON.ESTADO IN(");

			// se recorre todos los estados construyendo el filtro
			boolean isPrimero = true;
			for (Integer estado : estados) {
				if (isPrimero) {
					sql.append(estado);
					isPrimero = false;
				} else {
					sql.append(",").append(estado);
				}
			}
			sql.append(")");
		}
	}
}
