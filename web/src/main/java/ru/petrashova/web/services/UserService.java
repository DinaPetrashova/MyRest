package ru.petrashova.web.services;

import ru.petrashova.web.models.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User getUser (String login);

    Optional<User>getUserById(int id);
    void save(User user);

    void update(User updatedUser, int id);
    void delete(int id);
}