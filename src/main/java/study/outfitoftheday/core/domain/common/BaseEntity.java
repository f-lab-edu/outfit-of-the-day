package study.outfitoftheday.core.domain.common;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

/*
 * @MappedSuperclass
 * 자식 클래스에게 매핑 정보만 제공해줄 경우 사용.
 * */
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@CreatedBy
	private Long createdBy;

	@LastModifiedBy
	private Long updatedBy;
}
