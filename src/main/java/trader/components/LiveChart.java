package trader.components;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;

import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Component;
import com.vaadin.ui.JavaScript;

public class LiveChart extends AbsoluteLayout {
	
	private static final String LIVE_CHART_ID = "trader-chart-browser";
	/**
	 * 
	 */
	private static final long serialVersionUID = -3154463732483823099L;
	private String symbol = "1:EURUSD";
	
	private BrowserFrame browser = new BrowserFrame("Real Time Chart");
	private TimeStep timeStep = null;
	private ChartDrawType chartDrawType = ChartDrawType.CANDLE;
	
	public enum TimeStep {
		M1,
		M5,
		M15,
		M30,
		H1,
		H4,
		D1,
		W1
	}
	
	public enum ChartDrawType {
		LINE,
		CANDLE
	}
	
	public LiveChart() {
		updateChart();
		setupLayout();
		browser.setId(LIVE_CHART_ID);
		
	}

	public LiveChart(String symbol) {
		super();
		this.symbol = symbol;
		
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
		try {
			browser.setSource(new ThemeResource("HTML/live-chart.html?" + URLEncoder.encode(symbol, "UTF-8")));
			chartDrawType = ChartDrawType.CANDLE;
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

	public TimeStep getTimeStep() {
		if(null == timeStep) {
			Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();

		    // Iterate to find cookie by its name
		    for (Cookie cookie : cookies) {
		        if ("ifcchartsstep".equals(cookie.getName())) {
		        	switch(Integer.parseInt(cookie.getValue())) {
		        	case 1:
		        		timeStep = TimeStep.M1;
		        		break;
		        	case 5:
		        		timeStep = TimeStep.M5;
		        		break;
		        	case 15:
		        		timeStep = TimeStep.M15;
		        		break;
		        	case 30:
		        		timeStep = TimeStep.M30;
		        		break;
		        	case 60:
		        		timeStep = TimeStep.H1;
		        		break;
		        	default:
		        	case 240:
		        		timeStep = TimeStep.H4;
		        		break;
		        	case 1440:
		        		timeStep = TimeStep.D1;
		        		break;
		        	case 10080:
		        		timeStep = TimeStep.W1;
		        		break;
		        	}
		        }
		    }
		    if(null == timeStep) { // no cookie
		    	timeStep = TimeStep.H4; // default
		    }
		}
		
		return timeStep;
	}

	public void setTimeStep(TimeStep timeStep) {
		if (this.timeStep == null || this.timeStep != timeStep) {
			_jsSetTimeStep(timeStep);
			this.timeStep = timeStep;
		}
	}

	private void _jsSetTimeStep(TimeStep timeStep) {
		JavaScript.getCurrent()
				.execute("document.getElementById('" + LIVE_CHART_ID
						+ "').firstElementChild.contentDocument.getElementsByClassName('stepli')["
						+ timeStep.ordinal() + "].click();");
	}

	public ChartDrawType getChartDrawType() {
		return chartDrawType;
	}

	public void setChartDrawType(ChartDrawType chartDrawType) {
		if (this.chartDrawType == null || this.chartDrawType != chartDrawType) {
			_jsSetChartDrawType(chartDrawType);
			this.chartDrawType = chartDrawType;
		}
	}

	private void _jsSetChartDrawType(ChartDrawType chartDrawType) {
		JavaScript.getCurrent()
				.execute("document.getElementById('" + LIVE_CHART_ID
						+ "').firstElementChild.contentDocument.getElementsByClassName('chart_drawtype')["
						+ chartDrawType.ordinal() + "].click();");
	}
	
}
