package com.andresouza.dscatalog.repositories;

import com.andresouza.dscatalog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepostitory extends JpaRepository<User, Long> {
}