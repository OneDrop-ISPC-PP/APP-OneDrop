package com.pisis.oneDrop.auth;

import com.pisis.oneDrop.auth.entities.Role;
import com.pisis.oneDrop.auth.entities.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername (String username);
    Optional<User> findByEmail (String email);
    Optional<User> findByDni (String dni);
    Boolean existsByUsername (String username);
    Boolean existsByEmail (String email);
    Boolean existsByDni (String dni);


    Page<User> findAllByDniContains (String dni, Pageable pageable);
    Page<User> findAllByRoleAndDniContains (Role role, String dni, Pageable pageable);

    @Query("SELECT u FROM users u WHERE ( CAST(u.role AS string) LIKE %:role% AND u.apellido LIKE %:fullName% OR u.nombre LIKE %:fullName%)")
    Page<User> getAllByRoleAndFullName (Role role, String fullName, Pageable pageable);
    @Query("SELECT u FROM users u WHERE ( u.apellido LIKE %:fullName% OR u.nombre LIKE %:fullName%)")
    Page<User> getAllByFullName (String fullName, Pageable pageable);
    Page<User> findAllByRole (Role role, Pageable pageable);

}
