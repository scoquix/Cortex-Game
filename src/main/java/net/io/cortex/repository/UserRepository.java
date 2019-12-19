package net.io.cortex.repository;

import net.io.cortex.model.Authentication;
import net.io.cortex.model.Registration;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<String> findAll();

    Optional<String> findById(String id);

    Optional<String> findByName(String id);

    boolean delete(Authentication user);

    boolean create(Registration user);

    boolean update(Authentication oldUser, Authentication newUser);
}
