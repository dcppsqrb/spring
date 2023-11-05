package local.example.batchservice.processor;

import java.time.Instant;
import java.time.ZoneId;

import org.springframework.batch.item.ItemProcessor;

import local.example.batchservice.entity.Order;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderProcessor implements ItemProcessor<Order, Order> {

    @Override
    public Order process(final Order order) throws Exception {
    	log.info(String.format("Process order: [%s]", order.getOrderRef()));
    	log.info(order.toString());
    	return Order.builder()
        	.orderRef(order.getOrderRef())
        	.amount(order.getAmount())
        	.note(order.getNote())
        	.orderDate(Instant.ofEpochMilli(order.getTempOrderDate().getTime())
                .atZone(ZoneId.systemDefault()).toLocalDateTime())
				.build();
	}
}