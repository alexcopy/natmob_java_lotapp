package ru.m2mcom.natmob.repository;

import ru.m2mcom.natmob.domain.Ranks;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Ranks entity.
 */
@SuppressWarnings("unused")
public interface RanksRepository extends JpaRepository<Ranks,Long> {

}
