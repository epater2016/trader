package trader.components;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.BrowserFrame;

public class LiveChart extends AbsoluteLayout {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3154463732483823099L;
	private String symbol = "1:EURUSD";
	private String timeZone = "Etc/UTC";
	private String locale = "en";
	
	private BrowserFrame browser = new BrowserFrame("Real Time Chart");
	
	public LiveChart() {
		updateChart();
		setupLayout();
		browser.setId("trader-chart-browser");
	}

	public LiveChart(String symbol, String timeZone, String locale) {
		super();
		this.symbol = symbol;
		this.timeZone = timeZone;
		this.locale = locale;
		
		updateChart();
		setupLayout();
	}

	private void setupLayout() {
		browser.setSizeFull();
		Responsive.makeResponsive(browser);
		addComponent(browser, "left: 0%; top: 0%;");
		setSizeFull();
	}
	
	
	private void updateChart() {
//		browser.setSource(new ThemeResource("HTML/graph.html?symbol="+symbol+"&zone="+timeZone+"&locale="+locale));
		try {
			browser.setSource(new ThemeResource("HTML/live-chart.html?" + URLEncoder.encode(symbol, "UTF-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
