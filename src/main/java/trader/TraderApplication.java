package trader;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.vaadin.spring.http.HttpService;
import org.vaadin.spring.security.web.VaadinRedirectStrategy;
import org.vaadin.spring.security.web.authentication.VaadinAuthenticationSuccessHandler;
import org.vaadin.spring.security.web.authentication.VaadinUrlAuthenticationSuccessHandler;

import com.google.gwt.i18n.client.TimeZone;

import org.vaadin.spring.security.annotation.*;
import org.vaadin.spring.security.config.VaadinSharedSecurityConfiguration;

import trader.models.Role;
import trader.models.Roles;
import trader.models.User;
import trader.models.UserRole;
import trader.repositories.UserRepository;
import trader.services.UserDetailsServiceImpl;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.quotes.fx.FxQuote;
import yahoofinance.quotes.fx.FxSymbols;

@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class)
public class TraderApplication {

    @Autowired
    private UserRepository repository;
	
	public static void main(String[] args) {
        SpringApplication.run(TraderApplication.class, args);
    }
	
	@Bean
	public CommandLineRunner initializeData() {
		return args -> {
//			repository.deleteAll();
//			User adminUser = repository.save(User.createUser("hitam@live.com", "Haitam", "Haj Ali", "57571573"));
//			adminUser.getRoles().add(new UserRole(Role.ROLE_ADMIN, adminUser));
//			User user = repository.save(User.createUser("majdy@live.com", "Majdy", "Haj Ali", "mj@Aloha!"));
//			user.getRoles().add(new UserRole(Role.ROLE_USER, user));
//			repository.save(adminUser);
//			repository.save(user);
			
			Stock usdeur = YahooFinance.get(FxSymbols.EURUSD);
			System.out.println(usdeur);

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
            		.antMatchers("/").anonymous()        
            		.antMatchers("/login/**").anonymous()
                    .antMatchers("/vaadinServlet/UIDL/**").permitAll()
                    .antMatchers("/vaadinServlet/HEARTBEAT/**").permitAll()
                    .antMatchers("/").permitAll()
                    .anyRequest().authenticated();
            http.httpBasic().disable();
            http.formLogin().disable();
            http.logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .permitAll();
            http.exceptionHandling()
                    .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
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
            return new VaadinUrlAuthenticationSuccessHandler(httpService, vaadinRedirectStrategy, "/"); 
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
