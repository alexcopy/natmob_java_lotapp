package ru.m2mcom.natmob.repository.search;

import ru.m2mcom.natmob.domain.Nat_plays;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Nat_plays entity.
 */
public interface Nat_playsSearchRepository extends ElasticsearchRepository<Nat_plays, Long> {
}
