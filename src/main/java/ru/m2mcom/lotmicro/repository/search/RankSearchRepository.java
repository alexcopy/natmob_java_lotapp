package ru.m2mcom.lotmicro.repository.search;

import ru.m2mcom.lotmicro.domain.Rank;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Rank entity.
 */
public interface RankSearchRepository extends ElasticsearchRepository<Rank, Long> {
}
