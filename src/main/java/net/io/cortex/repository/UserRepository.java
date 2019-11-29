package net.io.cortex.repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<String> findAll();

    Optional<String> findById(String id);

    Optional<String> findByName(String id);

    void delete(String id);

    void create(String id);

    void update(String id);
}
