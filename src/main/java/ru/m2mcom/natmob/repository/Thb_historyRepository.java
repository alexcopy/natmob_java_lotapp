package ru.m2mcom.natmob.repository;

import ru.m2mcom.natmob.domain.Thb_history;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Thb_history entity.
 */
@SuppressWarnings("unused")
public interface Thb_historyRepository extends JpaRepository<Thb_history,Long> {

}
