package ru.m2mcom.natmob.repository;

import ru.m2mcom.natmob.domain.Hot_plays;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Hot_plays entity.
 */
@SuppressWarnings("unused")
public interface Hot_playsRepository extends JpaRepository<Hot_plays,Long> {

}
