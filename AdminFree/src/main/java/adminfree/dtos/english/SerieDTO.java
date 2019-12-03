package adminfree.dtos.english;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO que se utiliza para mappear los datos de las series
 *
 * @author Carlos Andres Diaz
 *
 */
public class SerieDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** identificador de la serie */
	private Long id;

	/** Es el nombre de la serie */
	private String name;

	/** Es la URL de esta serie */
	private String url;

	/** Es la imagen de esta serie */
	private byte[] img;

	/** son las temporadas de esta serie */
	private List<SeasonDTO> seasons;

	/**
	 * Metodo que permite agregar una temporada para esta serie
	 * @param season, DTO con los datos de la temporada
	 */
	public void addSeason(SeasonDTO season) {
		if (this.seasons == null) {
			this.seasons = new ArrayList<>();
		}
		this.seasons.add(season);
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
	 * @return the img
	 */
	public byte[] getImg() {
		return img;
	}

	/**
	 * @param img the img to set
	 */
	public void setImg(byte[] img) {
		this.img = img;
	}

	/**
	 * @return the seasons
	 */
	public List<SeasonDTO> getSeasons() {
		return seasons;
	}

	/**
	 * @param seasons the seasons to set
	 */
	public void setSeasons(List<SeasonDTO> seasons) {
		this.seasons = seasons;
	}
}
