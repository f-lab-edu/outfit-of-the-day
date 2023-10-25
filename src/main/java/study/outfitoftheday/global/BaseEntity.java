package study.outfitoftheday.global;

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
 * 공통 맵핑 정보가 필요할 때 사용하며 자식 클래스는 부모 클래스(BaseEntity)의 속성만 상속받아서 사용하게 된다.
 *
 * @EntityListeners
 * JPA에서 Entity의 생명주기 이벤트를 수신하는 리스너 클래스를 지정하는데 사용된다.
 * */
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {

	/*
	 * @CreatedDate
	 * Spring Data JPA에서 제공하는 어노테이션 중 하나
	 * 엔터티 객체가 생성될 때 해당 필드에 현재 일시를 자동으로 할당하도록 지정한다.
	 *
	 * @Column
	 * JPA에서 Entity 클래스의 필드와 DB Column을 매핑하는데 사용된다.
	 * */
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	/*
	 * @LastModifiedDate
	 * Spring Data JPA에서 제공하는 어노테이션 중 하나
	 * 엔터티 객체가 수정될 때 해당 필드에 현재 일시를 자동으로 할당하도록 지정한다.
	 * */
	@LastModifiedDate
	private LocalDateTime updatedAt;

	/*
	 * @CreatedBy
	 * Spring Data JPA에서 제공하는 어노테이션 중 하나
	 * 엔터티 객체가 생성될 때 해당 필드에 객체를 생성한 사용자의 정보를 자동으로 할당하도록 지정한다.
	 * */
	@CreatedBy
	@Column(updatable = false)
	private Long createdBy = 0L;

	/*
	 * @LastModifiedBy
	 * Spring Data JPA에서 제공하는 어노테이션 중 하나
	 * 엔터티 객체가 수정될 때 해당 필드에 객체를 수정한 사용자의 정보를 자동으로 할당하도록 지정한다.
	 * */
	@LastModifiedBy
	private Long updatedBy = 0L;
}
