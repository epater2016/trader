package trader.components;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

//import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.BrowserFrame;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.themes.ValoTheme;

public class LiveTicker extends AbsoluteLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2902220055714080981L;

	private String symbol = "EURUSD";
	
	private BrowserFrame browser = new BrowserFrame("Real Time Ticker");
	
	public LiveTicker() {
		updateTicker();
		setupLayout();
	}
	
	public LiveTicker(String symbol) {
		super();
		this.symbol = symbol;
		updateTicker();
		setupLayout();
	}

	private void setupLayout() {
		browser.setSizeFull();
		addComponent(browser, "left: 0%; top: 0%;");
		
//      Button down = new Button("PUT");
//      down.addStyleName(ValoTheme.BUTTON_DANGER);
//      down.setIcon(FontAwesome.ARROW_DOWN);
//        
//      Button up = new Button("CALL");
//      up.addStyleName(ValoTheme.BUTTON_FRIENDLY);
//      up.setIcon(FontAwesome.ARROW_UP);
		
		setWidth("200px");
		setHeight("180px");
	}

	private void updateTicker() {
//		browser.setSource(new ThemeResource("HTML/tickerWrapper.html?"+symbol));
		try {
			browser.setSource(new ThemeResource("HTML/live-ticker.html?" + URLEncoder.encode(symbol, "UTF-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
		updateTicker();
	}
	
	
}
