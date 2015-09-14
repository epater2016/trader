package trader.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "quotes")
public class Quote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Version
    private Long version;
	
	@NotNull
	@Column(unique = true)
	private String symbol;
	
	@NotNull
	private String name;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private QuoteCategory category;
	
	@ManyToOne
	private Market market;
	
	@NotNull
	private String chartSrc;
	
	@NotNull
	private String quoteSrc;
	
	@NotNull
	private Integer payout;
	
	@NotNull
	private Boolean enabled;

	public Quote(String symbol, String name, QuoteCategory category, Market market, String chartSrc, String quoteSrc,
			Integer payout) {
		super();
		this.symbol = symbol;
		this.name = name;
		this.category = category;
		this.market = market;
		this.chartSrc = chartSrc;
		this.quoteSrc = quoteSrc;
		this.payout = payout;
		this.enabled = true;
	}
	
	public Quote() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public QuoteCategory getCategory() {
		return category;
	}

	public void setCategory(QuoteCategory category) {
		this.category = category;
	}

	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}

	public String getChartSrc() {
		return chartSrc;
	}

	public void setChartSrc(String chartSrc) {
		this.chartSrc = chartSrc;
	}

	public String getQuoteSrc() {
		return quoteSrc;
	}

	public void setQuoteSrc(String quoteSrc) {
		this.quoteSrc = quoteSrc;
	}

	public Integer getPayout() {
		return payout;
	}

	public void setPayout(Integer payout) {
		this.payout = payout;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
