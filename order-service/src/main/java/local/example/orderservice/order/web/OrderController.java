package local.example.orderservice.order.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import local.example.orderservice.order.domain.Order;
import local.example.orderservice.order.domain.OrderService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("orders")
@Slf4j
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping
	public Flux<Order> getAllOrders(@AuthenticationPrincipal Jwt jwt) {
		log.info("Fetching all orders");
		return orderService.getAllOrders(jwt.getSubject());
	}

	@PostMapping
	public Mono<Order> submitOrder(@RequestBody @Valid OrderRequest orderRequest) {
		log.info("Order for {} copies of the book with ISBN {}", orderRequest.quantity(), orderRequest.isbn());
		return orderService.submitOrder(orderRequest.isbn(), orderRequest.quantity());
	}

}
