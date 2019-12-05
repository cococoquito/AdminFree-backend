package adminfree.dtos.english;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO que se utiliza para mappear los datos de los capitulos
 *
 * @author Carlos Andres Diaz
 *
 */
public class ChapterDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** identificador del capitulo */
	private Long id;

	/** identificador de la temporada */
	private Long idSeason;

	/** identificador de la serie */
	private Long idSerie;

	/** Es el nombre del capitulo */
	private String name;

	/** Es la URL de este capitulo */
	private String url;

	/** Son las sentencias que tiene este capitulo */
	private List<SentenceDTO> sentences;

	/**
	 * Metodo que permite agregar una sentencia para este capitulo
	 */
	public void addSentence(SentenceDTO sentence) {
		if (this.sentences == null) {
			this.sentences = new ArrayList<>();
		}
		this.sentences.add(sentence);
	}

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the sentences
	 */
	public List<SentenceDTO> getSentences() {
		return sentences;
	}

	/**
	 * @param sentences the sentences to set
	 */
	public void setSentences(List<SentenceDTO> sentences) {
		this.sentences = sentences;
	}

	/**
	 * @return the idSeason
	 */
	public Long getIdSeason() {
		return idSeason;
	}

	/**
	 * @param idSeason the idSeason to set
	 */
	public void setIdSeason(Long idSeason) {
		this.idSeason = idSeason;
	}

	/**
	 * @return the idSerie
	 */
	public Long getIdSerie() {
		return idSerie;
	}

	/**
	 * @param idSerie the idSerie to set
	 */
	public void setIdSerie(Long idSerie) {
		this.idSerie = idSerie;
	}
}
