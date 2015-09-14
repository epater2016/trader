package trader.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import trader.models.Market;
import trader.models.MarketClosedDay;

@Repository
@Transactional
public interface MarketClosedDayRepository extends CrudRepository<MarketClosedDay, Long> {
	List<MarketClosedDay> findByMarket(Market market);
}
