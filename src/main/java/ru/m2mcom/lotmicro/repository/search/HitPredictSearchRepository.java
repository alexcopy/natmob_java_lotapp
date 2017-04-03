package ru.m2mcom.lotmicro.repository.search;

import ru.m2mcom.lotmicro.domain.HitPredict;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the HitPredict entity.
 */
public interface HitPredictSearchRepository extends ElasticsearchRepository<HitPredict, Long> {
}
