package ru.m2mcom.natmob.repository.search;

import ru.m2mcom.natmob.domain.Thb_history;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Thb_history entity.
 */
public interface Thb_historySearchRepository extends ElasticsearchRepository<Thb_history, Long> {
}
