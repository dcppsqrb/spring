package local.example.batchservice.component;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

import local.example.batchservice.entity.Order;

@Component
public class OrderFieldMapper implements FieldSetMapper<Order> {

    @Override
    public Order mapFieldSet(FieldSet fieldSet) {
    	return Order.builder()
        	.orderRef(fieldSet.readString("order_ref"))
        	.amount(fieldSet.readBigDecimal("amount"))
        	.note(fieldSet.readString("note"))
        	.tempOrderDate(fieldSet.readDate("order_date"))
        	.build();
        
    }
}