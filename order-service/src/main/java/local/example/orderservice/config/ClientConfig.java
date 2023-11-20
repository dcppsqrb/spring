package local.example.orderservice.config;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;


@Configuration
@EnableCaching
@Slf4j
public class ClientConfig {

	@Bean
	WebClient webClient(ClientProperties clientProperties, WebClient.Builder webClientBuilder) {

		HttpClient httpClient = HttpClient.create()
				.followRedirect(true).compress(true).secure()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)				
				.responseTimeout(Duration.ofMillis(10000))
				.doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
						.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

		return WebClient.builder()
				.clientConnector(new ReactorClientHttpConnector(httpClient)) 
				.exchangeStrategies(ExchangeStrategies.builder().codecs(configurer ->
				configurer.defaultCodecs().maxInMemorySize(30 * 1024 * 1024)).build())
				.filter(logRequest()).filter(logResponse())
				.baseUrl(clientProperties.catalogServiceUri().toString()).build();
	}
	
	private static ExchangeFilterFunction logResponse() {                    
	    return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
	    	if (log.isDebugEnabled()) {
		        log.debug("Response: " + clientResponse.statusCode());
		        clientResponse.headers().asHttpHeaders()
		                .forEach((name, values) -> values.forEach(value ->
		                	log.debug(name + " " + value)));
	    	}
	        return Mono.just(clientResponse);
	    });
	}
	
	private static ExchangeFilterFunction logRequest() {                     
	    return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
	    	if (log.isDebugEnabled()) {
	    		log.debug("Request: " + clientRequest.method() + " " + clientRequest.url());
	        	clientRequest.headers()
	            	.forEach((name, values) -> values.forEach(value ->
	            		log.debug(name + " " + value)));
	    	}
	        return Mono.just(clientRequest);
	    });
	}
}
