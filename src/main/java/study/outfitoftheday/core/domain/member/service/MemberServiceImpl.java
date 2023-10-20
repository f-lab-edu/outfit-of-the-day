package study.outfitoftheday.core.domain.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import study.outfitoftheday.core.domain.auth.service.AuthService;
import study.outfitoftheday.core.domain.member.entity.Member;
import study.outfitoftheday.core.domain.member.exception.DuplicatedMemberException;
import study.outfitoftheday.core.domain.member.exception.MismatchPasswordInSignUpException;
import study.outfitoftheday.core.domain.member.exception.NotFoundMemberException;
import study.outfitoftheday.core.domain.member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService {
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;
	private final AuthService authService;
	private final EntityManager em;

	@Override
	public void signUp(
		String loginId,
		String nickname,
		String plainPassword,
		String passwordConfirm
	) {

		if (!plainPassword.equals(passwordConfirm)) {
			throw new MismatchPasswordInSignUpException();
		}

		if (memberRepository.findByLoginIdOrNicknameAndIsDeletedIsFalse(loginId, nickname).isPresent()) {
			throw new DuplicatedMemberException();
		}

		String encryptedPassword = passwordEncoder.encode(plainPassword);
		Member createdMember = Member.builder()
			.loginId(loginId)
			.nickname(nickname)
			.password(encryptedPassword)
			.build();

		memberRepository.save(createdMember);
	}

	@Override
	public boolean isDuplicatedMemberByLoginId(String loginId) {
		return memberRepository.findByLoginIdAndIsDeletedIsFalse(loginId).isPresent();
	}

	@Override
	public boolean isDuplicatedMemberByNickname(String nickname) {
		return memberRepository.findByNicknameAndIsDeletedIsFalse(nickname).isPresent();
	}

	@Override
	public void withdrawMember(Member member) {
		member.withdrawMember();

		em.flush();
		em.clear();

		authService.logout();
	}

	@Override
	public boolean isDeletedMemberByLoginId(String loginId) {
		return memberRepository.findByLoginIdAndIsDeletedIsTrue(loginId).isPresent();
	}

	@Override
	public Member findMemberByLoginId(String loginId) {
		return memberRepository.findByLoginIdAndIsDeletedIsFalse(loginId).orElseThrow(NotFoundMemberException::new);
	}
}
