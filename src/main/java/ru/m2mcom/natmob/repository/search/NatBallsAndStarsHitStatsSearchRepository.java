package ru.m2mcom.natmob.repository.search;

import ru.m2mcom.natmob.domain.NatBallsAndStarsHitStats;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the NatBallsAndStarsHitStats entity.
 */
public interface NatBallsAndStarsHitStatsSearchRepository extends ElasticsearchRepository<NatBallsAndStarsHitStats, Long> {
}
