package ru.m2mcom.lotmicro.repository;

import ru.m2mcom.lotmicro.domain.WebGenerated;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the WebGenerated entity.
 */
@SuppressWarnings("unused")
public interface WebGeneratedRepository extends JpaRepository<WebGenerated,Long> {

}
