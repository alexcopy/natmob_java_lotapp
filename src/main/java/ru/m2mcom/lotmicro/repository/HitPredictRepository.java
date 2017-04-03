package ru.m2mcom.lotmicro.repository;

import ru.m2mcom.lotmicro.domain.HitPredict;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HitPredict entity.
 */
@SuppressWarnings("unused")
public interface HitPredictRepository extends JpaRepository<HitPredict,Long> {

}
