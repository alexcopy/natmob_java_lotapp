package ru.m2mcom.lotmicro.repository;

import ru.m2mcom.lotmicro.domain.BonusRank;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BonusRank entity.
 */
@SuppressWarnings("unused")
public interface BonusRankRepository extends JpaRepository<BonusRank,Long> {

}
