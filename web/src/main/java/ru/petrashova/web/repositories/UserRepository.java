package ru.petrashova.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.petrashova.web.models.User;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository <User, Integer> {
    @Query("select u from User u join fetch u.roles where u.login=:login")
    Optional<User> findByLogin(String login);

    @Override
    Optional<User> findById(Integer integer);
}

