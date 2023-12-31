package local.example.orderservice.order.web;
 
import static org.mockito.BDDMockito.given;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import local.example.orderservice.order.domain.Order;
import local.example.orderservice.order.domain.OrderService;
import local.example.orderservice.order.domain.OrderStatus;
import reactor.core.publisher.Mono;

@WebFluxTest(OrderController.class)
class OrderControllerWebFluxTests {

	@Autowired
	private WebTestClient webClient;

	@MockBean
	private OrderService orderService;

	@Test
	void whenBookNotAvailableThenRejectOrder() {
		var orderRequest = new OrderRequest("1234567890", 3);
		var expectedOrder = OrderService.buildRejectedOrder(orderRequest.isbn(), orderRequest.quantity());
		given(orderService.submitOrder(orderRequest.isbn(), orderRequest.quantity()))
				.willReturn(Mono.just(expectedOrder));

		webClient
				.post()
				.uri("/orders")
				.bodyValue(orderRequest)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Order.class).value(actualOrder -> {
					Assertions.assertThat(actualOrder).isNotNull();
					Assertions.assertThat(actualOrder.status()).isEqualTo(OrderStatus.REJECTED);
				});

	}

}
