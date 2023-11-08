package study.outfitoftheday.global.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * @Target
 * 해당 Annootation 적용 할 대상을 지정하는데 사용한다.
 * Method, Parameter 등에 적용 대상을 설정할 수 있다.
 * 현재는 Parameter를 적용 대상으로 지정했다.
 *
 * @Retention
 * 해당 Annotation은 어떤 시점까지 유효할 것인지를 지정하는데 사용된다.
 * - RententionPolicy.RUNTIME: 런타임 시에 유효하다. 리플렉션을 통해 해당 Annotation을 읽을 수 있다.
 * - RetentionPolicy.SOURCE: 컴파일 되기 전까지 유효하다. 즉, 컴파일된 클래스 파일에는 존재하지 않는다. 소스 코드 분석이나 컴파일 시 경고 등에 사용된다.
 * - RetentionPolicy.CLASS: 클래스 파일에는 Annotation 정보가 포함되지만 런타임 시에는 사용할 수 없다.
 *
 * @Documented
 * 해당 Annotation을 JavaDoc에 포함시킬 때 사용한다.
 * */
@Target(value = {ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface LoginMember {

}
