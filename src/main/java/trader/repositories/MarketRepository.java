package trader.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import trader.models.Market;

@Repository
@Transactional
public interface MarketRepository extends CrudRepository<Market, Long> {
	List<Market> findByName(String name);
}
