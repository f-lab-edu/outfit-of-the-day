package study.outfitoftheday.core.domain.member.service;

public interface MemberService {

	/*
	 * sign-up
	 * */
	void signUp(String loginId, String nickname, String plainPassword);
}
