package net.io.cortex.repository;

import net.io.cortex.model.Riddle;

import java.util.List;
import java.util.Optional;

public interface RiddleRepository {
    boolean create(Riddle riddle);

    boolean update(Riddle oldRiddle, Riddle newRiddle);

    boolean delete(Riddle riddle);

    Optional<String> findByName(String name);

    List<String> findAll();
}
