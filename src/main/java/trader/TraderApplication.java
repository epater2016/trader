package trader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.vaadin.spring.events.annotation.EnableEventBus;
import org.vaadin.spring.http.HttpService;
import org.vaadin.spring.i18n.annotation.EnableI18N;
import org.vaadin.spring.security.web.VaadinRedirectStrategy;
import org.vaadin.spring.security.web.authentication.VaadinAuthenticationSuccessHandler;
import org.vaadin.spring.security.web.authentication.VaadinUrlAuthenticationSuccessHandler;

import org.vaadin.spring.security.annotation.*;
import org.vaadin.spring.security.config.VaadinSharedSecurityConfiguration;

//import trader.repositories.MarketRepository;
//import trader.repositories.QuoteRepository;
//import trader.repositories.UserRepository;

@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class)
@EnableI18N
@EnableScheduling
@EnableEventBus
public class TraderApplication {

//    @Autowired
//    private UserRepository users;
//    
//    @Autowired
//    private MarketRepository markets;
//    
//    @Autowired
//    private QuoteRepository quotes;
	
	public static void main(String[] args) {
        SpringApplication.run(TraderApplication.class, args);
    }
	
	@Bean
	public CommandLineRunner initializeData() {
		return args -> {
//			repository.deleteAll();
//			User adminUser = users.save(new User("hitam@live.com", "Haitam", "Haj Ali", "57571573"));
//			adminUser.getRoles().add(new UserRole(Role.ROLE_ADMIN, adminUser));
//			User user = users.save(new User("majdy@live.com", "Majdy", "Haj Ali", "mj@Aloha!"));
//			user.getRoles().add(new UserRole(Role.ROLE_USER, user));
//			users.save(adminUser);
//			users.save(user);
//			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//			cal.clear();
//			
//			cal.set(Calendar.HOUR_OF_DAY, 6);
//			cal.set(Calendar.MINUTE, 0);
//			Date openHour = cal.getTime();
//			cal.set(Calendar.HOUR_OF_DAY, 20);
//			Date closeHour = cal.getTime();
//			Market forex = new Market("FOREX", Calendar.MONDAY, Calendar.FRIDAY, openHour, closeHour);
//			cal.set(Calendar.HOUR_OF_DAY, 14);
//			cal.set(Calendar.MINUTE, 30);
//			openHour = cal.getTime();
//			cal.set(Calendar.HOUR_OF_DAY, 21);
//			cal.set(Calendar.MINUTE, 0);
//			closeHour = cal.getTime();
//			Market nyse = new Market("NYSE", Calendar.MONDAY, Calendar.FRIDAY, openHour, closeHour);
//			markets.save(forex);
//			markets.save(nyse);
//			quotes.save(new Quote("EURUSD", "EUR/USD", QuoteCategory.CURRENCIES, forex, "FX:EURUSD", "EURUSD=x", 80));
//			quotes.save(new Quote("GOOG", "Google Inc", QuoteCategory.STOCKS, nyse, "NASDAQ:GOOG", "GOOG", 80));
		};
	}

    /**
     * Configure Spring Security.
     */
    @Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
    @EnableVaadinSharedSecurity
    static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    	
    	private static PasswordEncoder encoder;
    	
    	@Autowired
        private UserDetailsService userDetailsService;

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth.inMemoryAuthentication()
//                    .withUser("user").password("user").roles("USER")
//                    .and()
//                    .withUser("admin").password("admin").roles("ADMIN");
        	
          auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable(); // Use Vaadin's built-in CSRF protection instead
            http.authorizeRequests()
            		.antMatchers("/ui/").anonymous()
            		.antMatchers("/ui/login/**").anonymous()
                    .antMatchers("/ui/UIDL/**").permitAll()
                    .antMatchers("/ui/HEARTBEAT/**").permitAll()
                    .antMatchers("/ui/").permitAll()
                    .antMatchers("/ui").denyAll()
                    .anyRequest().authenticated();
            http.httpBasic().disable();
            http.formLogin().disable();
            http.logout()
                    .logoutUrl("/ui/logout")
                    .logoutSuccessUrl("/ui/login?logout")
                    .permitAll();
            http.exceptionHandling()
                    .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/ui/login"));
            http.rememberMe().rememberMeServices(rememberMeServices()).key("myAppKey");
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/VAADIN/**");
        }
        
        

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Bean
        public RememberMeServices rememberMeServices() {
            // TODO Is there some way of exposing the RememberMeServices instance that the remember me configurer creates by default?
            TokenBasedRememberMeServices services = new TokenBasedRememberMeServices("myAppKey", userDetailsService());
            services.setAlwaysRemember(true);
            return services;
        }

        @Bean(name = VaadinSharedSecurityConfiguration.VAADIN_AUTHENTICATION_SUCCESS_HANDLER_BEAN)
        VaadinAuthenticationSuccessHandler vaadinAuthenticationSuccessHandler(HttpService httpService, VaadinRedirectStrategy vaadinRedirectStrategy) {
            return new VaadinUrlAuthenticationSuccessHandler(httpService, vaadinRedirectStrategy, "/ui/"); 
        }
        
        @Bean
        public PasswordEncoder passwordEncoder() {
        	if(encoder == null) {
        		encoder = new BCryptPasswordEncoder();
        	}
  
        	return encoder;
        }
    }
}
