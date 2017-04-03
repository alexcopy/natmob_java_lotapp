package ru.m2mcom.lotmicro.repository.search;

import ru.m2mcom.lotmicro.domain.DblTrplStats;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DblTrplStats entity.
 */
public interface DblTrplStatsSearchRepository extends ElasticsearchRepository<DblTrplStats, Long> {
}
