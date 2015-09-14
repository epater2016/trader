package trader;

import java.io.IOException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.fx.FxSymbols;

@Component
public class ScheduledTasks {
	
	@Scheduled(cron="0 */15 6-20 * * MON-FRI", zone = "Etc/UTC")
	void YahooFetch() {
		try {
			Stock usdeur = YahooFinance.get(FxSymbols.EURUSD);
			System.out.println(usdeur);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
