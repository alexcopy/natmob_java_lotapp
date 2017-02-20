package ru.m2mcom.natmob.repository.search;

import ru.m2mcom.natmob.domain.HitBallsAndStarsPrediction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the HitBallsAndStarsPrediction entity.
 */
public interface HitBallsAndStarsPredictionSearchRepository extends ElasticsearchRepository<HitBallsAndStarsPrediction, Long> {
}
