package ru.m2mcom.lotmicro.repository;

import ru.m2mcom.lotmicro.domain.GameRule;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GameRule entity.
 */
@SuppressWarnings("unused")
public interface GameRuleRepository extends JpaRepository<GameRule,Long> {

}
