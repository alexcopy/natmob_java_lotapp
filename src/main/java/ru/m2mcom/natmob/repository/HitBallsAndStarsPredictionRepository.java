package ru.m2mcom.natmob.repository;

import ru.m2mcom.natmob.domain.HitBallsAndStarsPrediction;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HitBallsAndStarsPrediction entity.
 */
@SuppressWarnings("unused")
public interface HitBallsAndStarsPredictionRepository extends JpaRepository<HitBallsAndStarsPrediction,Long> {

}
