package local.example.batchservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Setter
@Getter
public class Order extends AbstractBaseEntity {

	@Transient
	private static final long serialVersionUID = 1128502579950958917L;

	@Id
	@SequenceGenerator(name = "orders_seq_generator", sequenceName = "orders_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq_generator")
	private Long id;

	private String orderRef;

	private BigDecimal amount;

	private LocalDateTime orderDate;

	private String note;

	@Transient
	private Date tempOrderDate;

}
