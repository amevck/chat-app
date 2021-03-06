package netgloo.configs;

//import netgloo.services.CustomAuthenticationSuccessHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//
//  /**
//   * Enable authentication with three in-memory users: UserA, UserB and UserC.
//   *
//   * Spring Security will provide a default login form where insert username
//   * and password.
//   */
//  @Autowired
//  public void configureGlobal(AuthenticationManagerBuilder auth)
//  throws Exception {
//    auth
//      // Defines three users with their passwords and roles
//      .inMemoryAuthentication().withUser("UserA").password("UserA").roles("USER")
//      .and()
//      .withUser("UserB").password("UserB").roles("USER")
//      .and()
//      .withUser("UserC").password("UserC").roles("USER");
//    return;
//  }
//
//  /**
//   * Disable CSRF protection (to simplify this demo) and enable the default
//   * login form.
//   */
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http
//
//      // Disable CSRF protection
//      .csrf().disable()
//      // Set default configurations from Spring Security
//      .authorizeRequests()
//        .anyRequest().authenticated()
//        .and()
//      .formLogin()
//            .successHandler(new CustomAuthenticationSuccessHandler())
//        .and()
//      .httpBasic();
//    return;
//
//
//
//  }
//
//} // class WebSecurityConfig



import netgloo.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;

  @Bean
  public UserDetailsService mongoUserDetails() {
    return new CustomUserDetailsService();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    UserDetailsService userDetailsService = mongoUserDetails();
    auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(bCryptPasswordEncoder);

  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http

            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/home").permitAll()
            .antMatchers("/login").permitAll()
            .antMatchers("/signup").permitAll()
            .antMatchers("/dashboard/**").hasAuthority("ADMIN").anyRequest()
            .permitAll().and().csrf().disable().formLogin().successHandler(customizeAuthenticationSuccessHandler)
            .loginPage("/login").failureUrl("/login?error=true")
            .usernameParameter("email")
            .passwordParameter("password")
            .and().logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/").and().exceptionHandling().and().sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());;
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web
            .ignoring()
            .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
  }


  @Bean
  public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }

  @Bean
  public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
    return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
  }

}