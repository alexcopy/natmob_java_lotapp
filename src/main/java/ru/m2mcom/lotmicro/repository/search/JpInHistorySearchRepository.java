package ru.m2mcom.lotmicro.repository.search;

import ru.m2mcom.lotmicro.domain.JpInHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the JpInHistory entity.
 */
public interface JpInHistorySearchRepository extends ElasticsearchRepository<JpInHistory, Long> {
}
