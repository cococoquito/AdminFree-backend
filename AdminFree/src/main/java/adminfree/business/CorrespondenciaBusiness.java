package adminfree.business;

import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adminfree.constants.CommonConstant;
import adminfree.constants.SQLConfiguraciones;
import adminfree.constants.SQLCorrespondencia;
import adminfree.dtos.configuraciones.ItemDTO;
import adminfree.dtos.correspondencia.CampoEntradaDetalleDTO;
import adminfree.dtos.correspondencia.CampoEntradaValueDTO;
import adminfree.dtos.correspondencia.InitSolicitarConsecutivoDTO;
import adminfree.dtos.correspondencia.NomenclaturaDetalleDTO;
import adminfree.dtos.correspondencia.SolicitudConsecutivoDTO;
import adminfree.dtos.correspondencia.SolicitudConsecutivoResponseDTO;
import adminfree.dtos.transversal.MessageResponseDTO;
import adminfree.enums.Numero;
import adminfree.enums.TipoCampo;
import adminfree.mappers.MapperConfiguraciones;
import adminfree.mappers.MapperCorrespondencia;
import adminfree.mappers.MapperTransversal;
import adminfree.persistence.CommonDAO;
import adminfree.persistence.ValueSQL;

/**
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
				SQLCorrespondencia.GET_DTL_NOMENCLATURA,
				MapperCorrespondencia.get(MapperCorrespondencia.GET_DTL_NOMENCLATURA), 
				ValueSQL.get(idNomenclatura, Types.BIGINT));
	}

	/**
	 * Metodo que permite obtener los campos de la nomenclatura
	 * 
	 * @param idNomenclatura, identificador de la nomenclatura
	 * @return DTO con los campos de la nomenclatura
	 */
	@SuppressWarnings("unchecked")
	public List<CampoEntradaDetalleDTO> getCamposNomenclatura(Long idNomenclatura, Connection connection) throws Exception {

		// se obtiene los campos asociados a la nomenclatura
		List<CampoEntradaDetalleDTO> resultado = (List<CampoEntradaDetalleDTO>)
				find(connection,
						SQLCorrespondencia.GET_DTL_NOMENCLATURA_CAMPOS,
						MapperCorrespondencia.get(MapperCorrespondencia.GET_DTL_NOMENCLATURA_CAMPOS),
						ValueSQL.get(idNomenclatura, Types.BIGINT));

		// se recorre todos los campos en busqueda de los select-items
		if (resultado != null && !resultado.isEmpty()) {
			for (CampoEntradaDetalleDTO campo : resultado) {

				// se consulta los items para esta lista desplegable
				if (TipoCampo.LISTA_DESPLEGABLE.id.equals(campo.getTipoCampo())) {
					campo.setItems((List<ItemDTO>)
							find(connection,
									SQLConfiguraciones.GET_ITEMS,
									MapperConfiguraciones.get(MapperConfiguraciones.GET_ITEMS),
									ValueSQL.get(campo.getId(), Types.BIGINT)));
				}
			}
		}
		return resultado;
	}

	/**
	 * Metodo que permite obtener los datos iniciales para las
	 * solicitudes de consecutivos de correspondencia
	 *
	 * @param idCliente, identificador del cliente autenticado
	 * @return DTO con los datos iniciales
	 */
	public InitSolicitarConsecutivoDTO getInitSolicitarConsecutivo(Long idCliente, Connection connection) throws Exception {

		// se configura el DTO de retorno
		InitSolicitarConsecutivoDTO init = new InitSolicitarConsecutivoDTO();
		init.setFechaActual(Calendar.getInstance().getTime());

		// se configura las nomenclaturas de acuerdo al cliente
		init.setNomenclaturas(new ConfiguracionesBusiness().getNomenclaturas(idCliente, connection));
		return init;
	}

	/**
	 * Metodo que permite validar los campos de ingreso de informacion para el proceso de 
	 * solicitar o editar un consecutivo de correspondencia
	 *
	 * @param solicitud, DTO con los datos de la solicitud
	 * @return Lista de mensajes con los errores encontrados solo si lo hay
	 */
	public List<MessageResponseDTO> validarCamposIngresoInformacion(
			SolicitudConsecutivoDTO solicitud,
			Connection connection) throws Exception {
		return null;
	}

	/**
	 * Metodo que permite soportar el proceso de negocio de solicitar
	 * un consecutivo de correspondencia para una nomenclatura
	 *
	 * @param solicitud, DTO que contiene los datos de la solicitud
	 * @return DTO con los datos de la respuesta
	 */
	@SuppressWarnings("unchecked")
	public SolicitudConsecutivoResponseDTO solicitarConsecutivo(
			SolicitudConsecutivoDTO solicitud,
			Connection connection) throws Exception {
		try {
			// para este proceso debe estar bajo transaccionalidad
			connection.setAutoCommit(false);

			// identificadores que se utiliza para varios procesos
			Long idNomenclatura = solicitud.getIdNomenclatura();
			Long idCliente = solicitud.getIdCliente();
			String idCliente_ = idCliente.toString();
			String idNomenclatura_ = idNomenclatura.toString();

			// se procede a consultar la secuencia actual de la nomenclatura
			List<Integer> respuesta = (List<Integer>) find(connection,
					SQLCorrespondencia.GET_SECUENCIA_NOMENCLATURA,
					MapperCorrespondencia.get(MapperCorrespondencia.GET_SECUENCIA_NOMENCLATURA),
					ValueSQL.get(idNomenclatura, Types.BIGINT));

			// se configura el nro inicial y la secuencia actual de la nomenclatura consultada
			Integer nroInicial = respuesta.get(Numero.ZERO.value);
			Integer nroSecuencia = respuesta.get(Numero.UNO.value);

			// se establece el nuevo consecutivo para la nomenclatura
			if (nroSecuencia != null && nroSecuencia > Numero.ZERO.value) {
				nroSecuencia = nroSecuencia + Numero.UNO.value;
			} else {
				nroSecuencia = nroInicial;
			}

			// se configura el formato de la nueva secuencia
			StringBuilder secuenciaFormatoB = new StringBuilder();
			secuenciaFormatoB.append(CommonConstant.RANGO);
			secuenciaFormatoB.delete(Numero.ZERO.value, nroSecuencia.toString().length());
			secuenciaFormatoB.append(nroSecuencia);
			String secuenciaFormato = secuenciaFormatoB.toString();

			// se realiza el insert de la tabla padre CONSECUTIVOS_ID_CLIENTE
			insertUpdate(connection,
					SQLCorrespondencia.getInsertConsecutivo(idCliente_),
					ValueSQL.get(idNomenclatura, Types.BIGINT),
					ValueSQL.get(secuenciaFormato, Types.VARCHAR),
					ValueSQL.get(solicitud.getIdUsuario(), Types.BIGINT));

			// se obtiene el identificador del consecutivo creado
			Long idConsecutivo = (Long) find(connection,
					CommonConstant.LAST_INSERT_ID,
					MapperTransversal.get(MapperTransversal.GET_ID));

			// si hay valores para este consecutivo se procede a insertarlo
			List<CampoEntradaValueDTO> valores = solicitud.getValores();
			if (valores != null && !valores.isEmpty()) {

				// se construye la lista de injections para la insercion
				List<List<ValueSQL>> injections = new ArrayList<>();

				// este valor es general para todos los valores
				ValueSQL valueIdConsecutivo = ValueSQL.get(idConsecutivo, Types.BIGINT);

				// se recorre cada valor para construir el INSERT
				List<ValueSQL> values;
				for (CampoEntradaValueDTO value : valores) {
					values = new ArrayList<>();

					// identificador de la tabla padre
					values.add(valueIdConsecutivo);

					// identificador del id del la tabla NOMENCLATURAS_CAMPOS_ENTRADA
					values.add(ValueSQL.get(value.getIdCampoNomenclatura(), Types.BIGINT));

					// se configura el valor del campo
					if (value.getValue() != null) {
						values.add(ValueSQL.get(value.getValue().toString(), Types.VARCHAR));
					} else {
						values.add(ValueSQL.get(null, Types.VARCHAR));
					}

					// se agrega los valores para este INSERT
					injections.add(values);
				}

				// se inserta los valores del consecutivo
				batchConInjection(connection, SQLCorrespondencia.getInsertConsecutivoValues(idCliente_), injections);
			}

			// Lista para la ejecucion de los dmls por batch sin injection
			List<String> dmls = new ArrayList<>();

			// SQL para actualizar la secuencia para la nomenclatura seleccionada
			dmls.add(SQLCorrespondencia.getUpdateNomenclaturaSecuencia(nroSecuencia.toString(), idNomenclatura_));

			// SQL para actualizar la bandera que indica que campos ya tienen asociado un consecutivo
			dmls.add(SQLCorrespondencia.getUpdateCamposTieneConsecutivo(idNomenclatura_));

			// se ejecuta el batch para estos dos actualizaciones
			batchSinInjection(connection, dmls);

			// se debe confirmar los cambios en BD
			connection.commit();

			// se construye la respuesta a retornar
			SolicitudConsecutivoResponseDTO response = new SolicitudConsecutivoResponseDTO();
			response.setConsecutivo(secuenciaFormato);
			response.setIdConsecutivo(idConsecutivo);
			return response;
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
	}
}
