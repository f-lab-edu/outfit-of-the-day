package study.outfitoftheday.core.domain.auth.service;

import study.outfitoftheday.core.domain.member.entity.Member;

public interface AuthService {
	void login(String loginId, String plainPassword);

	void logout();

	Member findLoginMemberInSession();

	Long findMemberIdInSession();
}
