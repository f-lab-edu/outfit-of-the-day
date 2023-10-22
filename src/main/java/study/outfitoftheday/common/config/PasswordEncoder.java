package study.outfitoftheday.common.config;

public interface PasswordEncoder {
	String encode(String plainPassword);

	boolean matches(String plainPassword, String encryptedPassword);

}
