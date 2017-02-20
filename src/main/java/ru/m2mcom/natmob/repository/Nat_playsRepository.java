package ru.m2mcom.natmob.repository;

import ru.m2mcom.natmob.domain.Nat_plays;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Nat_plays entity.
 */
@SuppressWarnings("unused")
public interface Nat_playsRepository extends JpaRepository<Nat_plays,Long> {

}
