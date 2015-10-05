package trader.views;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.i18n.I18N;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
//import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import trader.components.LiveChart;
import trader.components.LiveTicker;
import trader.models.Quote;
import trader.services.TradingService;

@SpringView(name = TradingAreaView.NAME)
public class TradingAreaView extends Panel implements View {

	private static final long serialVersionUID = 7174365612115919173L;

	public static final String NAME = "tradingArea";
	
    public static final String TITLE_ID = "trading-area-title";
    public static final String EDIT_ID = "trading-area-edit";
    
    @Autowired
    private EventBus.ViewEventBus eventBus;
    
	@Autowired
	private TradingService tradingService;
	
	@Autowired
	I18N i18n;

    private Label titleLabel;
    private CssLayout tradingAreaPanels;
    private VerticalLayout root;
//    private Window notificationsWindow;

	private ComboBox quoteSelector;

	private LiveChart chart;

	private LiveTicker ticker;

	private BeanContainer<String, Quote> quotes;

	private ChartToolbar chartToolbar;
	
	@PostConstruct
	protected void initialize() {
		
		
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();
        eventBus.subscribe(this);
        
        root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.addStyleName("tradingarea-view");
        setContent(root);
        Responsive.makeResponsive(root);

        root.addComponent(buildHeader());

//        root.addComponent(buildBanner());

        Component content = buildContent();
        root.addComponent(content);
        root.setExpandRatio(content, 1);

        // All the open sub-windows should be closed whenever the root layout
        // gets clicked.
//        root.addLayoutClickListener(new LayoutClickListener() {
//            @Override
//            public void layoutClick(final LayoutClickEvent event) {
//                DashboardEventBus.post(new CloseOpenWindowsEvent());
//            }
//        });
		
//		BrowserFrame browser = new BrowserFrame("", new ThemeResource("HTML/graph.html?FX:EURUSD"));
//		browser.setSizeFull();
//		//browser.setSource(new ThemeResource("HTML/graph.html?FX:EURCHF"));
//        
//        
//        Button down = new Button("PUT");
//        down.addStyleName(ValoTheme.BUTTON_DANGER);
//        down.setIcon(FontAwesome.ARROW_DOWN);
//        
//        Button up = new Button("CALL");
//        up.addStyleName(ValoTheme.BUTTON_FRIENDLY);
//        up.setIcon(FontAwesome.ARROW_UP);
//        
//        HorizontalLayout buttons = new HorizontalLayout();
//        buttons.addComponents(up, down);
//        buttons.setSpacing(true);
//        
//        addComponent(browser, "left: 0%; top: 0%;");
//        addComponent(buttons, "right: 59px; top: 250px;");
//		RealTimeChart chart = new RealTimeChart();
//		addComponents(chart, new RealTimeTicker());
//		setExpandRatio(chart, 1);
//        setSizeFull();
	}
	
	private Component buildHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);

        titleLabel = new Label(i18n.get("trader.tradingarea.title"));
        titleLabel.setId(TITLE_ID);
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName(ValoTheme.LABEL_H2);
        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(titleLabel);

//        Component quoteSelector = buildQuoteSelector();
//        Component edit = buildEditButton();
//        HorizontalLayout tools = new HorizontalLayout(quoteSelector, edit);
//        tools.setSpacing(true);
//        tools.addStyleName("toolbar");
//        header.addComponent(tools);

        return header;
	}
	
    private Component buildQuoteSelector() {
		quoteSelector = new ComboBox();
		
//		quoteSelector.setInputPrompt("Select Stock/Index...");
		
//		quoteSelector.setItemCaptionPropertyId(Quote.PROPERTY_NAME);
//		quoteSelector.setItemCaptionMode(ItemCaptionMode.PROPERTY);

		
        // Set full width
//		quoteSelector.setWidth(100.0f, Unit.PERCENTAGE);
		quoteSelector.addStyleName("borderless");
		
        // Set the appropriate filtering mode for this example
		quoteSelector.setFilteringMode(FilteringMode.CONTAINS);
		quoteSelector.setImmediate(true);
 
        // Disallow null selections
		quoteSelector.setNullSelectionAllowed(false);
		
		quoteSelector.addValueChangeListener(e -> {
                chart.setChartSymbol(quotes.getItem(e.getProperty().getValue()).getBean().getChartSrc());
                ticker.setSymbol(quotes.getItem(e.getProperty().getValue()).getBean().getTickerSrc());
                chartToolbar.getCharts().getChildren().get(LiveChart.ChartDrawType.LINE.ordinal()*2).setChecked(false);
                chartToolbar.getCharts().getChildren().get(LiveChart.ChartDrawType.CANDLE.ordinal()*2).setChecked(true);
		});
		
		return quoteSelector;
	}

