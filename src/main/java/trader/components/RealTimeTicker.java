package trader.components;

//import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.BrowserFrame;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.themes.ValoTheme;

public class RealTimeTicker extends AbsoluteLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2902220055714080981L;

	private String symbol = "FX:EURUSD";
	
	private BrowserFrame browser = new BrowserFrame("Real Time Ticker");
	
	public RealTimeTicker() {
		updateTicker();
		setupLayout();
	}
	
	public RealTimeTicker(String symbol) {
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
		browser.setSource(new ThemeResource("HTML/tickerWrapper.html?"+symbol));
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
		updateTicker();
	}
	
	
}
