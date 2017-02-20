package ru.m2mcom.natmob.repository;

import ru.m2mcom.natmob.domain.CombinedBallsAndStarsStats;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CombinedBallsAndStarsStats entity.
 */
@SuppressWarnings("unused")
public interface CombinedBallsAndStarsStatsRepository extends JpaRepository<CombinedBallsAndStarsStats,Long> {

}
