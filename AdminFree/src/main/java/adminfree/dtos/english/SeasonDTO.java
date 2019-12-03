package adminfree.dtos.english;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO que se utiliza para mappear los datos de las temporadas de una serie
 *
 * @author Carlos Andres Diaz
 *
 */
public class SeasonDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** identificador de la temporada */
	private Long id;

	/** Se utiliza para visualizarlo en pantalla, aunque no se guarda en BD */
	private String name;

	/** Son los capitulos de esta temporada */
	private List<ChapterDTO> chapters;

	/**
	 * Metodo que permite agregar un capitulo para esta temporada
	 */
	public void addChapter(ChapterDTO chapter) {
		if (this.chapters == null) {
			this.chapters = new ArrayList<>();
		}
		this.chapters.add(chapter);
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
	 * @return the chapters
	 */
	public List<ChapterDTO> getChapters() {
		return chapters;
	}

	/**
	 * @param chapters the chapters to set
	 */
	public void setChapters(List<ChapterDTO> chapters) {
		this.chapters = chapters;
	}
}
