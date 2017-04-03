package ru.m2mcom.lotmicro.repository.search;

import ru.m2mcom.lotmicro.domain.LocalPlay;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LocalPlay entity.
 */
public interface LocalPlaySearchRepository extends ElasticsearchRepository<LocalPlay, Long> {
}
