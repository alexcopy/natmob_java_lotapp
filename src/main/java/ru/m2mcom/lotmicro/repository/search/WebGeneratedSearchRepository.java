package ru.m2mcom.lotmicro.repository.search;

import ru.m2mcom.lotmicro.domain.WebGenerated;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the WebGenerated entity.
 */
public interface WebGeneratedSearchRepository extends ElasticsearchRepository<WebGenerated, Long> {
}
