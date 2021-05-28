package org.resmed.game.repository;

import org.resmed.game.model.RockPaperScissors.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}

