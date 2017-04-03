package ru.m2mcom.lotmicro.repository.search;

import ru.m2mcom.lotmicro.domain.GameRule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the GameRule entity.
 */
public interface GameRuleSearchRepository extends ElasticsearchRepository<GameRule, Long> {
}
