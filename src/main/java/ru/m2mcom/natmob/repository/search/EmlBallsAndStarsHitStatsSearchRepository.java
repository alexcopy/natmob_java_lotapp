package ru.m2mcom.natmob.repository.search;

import ru.m2mcom.natmob.domain.EmlBallsAndStarsHitStats;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EmlBallsAndStarsHitStats entity.
 */
public interface EmlBallsAndStarsHitStatsSearchRepository extends ElasticsearchRepository<EmlBallsAndStarsHitStats, Long> {
}
