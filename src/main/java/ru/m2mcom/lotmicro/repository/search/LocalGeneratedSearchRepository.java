package ru.m2mcom.lotmicro.repository.search;

import ru.m2mcom.lotmicro.domain.LocalGenerated;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LocalGenerated entity.
 */
public interface LocalGeneratedSearchRepository extends ElasticsearchRepository<LocalGenerated, Long> {
}
