package ru.m2mcom.natmob.repository.search;

import ru.m2mcom.natmob.domain.ThbBallsAndStarsHitStats;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ThbBallsAndStarsHitStats entity.
 */
public interface ThbBallsAndStarsHitStatsSearchRepository extends ElasticsearchRepository<ThbBallsAndStarsHitStats, Long> {
}
