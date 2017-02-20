package ru.m2mcom.natmob.repository;

import ru.m2mcom.natmob.domain.NatBallsAndStarsHitStats;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the NatBallsAndStarsHitStats entity.
 */
@SuppressWarnings("unused")
public interface NatBallsAndStarsHitStatsRepository extends JpaRepository<NatBallsAndStarsHitStats,Long> {

}
