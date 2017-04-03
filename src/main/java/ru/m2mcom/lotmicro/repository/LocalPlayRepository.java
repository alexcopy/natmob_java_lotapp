package ru.m2mcom.lotmicro.repository;

import ru.m2mcom.lotmicro.domain.LocalPlay;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LocalPlay entity.
 */
@SuppressWarnings("unused")
public interface LocalPlayRepository extends JpaRepository<LocalPlay,Long> {

}
