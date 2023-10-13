package study.outfitoftheday.core.domain.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import study.outfitoftheday.core.domain.member.entity.Member;
import study.outfitoftheday.core.domain.member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberServiceImpl implements MemberService {
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;

	@Override
	@Transactional
	public void signUp(
		String loginId,
		String nickname,
		String plainPassword
	) {

		System.out.println("loginId!!= " + loginId);
		Member foundMember = memberRepository.findByMemberByLoginIdOrNickname(loginId, nickname).orElse(null);

		if (foundMember != null) {
			throw new IllegalStateException("is exsist member");
		}

		String encryptedPassword = passwordEncoder.encode(plainPassword);

		log.info("encryptedPassword: {}", encryptedPassword);
		Member createdMember = Member.builder()
			.loginId(loginId)
			.nickname(nickname)
			.password(encryptedPassword)
			.build();

		log.info("encryptedPassword = {} / length = {}", encryptedPassword, encryptedPassword.length());

		memberRepository.save(createdMember);

	}
}
