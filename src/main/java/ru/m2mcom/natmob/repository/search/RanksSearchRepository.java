package ru.m2mcom.natmob.repository.search;

import ru.m2mcom.natmob.domain.Ranks;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Ranks entity.
 */
public interface RanksSearchRepository extends ElasticsearchRepository<Ranks, Long> {
}
