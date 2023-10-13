package study.outfitoftheday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/*
 * PasswordEncoder를 Spring Security를 활용해서 구현하다 보니, 서버를 켰을 때 로그인 화면으로 이동됐습니다.
 * 로그인 화면 자동 이동 방지를 위해서 "exclude = SecurityAutoConfiguration.class"를 추가했습니다.
 *
 * */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class OutfitOfTheDayApplication {

	public static void main(String[] args) {
		SpringApplication.run(OutfitOfTheDayApplication.class, args);
	}

}
