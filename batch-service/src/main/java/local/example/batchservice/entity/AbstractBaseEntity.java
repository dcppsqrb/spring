package local.example.batchservice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class AbstractBaseEntity implements Serializable {

	@Transient
	static final long serialVersionUID = -1820301298081607447L;

	@CreationTimestamp 
	private LocalDateTime createdDate;

	@UpdateTimestamp 
	private LocalDateTime lastModifiedDate;

	@LastModifiedBy
	private String lastModifiedBy;

	@CreatedBy
	private String createdBy;
	
	@Version
	private Integer version;
}
