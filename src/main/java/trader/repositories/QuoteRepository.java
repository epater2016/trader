package trader.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import trader.models.Market;
import trader.models.Quote;

@Repository
@Transactional
public interface QuoteRepository extends CrudRepository<Quote, Long> {
	
	List<Quote> findByEnabled(Boolean enabled);
	
	Quote findBySymbol(String symbol);
	
	List<Quote> findByMarket(Market market);
}
