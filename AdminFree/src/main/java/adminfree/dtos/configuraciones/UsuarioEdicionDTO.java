package adminfree.dtos.configuraciones;

import java.io.Serializable;

import adminfree.dtos.seguridad.UsuarioDTO;

/**
 * DTO que se utiliza para la edicion del Usuario
 * 
 * @author Carlos Andres Diaz
 *
 */
public class UsuarioEdicionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Contiene los datos del usuario a modificar */
	private UsuarioDTO usuario;

	/** Indica si los datos basicos del usuario se debe editar */
	private boolean datosBasicosEditar;

	/** Indica si los modulos asignados fueron modificados */
	private boolean modulosEditar;

	/**
	 * Metodo que permite obtener el valor del atributo usuario
	 */
	public UsuarioDTO getUsuario() {
		return usuario;
	}

	/**
	 * Metodo que permite obtener el valor del atributo datosBasicosEditar
	 */
	public boolean isDatosBasicosEditar() {
		return datosBasicosEditar;
	}

	/**
	 * Metodo que permite obtener el valor del atributo modulosEditar
	 */
	public boolean isModulosEditar() {
		return modulosEditar;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param usuario
	 */
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * datosBasicosEditar
	 */
	public void setDatosBasicosEditar(boolean datosBasicosEditar) {
		this.datosBasicosEditar = datosBasicosEditar;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * modulosEditar
	 */
	public void setModulosEditar(boolean modulosEditar) {
		this.modulosEditar = modulosEditar;
	}
}
