package study.outfitoftheday.core.domain.member.service;

import study.outfitoftheday.core.domain.member.entity.Member;

public interface MemberService {

	void signUp(String loginId, String nickname, String plainPassword, String passwordConfirm);

	void withdrawMember(Member member);

	boolean isDuplicatedMemberByLoginId(String loginId);

	boolean isDuplicatedMemberByNickname(String nickname);

	boolean isDeletedMemberByLoginId(String loginId);

	Member findMemberByLoginId(String loginId);
}

