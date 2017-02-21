package ru.m2mcom.natmob.repository.search;

import ru.m2mcom.natmob.domain.BonusRanks;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BonusRanks entity.
 */
public interface BonusRanksSearchRepository extends ElasticsearchRepository<BonusRanks, Long> {
}
