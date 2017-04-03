package ru.m2mcom.lotmicro.repository;

import ru.m2mcom.lotmicro.domain.DblTrplStats;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DblTrplStats entity.
 */
@SuppressWarnings("unused")
public interface DblTrplStatsRepository extends JpaRepository<DblTrplStats,Long> {

}
