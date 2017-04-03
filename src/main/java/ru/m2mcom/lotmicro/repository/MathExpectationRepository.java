package ru.m2mcom.lotmicro.repository;

import ru.m2mcom.lotmicro.domain.MathExpectation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MathExpectation entity.
 */
@SuppressWarnings("unused")
public interface MathExpectationRepository extends JpaRepository<MathExpectation,Long> {

}
