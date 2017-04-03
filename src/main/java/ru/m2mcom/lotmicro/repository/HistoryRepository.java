package ru.m2mcom.lotmicro.repository;

import ru.m2mcom.lotmicro.domain.History;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the History entity.
 */
@SuppressWarnings("unused")
public interface HistoryRepository extends JpaRepository<History,Long> {

}
