package ru.m2mcom.natmob.repository.search;

import ru.m2mcom.natmob.domain.Nat_history;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Nat_history entity.
 */
public interface Nat_historySearchRepository extends ElasticsearchRepository<Nat_history, Long> {
}
