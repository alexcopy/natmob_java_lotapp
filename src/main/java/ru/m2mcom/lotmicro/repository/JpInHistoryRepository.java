package ru.m2mcom.lotmicro.repository;

import ru.m2mcom.lotmicro.domain.JpInHistory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the JpInHistory entity.
 */
@SuppressWarnings("unused")
public interface JpInHistoryRepository extends JpaRepository<JpInHistory,Long> {

}
