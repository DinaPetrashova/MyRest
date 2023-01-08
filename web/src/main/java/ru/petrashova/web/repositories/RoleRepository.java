package ru.petrashova.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.petrashova.web.models.Role;


@Repository
public interface RoleRepository extends JpaRepository <Role, Long> {

}
