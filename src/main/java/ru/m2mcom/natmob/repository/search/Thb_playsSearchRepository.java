package ru.m2mcom.natmob.repository.search;

import ru.m2mcom.natmob.domain.Thb_plays;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Thb_plays entity.
 */
public interface Thb_playsSearchRepository extends ElasticsearchRepository<Thb_plays, Long> {
}
