package trader.components;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.BrowserFrame;

public class RealTimeTicker extends AbsoluteLayout {
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
