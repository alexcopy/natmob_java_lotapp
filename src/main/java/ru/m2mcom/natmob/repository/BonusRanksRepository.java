package ru.m2mcom.natmob.repository;

import ru.m2mcom.natmob.domain.BonusRanks;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BonusRanks entity.
 */
@SuppressWarnings("unused")
public interface BonusRanksRepository extends JpaRepository<BonusRanks,Long> {

}