//	private Component buildBanner() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	private Component buildContent() {
		tradingAreaPanels = new CssLayout();
		tradingAreaPanels.addStyleName("tradingarea-panels");
        Responsive.makeResponsive(tradingAreaPanels);

        tradingAreaPanels.addComponent(buildChart());
        tradingAreaPanels.addComponent(buildTicker());
		
		return tradingAreaPanels;
	}

	private Component buildTicker() {
		ticker = new LiveTicker();
		return createContentWrapper("tradingarea-panel-slot-ticker", new Label("Payout 80%"), ticker);
	}

	private Component buildChart() {
		chart = new LiveChart();
		chartToolbar = new ChartToolbar();
		return createContentWrapper("tradingarea-panel-slot-chart", chartToolbar, chart);
	}
	
    private Component createContentWrapper(String slotStyle, Component toolBar, Component content) {
        CssLayout slot = new CssLayout();
        slot.setWidth("100%");
        slot.addStyleName(slotStyle);

        CssLayout card = new CssLayout();
        card.setWidth("100%");
        card.addStyleName(ValoTheme.LAYOUT_CARD);
        
        if(toolBar instanceof SlotToolBar) {
        	((SlotToolBar)toolBar).setSlot(slot);
        }

        card.addComponents(toolBar, content);
        slot.addComponent(card);
        return slot;
    }
    
    private interface SlotToolBar {
    	void setSlot(CssLayout slot);
    }

	private class ChartToolbar extends HorizontalLayout implements SlotToolBar {
		
		private CssLayout slot;
		private MenuItem timeSteps;
		private MenuItem charts;
		
		public ChartToolbar() {
	        addStyleName("tradingarea-panel-toolbar");
	        setWidth("100%");
	
	
	        MenuBar tools = new MenuBar();
	        tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
	
	        timeSteps = tools.addItem("", FontAwesome.CLOCK_O, null);
	        
	        for(LiveChart.TimeStep step : LiveChart.TimeStep.values()) {
	
	        	MenuItem menuTimeStep = timeSteps.addItem(i18n.get("trader.tradingarea.chart.timestep."+ step.toString()),
														  selectedItem -> {
															  timeSteps.getChildren().forEach(i->i.setChecked(false));
															  chart.setTimeStep(step);
															  selectedItem.setChecked(true);
														  });
	        	menuTimeStep.setCheckable(true);
	        	if(step.ordinal() + 1 < LiveChart.TimeStep.values().length) {
	        		timeSteps.addSeparator();
	        	}
	        }
	        
	        charts = tools.addItem("", FontAwesome.BAR_CHART_O, null);
	        
	        for(LiveChart.ChartDrawType drawType : LiveChart.ChartDrawType.values()) {
	        	MenuItem menuChartDrawType = charts.addItem(i18n.get("trader.tradingarea.chart.drawtype."+ drawType.toString()),
	        				   selectedItem -> {
	        					   charts.getChildren().forEach(i->i.setChecked(false));
	        					   chart.setChartDrawType(drawType);
	        					   selectedItem.setChecked(true);
	        				   });
	        	menuChartDrawType.setCheckable(true);
	        	if(drawType.ordinal() + 1 < LiveChart.ChartDrawType.values().length) {
	        		charts.addSeparator();
	        	}
	        }
	        
	        MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {
	
				private static final long serialVersionUID = -3443829142738989399L;
	
				@Override
	            public void menuSelected(final MenuItem selectedItem) {
	                if (!slot.getStyleName().contains("max")) {
	                    selectedItem.setIcon(FontAwesome.COMPRESS);
	                    toggleMaximized(slot, true);
	                } else {
	                    slot.removeStyleName("max");
	                    selectedItem.setIcon(FontAwesome.EXPAND);
	                    chart.markAsDirty();
	                    toggleMaximized(slot, false);
	                }
	            }
	        });
	        max.setStyleName("icon-only");
	        
	        Component quoteSelector = buildQuoteSelector();
	        addComponents(quoteSelector, tools);
	        setComponentAlignment(quoteSelector, Alignment.MIDDLE_LEFT);
	        setComponentAlignment(tools, Alignment.MIDDLE_RIGHT);
		}
		
		@Override
		public void setSlot(CssLayout slot) {
			this.slot = slot;
			
		}

		public MenuItem getTimeSteps() {
			return timeSteps;
		}

		public MenuItem getCharts() {
			return charts;
		}
		
		
	}

    private void toggleMaximized(final Component panel, final boolean maximized) {
        for (Iterator<Component> it = root.iterator(); it.hasNext();) {
            it.next().setVisible(!maximized);
        }
        tradingAreaPanels.setVisible(true);

        for (Iterator<Component> it = tradingAreaPanels.iterator(); it.hasNext();) {
            Component c = it.next();
            c.setVisible(!maximized);
        }

        if (maximized) {
            panel.setVisible(true);
            panel.addStyleName("max");
        } else {
            panel.removeStyleName("max");
        }
    }

	@Override
	public void enter(ViewChangeEvent event) {
		quotes = new BeanContainer<String, Quote>(Quote.class);
		    
	    // Use the name property as the item ID of the bean
		quotes.setBeanIdProperty(Quote.PROPERTY_SYMBOL_ID);
		
		List<Quote> quotesList = tradingService.getQuotes();
		quotes.addAll(quotesList);
//		quoteSelector.setContainerDataSource(quotes);
//		quoteSelector.setInputPrompt(quotesList.get(0).getName());
		
		for (Quote quote : quotesList) {
			quoteSelector.addItem(quote.getSymbol());
			quoteSelector.setItemCaption(quote.getSymbol(), quote.getName());
		}
		
		chartToolbar.getTimeSteps().getChildren().get(chart.getTimeStep().ordinal()*2).setChecked(true);
		chart.setChartDrawType(LiveChart.ChartDrawType.LINE);
		chartToolbar.getCharts().getChildren().get(LiveChart.ChartDrawType.LINE.ordinal()*2).setChecked(true);
		quoteSelector.select(quotesList.get(0).getSymbol());
		
		
	}

}
