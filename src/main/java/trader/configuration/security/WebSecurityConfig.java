//package trader.configuration.security;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationDetailsSource;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//	private static PasswordEncoder encoder;
//
//    @Autowired
//    private UserDetailsService traderUserDetailsService;
//    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//    	http.
//        authorizeRequests()
//            .antMatchers("/","/login","/vaadinServlet/**", "/VAADIN/**", "/PUSH/**", "/UIDL/**").permitAll()
//            .antMatchers("/user/**").access("hasRole('ROLE_USER')")
//            .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
//            .anyRequest().authenticated()
//        .and()
//            .formLogin().loginPage("/login")
////            .usernameParameter("username")
////            .passwordParameter("password")
//        .and()
//            .logout()
//            .permitAll()
//        .and()
//            .csrf().disable()
//            .exceptionHandling()
//            .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
//        
//        
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(traderUserDetailsService)
//                .passwordEncoder(passwordEncoder());
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        if(encoder == null) {
//            encoder = new BCryptPasswordEncoder();
//        }
//
//        return encoder;
//    }
//}
