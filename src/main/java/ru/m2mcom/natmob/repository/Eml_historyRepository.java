package ru.m2mcom.natmob.repository;

import ru.m2mcom.natmob.domain.Eml_history;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Eml_history entity.
 */
@SuppressWarnings("unused")
public interface Eml_historyRepository extends JpaRepository<Eml_history,Long> {

}
