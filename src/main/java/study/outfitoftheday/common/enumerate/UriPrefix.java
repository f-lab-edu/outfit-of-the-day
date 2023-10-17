package study.outfitoftheday.common.enumerate;

import lombok.Getter;

/*
 * @Getter
 *
 * lombok에서 지원해주는 Annotation으로 클래스, 혹은 필드에 적용하면 컴파일 시, 해당 필드의 getX() method를 생성시켜준다.
 * UriPrefix.class 파일을 확인하면 아래와 같이 생성되었음을 알 수 있다.
 * public String getPrefix() {
        return this.prefix;
    }
 * */
@Getter
public enum UriPrefix {
	MEMBER_URI_PREFIX("/api/members"),

	AUTH_URI_PREFIX("/api/auth");

	private final String prefix;

	UriPrefix(String prefix) {
		this.prefix = prefix;
	}
}
