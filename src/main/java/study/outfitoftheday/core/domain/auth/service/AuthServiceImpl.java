package study.outfitoftheday.core.domain.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import study.outfitoftheday.core.domain.auth.exception.NotFoundLoginMemberException;
import study.outfitoftheday.core.domain.member.entity.Member;
import study.outfitoftheday.core.domain.member.exception.MismatchPasswordInLoginException;
import study.outfitoftheday.core.domain.member.exception.NotFoundMemberException;
import study.outfitoftheday.core.domain.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private static final String MEMBER_ID = "MEMBER_ID";
	private final HttpSession httpSession;
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;

	public void login(String loginId, String plainPassword) {
		Member foundMember = memberRepository.findByLoginId(loginId).orElseThrow(NotFoundMemberException::new);

		if (!passwordEncoder.matches(plainPassword, foundMember.getPassword())) {
			throw new MismatchPasswordInLoginException();
		}
		httpSession.setAttribute(MEMBER_ID, foundMember.getId());

	}

	@Override
	public void logout() {
		Long memberId = (Long)httpSession.getAttribute(MEMBER_ID);
		memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
		httpSession.removeAttribute(MEMBER_ID);

	}

	@Override
	public Member findLoginMemberInSession() {
		Long memberId = (Long)httpSession.getAttribute(MEMBER_ID);
		return memberRepository.findById(memberId).orElseThrow(NotFoundLoginMemberException::new);
	}

	@Override
	public Long findMemberIdInSession() {
		return (Long)httpSession.getAttribute(MEMBER_ID);
	}
}

