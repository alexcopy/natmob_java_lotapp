package ru.m2mcom.natmob.repository.search;

import ru.m2mcom.natmob.domain.Eml_history;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Eml_history entity.
 */
public interface Eml_historySearchRepository extends ElasticsearchRepository<Eml_history, Long> {
}
