package trader.views;

import javax.annotation.PostConstruct;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = "dashboard")
public class DashboardView extends AbsoluteLayout implements View {

	@PostConstruct
	protected void initialize() {
		
		BrowserFrame browser = new BrowserFrame("", new ThemeResource("HTML/graph.html?FX:EURUSD"));
		browser.setSizeFull();
		//browser.setSource(new ThemeResource("HTML/graph.html?FX:EURCHF"));
        
        
        Button down = new Button("PUT");
        down.addStyleName(ValoTheme.BUTTON_DANGER);
        down.setIcon(FontAwesome.ARROW_DOWN);
        
        Button up = new Button("CALL");
        up.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        up.setIcon(FontAwesome.ARROW_UP);
        
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponents(up, down);
        buttons.setSpacing(true);
        
        addComponent(browser, "left: 0%; top: 0%;");
        addComponent(buttons, "right: 59px; top: 250px;");
        setSizeFull();
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
