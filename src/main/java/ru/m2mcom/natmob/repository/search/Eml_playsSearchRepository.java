package ru.m2mcom.natmob.repository.search;

import ru.m2mcom.natmob.domain.Eml_plays;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Eml_plays entity.
 */
public interface Eml_playsSearchRepository extends ElasticsearchRepository<Eml_plays, Long> {
}
