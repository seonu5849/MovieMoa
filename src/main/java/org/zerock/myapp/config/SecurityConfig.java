package org.zerock.myapp.config;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.zerock.myapp.service.MemberUserDetailsService;

import java.util.Objects;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Log4j2
@NoArgsConstructor

@Configuration // 스프링 구성 클래스임을 나타내는 어노테이션
@EnableWebSecurity // 스프링 시큐리티 활성화
public class SecurityConfig {

    @Setter(onMethod_ = @Autowired) // 자동 주입 설정
    private MemberUserDetailsService memberUserDetailsService;

    // SecurityFilterChain을 Bean으로 등록
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        log.trace("securityFilterChain({}) invoked", http);

        Objects.requireNonNull(this.memberUserDetailsService);
        http.userDetailsService(this.memberUserDetailsService);

        /*
        // HTTP 요청에 대한 보안 권한 설정
        http.authorizeHttpRequests(
                (customizer) -> customizer
                        // 특정 경로에 대한 접근을 모두 허용
                        .requestMatchers(antMatcher("/"), antMatcher("/main"),
                                antMatcher("/registration"), antMatcher("/movie/movies"))
                        .permitAll()
                        // "/member" 경로에 대해서는 인증된 사용자만 접근 가능
                        .requestMatchers(antMatcher("/member")).authenticated()
                        // "/admin" 경로에 대해서는 "ADMIN" 역할을 가진 사용자만 접근 가능
                        .requestMatchers(antMatcher("/admin")).hasRole("ADMIN")
                        // 그 외의 모든 요청에 대해서는 접근 허용
                        .anyRequest().permitAll()
        ); //authorizeHttpRequests
        */
        /*
        // HTTP 요청에 대한 보안 권한 설정
        http.authorizeHttpRequests(request -> request
                        // 특정 경로에 대한 접근을 모두 허용
                        .requestMatchers("/","/main","/registration", "/movie/movies", "/logout").permitAll()
                )
                .formLogin(login -> login
                        // 커스텀 로그인 페이지 지정
                        .loginPage("/login")
                        // 로그인 처리를 받을 URL
                        .loginProcessingUrl("/login-process")
                        // 로그인 시 제출할 사용자 이름 파라미터 지정
                        .usernameParameter("email")
                        // 로그인 시 제출할 비밀번호 파라미터 지정
                        .passwordParameter("passwd")
                        // 로그인 성공 시 이동할 기본 URL
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                // 로그아웃 설정
                .logout(withDefaults());
                */



        // HTTP 요청에 대한 인증 및 권한 부여 설정
        http.authorizeHttpRequests(
                (customizer) ->
                        customizer.
                                // 다음 경로들은 누구나 접근할 수 있음
                                 requestMatchers(antMatcher("/"), antMatcher("/main"), antMatcher("/registration"), antMatcher("/movie/movies"), antMatcher("/logout")).permitAll().
                                // "/mypage/**" 경로는 인증된 사용자만 접근할 수 있음
                                 requestMatchers(antMatcher("/mypage/**")).authenticated().
                                // "/admin/**" 경로는 'ADMIN' 역할을 가진 사용자만 접근할 수 있음
                                 requestMatchers(antMatcher("/admin/**")).hasRole("ADMIN").
                                // 위에서 정의되지 않은 나머지 모든 경로는 누구나 접근할 수 있음
                                 anyRequest().permitAll()
        );

        http.csrf(AbstractHttpConfigurer::disable); // CSRF 보호 기능 비활성화

        // 폼 로그인 설정
        http.formLogin(
                customizer -> customizer
                        // 로그인 페이지의 URL을 설정함. 사용자가 로그인을 시도할 때 이 URL로 접근
                        .loginPage("/login")
                        // 로그인 성공 후 리다이렉트 될 기본 URL 설정. true로 설정되면 항상 이 URL로 리다이렉트
                        .defaultSuccessUrl("/", true)
                        // 모든 사용자가 로그인 페이지에 접근할 수 있도록 허용
                        .permitAll()
        );

        // 접근 거부 처리 설정
        http.exceptionHandling(
                customizer -> customizer.accessDeniedPage("/accessDenied")
        );

        // 로그아웃 설정
        http.logout(
                customizer -> customizer
                        // 로그아웃을 요청하는 URL을 설정함. 사용자가 로그아웃을 시도할 때 이 URL로 요청
                        .logoutUrl("/logout")
                        // 로그아웃 성공 후 리다이렉트 될 URL 설정. 로그아웃 후에는 보통 메인 페이지나 로그인 페이지로 리다이렉트함
                        .logoutSuccessUrl("/")
                        // 로그아웃 시 HTTP 세션을 무효화할지 설정. true로 설정하면 로그아웃 시 세션 무효화
                        .invalidateHttpSession(true)
                        // 로그아웃 시 특정 쿠키를 삭제할 수 있음. 여기서는 JSESSIONID 쿠키를 삭제하도록 설정
                        .deleteCookies("JSESSIONID")
                        // 모든 사용자가 로그아웃 URL에 접근할 수 있도록 허용
                        .permitAll()
        );

        // 세션 관리 설정
        http.sessionManagement(customizer ->
                        // 세션 생성 정책을 설정
                        customizer
                                // IF_REQUIRED: 필요할 때만 세션을 생성함. 예를 들어, 로그인을 통해 인증이 필요한 경우에만 세션을 생성함
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                // STATELESS: 세션을 전혀 사용하지 않음. 각 요청을 독립적으로 처리하며, 인증 정보를 세션에 저장하지 않음
                                // .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        return http.build(); // HttpSecurity 구성 반환
    } // filterChain

    // PasswordEncoder Bean 설정
    @Bean
    public PasswordEncoder passwordEncoder(){
        log.trace("passwordEncoder() invoked");
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    } // passwordEncoder

} // end class
