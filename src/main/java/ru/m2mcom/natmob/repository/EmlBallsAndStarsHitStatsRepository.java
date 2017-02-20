package ru.m2mcom.natmob.repository;

import ru.m2mcom.natmob.domain.EmlBallsAndStarsHitStats;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EmlBallsAndStarsHitStats entity.
 */
@SuppressWarnings("unused")
public interface EmlBallsAndStarsHitStatsRepository extends JpaRepository<EmlBallsAndStarsHitStats,Long> {

}
