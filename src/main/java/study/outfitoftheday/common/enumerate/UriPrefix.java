package study.outfitoftheday.common.enumerate;

import lombok.Getter;

@Getter
public enum UriPrefix {
	MEMBER_URI_PREFIX("/api/members"),

	AUTH_URI_PREFIX("/api/auth");

	private final String prefix;

	UriPrefix(String prefix) {
		this.prefix = prefix;
	}
}
