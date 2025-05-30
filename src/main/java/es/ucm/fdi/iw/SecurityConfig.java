package es.ucm.fdi.iw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration.
 * 
 * Most security configuration will appear in this file, but according to 
 * https://spring.io/guides/topicals/spring-security-architecture/, it is not
 * a bad idea to also use method security (via @Secured annotations in methods) 
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private Environment env;

	/**
	 * Main security configuration.
	 * 
	 * The first rule that matches will be followed - so if a rule decides to grant access
	 * to a resource, a later rule cannot deny that access, and vice-versa.
	 * 
	 * To disable security entirely, just add an .antMatchers("**").permitAll() 
	 * as a first rule. Note that this may break an application that expects to have
	 * login information available.
	 */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// acceso a consola h2 en modo debug
		String debugProperty = env.getProperty("es.ucm.fdi.debug");
		if (debugProperty != null && Boolean.parseBoolean(debugProperty.toLowerCase())) {
			http.csrf(csrf -> csrf
				.ignoringRequestMatchers("/h2/**")
			);
			http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/h2/**").permitAll()  // <-- no login for h2 console
			);
      		http.headers(header->header.frameOptions(frameOptions->frameOptions.sameOrigin()));
		}

        http
			.csrf(csrf -> csrf
				.ignoringRequestMatchers("/api/**")
			)
            .authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/css/**", "/js/**", "/img/**", "/", "/error", "/registro", "/register", "/viewProfile").permitAll()
				.requestMatchers("/api/**", "/ranking").permitAll()            // <-- public api access
				.requestMatchers("/api/**", "/reglas1").permitAll()            // <-- public api access
				.requestMatchers("/api/**", "/galeria").permitAll()            // <-- public api access
				.requestMatchers("/api/**", "/autores").permitAll()            // <-- public api access
				.requestMatchers("/api/**", "/creaPartida").permitAll()            // <-- public api access
				.requestMatchers("/find-game").authenticated()

				.requestMatchers("/admin/**").hasRole("ADMIN")	   // <-- administration
				.requestMatchers(HttpMethod.DELETE, "/admin/gestHeroes/delete/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/admin/gestHeroes/add").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/admin/gestHeroes/update/**").hasRole("ADMIN")

				//.requestMatchers("/user/**").hasRole("USER")     LO HE CAMBIADO DE MOMENTO, REVISAR SI ES NECESARIO HACERLO ASI
				.requestMatchers("/user/**").permitAll()
				
				.anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .permitAll()
				.successHandler(loginSuccessHandler)  // <-- called when login Ok; can redirect
            );

        return http.build();
    }	
	
	/**
	 * Declares a PasswordEncoder bean.
	 * 
	 * This allows you to write, in any part of Spring-managed code, 
	 * @Autowired PasswordEncoder passwordEncoder, and have it initialized
	 * with the result of this method. 
	 */
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		// by default in Spring Security 5, a wrapped new BCryptPasswordEncoder();
		return PasswordEncoderFactories.createDelegatingPasswordEncoder(); 
	}	
	
	/**
	 * Declares a springDataUserDetailsService bean.
	 * 
	 * This is used to translate from Spring Security users to in-application users.
	 */
	@Bean
	public IwUserDetailsService springDataUserDetailsService() {
		return new IwUserDetailsService();
	} 
	
	/**
	 * Declares an AuthenticationManager bean.
	 * 
	 * This can be used to auto-login into the site after creating new users, for example.
	 * See https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html#publish-authentication-manager-bean
	 */
	 @Bean
	 public AuthenticationManager authenticationManager(
			UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(authenticationProvider);
	}
	 
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;

	@Autowired
	private LoginFailureHandler loginFailureHandler;
}
