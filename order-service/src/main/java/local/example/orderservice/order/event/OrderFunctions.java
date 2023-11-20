package local.example.orderservice.order.event;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import local.example.orderservice.order.domain.OrderService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Configuration
@Slf4j
public class OrderFunctions {
	@Bean
	Consumer<Flux<OrderDispatchedMessage>> dispatchOrder(OrderService orderService) {
		return flux -> orderService.consumeOrderDispatchedEvent(flux)
				.doOnNext(order -> log.info("The order with id {} is dispatched", order.id()))
				.subscribe();
	}

}
