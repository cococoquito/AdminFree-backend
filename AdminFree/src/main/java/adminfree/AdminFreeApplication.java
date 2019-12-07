package adminfree;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

/**
 * Clase RAIZ donde se desplega la aplicacion
 *
 * @author Carlos Andres Diaz
 *
 */
@SpringBootApplication
public class AdminFreeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminFreeApplication.class, args);
	}

	/**
	 * Se utiliza para incrementar post size para los MULTIPART_FORM_DATA_VALUE
	 */
	@Bean
	public TomcatServletWebServerFactory containerFactory() {
		return new TomcatServletWebServerFactory() {
			protected void customizeConnector(Connector connector) {
				int maxSize = 50000000;
				super.customizeConnector(connector);
				connector.setMaxPostSize(maxSize);
				connector.setMaxSavePostSize(maxSize);
				if (connector.getProtocolHandler() instanceof AbstractHttp11Protocol) {
					((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(maxSize);
				}
			}
		};
	}
}
