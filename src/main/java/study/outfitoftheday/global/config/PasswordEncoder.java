package study.outfitoftheday.global.config;

public interface PasswordEncoder {
	String encode(String plainPassword);

	boolean matches(String plainPassword, String encryptedPassword);

}
