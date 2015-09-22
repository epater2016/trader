package trader.views;

import java.util.Iterator;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.i18n.I18N;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
//import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import trader.components.RealTimeChart;
import trader.components.RealTimeTicker;
import trader.models.Quote;
import trader.services.TradingService;

@SpringView(name = TradingAreaView.NAME)
public class TradingAreaView extends Panel implements View {
	
	/**
	 * 
	 */
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

	private RealTimeChart chart;

	private RealTimeTicker ticker;
	
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

        Component quoteSelector = buildQuoteSelector();
        Component edit = buildEditButton();
        HorizontalLayout tools = new HorizontalLayout(quoteSelector, edit);
        tools.setSpacing(true);
        tools.addStyleName("toolbar");
        header.addComponent(tools);

        return header;
	}

	private Component buildEditButton() {
        Button result = new Button();
        result.setId(EDIT_ID);
        result.setIcon(FontAwesome.EDIT);
        result.addStyleName("icon-edit");
        result.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        result.setDescription("Edit Trading Area Style");

        return result;
    }
	
    private Component buildQuoteSelector() {
		quoteSelector = new ComboBox();
		
		quoteSelector.setInputPrompt("Select Stock/Index...");
		
		quoteSelector.setItemCaptionPropertyId(Quote.PROPERTY_NAME);
		quoteSelector.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		
//		quoteSelector.set
 
 
        // Set full width
		quoteSelector.setWidth(100.0f, Unit.PERCENTAGE);
 
        // Set the appropriate filtering mode for this example
		quoteSelector.setFilteringMode(FilteringMode.CONTAINS);
		quoteSelector.setImmediate(true);
 
        // Disallow null selections
		quoteSelector.setNullSelectionAllowed(false);
		
		quoteSelector.addValueChangeListener(e -> {
                chart.setChartSymbol(String.valueOf(e.getProperty().getValue()));
                ticker.setSymbol(String.valueOf(e.getProperty().getValue()));
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
		ticker = new RealTimeTicker();
		return createContentWrapper(ticker);
	}

	private Component buildChart() {
		chart = new RealTimeChart();
		return createContentWrapper(chart);
	}
	
    private Component createContentWrapper(final Component content) {
        final CssLayout slot = new CssLayout();
        slot.setWidth("100%");
        slot.addStyleName("tradingarea-panel-slot");

        CssLayout card = new CssLayout();
        card.setWidth("100%");
        card.addStyleName(ValoTheme.LAYOUT_CARD);

        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addStyleName("tradingarea-panel-toolbar");
        toolbar.setWidth("100%");

        Label caption = new Label(content.getCaption());
        caption.addStyleName(ValoTheme.LABEL_H4);
        caption.addStyleName(ValoTheme.LABEL_COLORED);
        caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        content.setCaption(null);

        MenuBar tools = new MenuBar();
        tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
        MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {

            /**
			 * 
			 */
			private static final long serialVersionUID = -3443829142738989399L;

			@Override
            public void menuSelected(final MenuItem selectedItem) {
                if (!slot.getStyleName().contains("max")) {
                    selectedItem.setIcon(FontAwesome.COMPRESS);
                    toggleMaximized(slot, true);
                } else {
                    slot.removeStyleName("max");
                    selectedItem.setIcon(FontAwesome.EXPAND);
                    toggleMaximized(slot, false);
                }
            }
        });
        max.setStyleName("icon-only");
//        MenuItem root = tools.addItem("", FontAwesome.COG, null);
//        root.addItem("Configure", new Command() {
//            @Override
//            public void menuSelected(final MenuItem selectedItem) {
//                Notification.show("Not implemented in this demo");
//            }
//        });
//        root.addSeparator();
//        root.addItem("Close", new Command() {
//            @Override
//            public void menuSelected(final MenuItem selectedItem) {
//                Notification.show("Not implemented in this demo");
//            }
//        });

        toolbar.addComponents(caption, tools);
        toolbar.setExpandRatio(caption, 1);
        toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);

        card.addComponents(toolbar, content);
        slot.addComponent(card);
        return slot;
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
		BeanContainer<String, Quote> quotes = new BeanContainer<String, Quote>(Quote.class);
		    
	    // Use the name property as the item ID of the bean
		quotes.setBeanIdProperty(Quote.PROPERTY_CHART_ID);
		
		
		//tradingService.getQuotes();
		
		quotes.addAll(tradingService.getQuotes());
		
		quoteSelector.setContainerDataSource(quotes);
	}

}
