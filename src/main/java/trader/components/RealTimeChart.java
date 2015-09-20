package trader.components;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.BrowserFrame;

public class RealTimeChart extends AbsoluteLayout {
	
	private String symbol = "FX:EURUSD";
	private String timeZone = "Etc/UTC";
	private String locale = "en";
	
	private BrowserFrame browser = new BrowserFrame("Real Time Chart");
	
	public RealTimeChart() {
		updateChart();
		setupLayout();
	}

	public RealTimeChart(String symbol, String timeZone, String locale) {
		super();
		this.symbol = symbol;
		this.timeZone = timeZone;
		this.locale = locale;
		
		updateChart();
		setupLayout();
	}

	private void setupLayout() {
		browser.setSizeFull();
		addComponent(browser, "left: 0%; top: 0%;");
		setSizeFull();
	}
	
	
	private void updateChart() {
		browser.setSource(new ThemeResource("HTML/graph.html?symbol="+symbol+"&zone="+timeZone+"&locale="+locale));
	}


	public String getChartSymbol() {
		return symbol;
	}


	public void setChartSymbol(String symbol) {
		if(!this.symbol.equals(symbol)) {
			this.symbol = symbol;
			updateChart();
		}
	}


	public String getChartTimeZone() {
		return timeZone;
	}


	public void setChartTimeZone(String timeZone) {
		if(!this.timeZone.equals(timeZone)) {
			this.timeZone = timeZone;
			updateChart();
		}
	}


	public String getChartLocale() {
		return locale;
	}


	public void setChartLocale(String locale) {
		if(!this.locale.equals(locale)) {
			this.locale = locale;
			updateChart();
		}
	}
	
}
