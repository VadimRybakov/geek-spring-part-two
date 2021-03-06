package ru.geekbrains.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
