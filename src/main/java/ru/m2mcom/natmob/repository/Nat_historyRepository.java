package ru.m2mcom.natmob.repository;

import ru.m2mcom.natmob.domain.Nat_history;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Nat_history entity.
 */
@SuppressWarnings("unused")
public interface Nat_historyRepository extends JpaRepository<Nat_history,Long> {

}
