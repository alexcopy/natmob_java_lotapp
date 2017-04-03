package ru.m2mcom.lotmicro.repository.search;

import ru.m2mcom.lotmicro.domain.History;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the History entity.
 */
public interface HistorySearchRepository extends ElasticsearchRepository<History, Long> {
}
