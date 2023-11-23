package local.example.catalogservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import local.example.catalogservice.config.PolarProperties;

@RestController
public class HomeController {

	private final PolarProperties polarProperties;

	public HomeController(PolarProperties polarProperties) {
		this.polarProperties = polarProperties;
	}

	@GetMapping("/")
	public String getGreeting() {
		return polarProperties.getGreeting();
	}

}
