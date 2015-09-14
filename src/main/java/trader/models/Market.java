package trader.models;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "markets")
public class Market {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Version
    private Long version;
	
	@NotNull
	@Column(unique = true)
	private String name;
	
	@NotNull
	private Integer openDay;
	
	@NotNull
	private Integer closeDay;
	
	@NotNull
	@Temporal(TemporalType.TIME) 
	private Date openHour;
	
	@NotNull
	@Temporal(TemporalType.TIME) 
	private Date closeHour;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "market", cascade = CascadeType.ALL)
	@OnDelete(action=OnDeleteAction.CASCADE)
    private List<MarketClosedDay> closedDays;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "market", cascade = CascadeType.ALL)
    private List<Quote> quotes;

	public Market(String name, Integer openDay, Integer closeDay, Date openHour, Date closeHour) {
		super();
		this.name = name;
		this.openDay = openDay;
		this.closeDay = closeDay;
		this.openHour = openHour;
		this.closeHour = closeHour;
	}
	
	public Market() {
		
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOpenDay() {
		return openDay;
	}

	public void setOpenDay(Integer openDay) {
		this.openDay = openDay;
	}

	public Integer getCloseDay() {
		return closeDay;
	}

	public void setCloseDay(Integer closeDay) {
		this.closeDay = closeDay;
	}

	public Date getOpenHour() {
		return openHour;
	}

	public void setOpenHour(Date openHour) {
		this.openHour = openHour;
	}

	public Date getCloseHour() {
		return closeHour;
	}

	public void setCloseHour(Date closeHour) {
		this.closeHour = closeHour;
	}

	public List<MarketClosedDay> getClosedDays() {
		return closedDays;
	}

	public void setClosedDays(List<MarketClosedDay> closedDays) {
		this.closedDays = closedDays;
	}
	
	
	
}
