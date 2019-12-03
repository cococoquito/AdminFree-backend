package adminfree.dtos.english;

import java.io.Serializable;

/**
 * DTO que se utiliza para mappear los datos de las sentencias
 *
 * @author Carlos Andres Diaz
 *
 */
public class SentenceDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** identificador de la sentencia */
	private Long id;

	/** Es la sentencia en español */
	private String spanish;

	/** Es la sentencia en english */
	private String english;

	/** Es el audio de la sentencia */
	private byte[] audio;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the spanish
	 */
	public String getSpanish() {
		return spanish;
	}

	/**
	 * @param spanish the spanish to set
	 */
	public void setSpanish(String spanish) {
		this.spanish = spanish;
	}

	/**
	 * @return the english
	 */
	public String getEnglish() {
		return english;
	}

	/**
	 * @param english the english to set
	 */
	public void setEnglish(String english) {
		this.english = english;
	}

	/**
	 * @return the audio
	 */
	public byte[] getAudio() {
		return audio;
	}

	/**
	 * @param audio the audio to set
	 */
	public void setAudio(byte[] audio) {
		this.audio = audio;
	}
}
