package ru.m2mcom.lotmicro.repository;

import ru.m2mcom.lotmicro.domain.LocalGenerated;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LocalGenerated entity.
 */
@SuppressWarnings("unused")
public interface LocalGeneratedRepository extends JpaRepository<LocalGenerated,Long> {

}
