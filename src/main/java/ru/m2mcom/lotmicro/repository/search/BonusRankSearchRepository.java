package ru.m2mcom.lotmicro.repository.search;

import ru.m2mcom.lotmicro.domain.BonusRank;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BonusRank entity.
 */
public interface BonusRankSearchRepository extends ElasticsearchRepository<BonusRank, Long> {
}
