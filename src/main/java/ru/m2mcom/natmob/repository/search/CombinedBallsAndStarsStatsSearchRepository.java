package ru.m2mcom.natmob.repository.search;

import ru.m2mcom.natmob.domain.CombinedBallsAndStarsStats;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CombinedBallsAndStarsStats entity.
 */
public interface CombinedBallsAndStarsStatsSearchRepository extends ElasticsearchRepository<CombinedBallsAndStarsStats, Long> {
}
