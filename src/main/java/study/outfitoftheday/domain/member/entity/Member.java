package study.outfitoftheday.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import study.outfitoftheday.global.BaseEntity;

/*
 * @Entity
 * JPA에서 제공하는 annotation
 * @Entity annotation을 클래스에 적용함으로써 해당 클래스를 JPA Entity로 지정한다.
 * (JPA Entity는 데이터베이스의 테이블과 매핑되는 자바 객체를 나타낸다.)
 *
 * @NoArgsConstructor
 * lombok에서 제공해주는 annotation
 * compile시 해당 클래스에 기본 생성자 생성시켜줌
 * access field를 통해 어떤 접근제어자로 생성할지 설정 가능.
 *
 * @ToString
 * lombok에서 제공하는 annotation
 * complile시 해당 클래스에 toString method를 생성시켜준다.
 * of field를 통해서 어떤 속성들을 toString method에 추가할지 지정가능하다.
 * */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "loginId", "nickname", "profileMessage", "profileImageUrl"})
public class Member extends BaseEntity {

	/*
	 * @Id
	 * JPA에게 해당 column이 Primary Key라는 정보를 전달한다.
	 *
	 * @GeneratedValue
	 * Primary Key 생성 전략을 지정하는데 사용된다.
	 * GenerationType.IDENTITY, GenerationType.SEQUENCE, GenerationType.TABLE, GenerationType.AUTO 등이 있다.
	 * */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	private String loginId;

	private String password;

	private String nickname;

	private String profileMessage;

	private String profileImageUrl;

	private Boolean isDeleted;

	/*
	 * @Builder
	 * lombok에서 제공해주는 annotation
	 * compile시 MemberBuilder Class를 생성해준다.
	 * */
	@Builder
	public Member(String loginId, String password, String nickname) {
		this.loginId = loginId;
		this.password = password;
		this.nickname = nickname;
		this.isDeleted = false;
		this.initializeWhenSignUp(nickname);
	}

	public void withdrawMember() {
		this.isDeleted = true;
	}
}
