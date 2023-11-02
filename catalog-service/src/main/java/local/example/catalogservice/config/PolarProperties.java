package local.example.catalogservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "polar")
@Getter
@Setter
public class PolarProperties {

	/**
	 * A message to welcome users.
	 */
	private String greeting;
}
