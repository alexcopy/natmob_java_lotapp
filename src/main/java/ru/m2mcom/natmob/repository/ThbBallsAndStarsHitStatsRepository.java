package ru.m2mcom.natmob.repository;

import ru.m2mcom.natmob.domain.ThbBallsAndStarsHitStats;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ThbBallsAndStarsHitStats entity.
 */
@SuppressWarnings("unused")
public interface ThbBallsAndStarsHitStatsRepository extends JpaRepository<ThbBallsAndStarsHitStats,Long> {

}
