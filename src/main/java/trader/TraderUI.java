package trader;

import java.util.Locale;

import javax.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vaadin.spring.i18n.MessageProvider;
import org.vaadin.spring.i18n.ResourceBundleMessageProvider;
import org.vaadin.spring.security.VaadinSecurity;
import org.vaadin.spring.security.util.SecurityExceptionUtils;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import trader.views.AccessDeniedView;
import trader.views.ErrorView;
import trader.views.TradingAreaView;

@SpringUI(path = "")
@Theme("trader")
@Title("Trading Website")
@Widgetset("TraderWidgetSet")
@SuppressWarnings("serial")
public class TraderUI extends UI {
	
	private Navigator navigator;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
    VaadinSecurity vaadinSecurity;
	
	@Autowired
	private SpringViewProvider springViewProvider;
	
	@Override
	protected void init(VaadinRequest request) {
		setLocale(Locale.ENGLISH);
        // Let's register a custom error handler to make the 'access denied' messages a bit friendlier.
        setErrorHandler(new DefaultErrorHandler() {
            @Override
            public void error(com.vaadin.server.ErrorEvent event) {
                if (SecurityExceptionUtils.isAccessDeniedException(event.getThrowable())) {
                    Notification.show("Sorry, you don't have access to do that.");
                } else {
                    super.error(event);
                }
            }
        });
        
		VerticalLayout layout = new VerticalLayout();
		Panel viewContent = new Panel();
		MenuBar menu = new MenuBar();
		menu.addStyleName("navigation-menu");
		
		layout.addComponents(menu, viewContent);
		layout.setSizeFull();
		viewContent.setSizeFull();
		layout.setExpandRatio(viewContent, 1);
		
		menu.addItem("Trading Area", e -> onDashboardClicked());
		menu.addItem("Users", e -> onCustomersClicked());
		
		navigator = new Navigator(this, viewContent);
		springViewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
        navigator.addProvider(springViewProvider);
        navigator.setErrorView(ErrorView.class);
        setContent(layout);
        String state = navigator.getState();
        if(state != null && !state.isEmpty()) {
        	navigator.navigateTo(state);
        }
        else {
        	navigator.navigateTo(TradingAreaView.NAME);
        }
        
	}
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	private void onCustomersClicked() {
		navigator.navigateTo("users");
	}

	private void onDashboardClicked() {
		navigator.navigateTo(TradingAreaView.NAME);
	}
	
	@Configuration
	public static class MyModule {
		
		public MyModule() {
			
		}
	    
	    @Bean
	    MessageProvider communicationMessages() {
	        return new ResourceBundleMessageProvider("lang/messages"); // Will use UTF-8 by default
	    }
	    
	    @Bean(name="springBootServletRegistrationBean")
	    public ServletRegistrationBean servletRegistrationBean() {
	        SpringVaadinServlet servlet = new SpringVaadinServlet() {

	            private static final long serialVersionUID = 1L;

				@Override
	            public void servletInitialized() throws ServletException {
	                super.servletInitialized();
	                getService().addSessionInitListener(new TraderSessionInitListener());
	            }
	            
	        };
	        
	        return new ServletRegistrationBean(servlet, "/ui/*", "/VAADIN/*");
	    }
	}
}
