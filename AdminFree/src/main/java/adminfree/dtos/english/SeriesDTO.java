package adminfree.dtos.english;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

/**
 * DTO que se utiliza para mappear los datos de las series
 *
 * @author Carlos Andres Diaz
 *
 */
public class SeriesDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** identificador de la serie */
	private Long id;

	/** Es el nombre de la serie */
	private String name;

	/** Es la URL de esta serie */
	private String url;

	/** Es la imagen asociada a esta serie */
	private MultipartFile img;

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
	public MultipartFile getImg() {
		return img;
	}

	/**
	 * @param img the img to set
	 */
	public void setImg(MultipartFile img) {
		this.img = img;
	}
}