package ru.m2mcom.lotmicro.repository;

import ru.m2mcom.lotmicro.domain.Rank;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Rank entity.
 */
@SuppressWarnings("unused")
public interface RankRepository extends JpaRepository<Rank,Long> {

}
