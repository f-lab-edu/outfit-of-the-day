package study.outfitoftheday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/*
 * @SpringBootApplication
 * Spring Boot Application의 주요 구송 요소를 선언하는 annotation이다.
 *
 * 아래의 annotation들을 포함한다.
 * 1. @SpringBootConfiguration: Spring의 Bean 구성 클래스임을 지정한다.
 * 2. @EnableAutoConfiguration: Spring Boot의 자동 구성 기능을 활성화한다. 클래스 패스와 설정에 따라 애플리케이션을 설정하고 초기화하는 데 필요한 빈들을 자동으로 추가한다.
 * 3. @ComponentScan: Spring이 @Component, @Service, @Repository 등을 찾아서 등록하는 annotation이다.
 * */

/*
 * 로그인 화면 자동 이동 방지를 위해서 "exclude = SecurityAutoConfiguration.class"를 추가
 * */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class OutfitOfTheDayApplication {

	public static void main(String[] args) {

		SpringApplication.run(OutfitOfTheDayApplication.class, args);
	}

}
