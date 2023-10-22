package study.outfitoftheday.common.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Value;

public class OneWayPasswordEncoder implements PasswordEncoder {
	/*
	 * @Value
	 * 스프링에서 제공하는 Annotation으로 설정파일(application.properties or .yml)에서 정의한 값을 코드상으로 의존성 주입할 때 사용한다.
	 * 스프링의 BeanPostProcessor를 통해 이루어진다.
	 * */
	@Value("${security.encryption.salt}")
	private int salt;

	@Value("${security.encryption.algorithm}")
	private String ENCRYPTION_ALGORITHM;

	@Override
	public String encode(String plainPassword) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance(ENCRYPTION_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		messageDigest.update((plainPassword + salt).getBytes());

		StringBuilder stringBuilder = new StringBuilder();
		for (byte piece : messageDigest.digest()) {
			stringBuilder.append(String.format("%02x", piece));
		}
		return stringBuilder.toString();

	}

	@Override
	public boolean matches(String plainPassword, String encryptedPassword) {
		return encode(plainPassword).equals(encryptedPassword);
	}
}
