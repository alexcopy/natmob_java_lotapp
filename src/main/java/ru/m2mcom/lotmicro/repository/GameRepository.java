package ru.m2mcom.lotmicro.repository;

import ru.m2mcom.lotmicro.domain.Game;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Game entity.
 */
@SuppressWarnings("unused")
public interface GameRepository extends JpaRepository<Game,Long> {

}
