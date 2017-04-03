package ru.m2mcom.lotmicro.repository.search;

import ru.m2mcom.lotmicro.domain.MathExpectation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MathExpectation entity.
 */
public interface MathExpectationSearchRepository extends ElasticsearchRepository<MathExpectation, Long> {
}
