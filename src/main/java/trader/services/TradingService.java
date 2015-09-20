package trader.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import trader.models.Quote;
import trader.repositories.MarketRepository;
import trader.repositories.QuoteRepository;

@Service
public class TradingService {
    @Autowired
    private MarketRepository markets;
    
    @Autowired
    private QuoteRepository quotes;
    
    @Transactional(readOnly=true)
    public List<Quote> getQuotes() {
    	return quotes.findByEnabled(true);
    }
}
