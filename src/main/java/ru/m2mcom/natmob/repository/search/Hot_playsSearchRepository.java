package ru.m2mcom.natmob.repository.search;

import ru.m2mcom.natmob.domain.Hot_plays;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Hot_plays entity.
 */
public interface Hot_playsSearchRepository extends ElasticsearchRepository<Hot_plays, Long> {
}
