package ru.m2mcom.lotmicro.repository.search;

import ru.m2mcom.lotmicro.domain.Game;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Game entity.
 */
public interface GameSearchRepository extends ElasticsearchRepository<Game, Long> {
}
