package com.substring.auth.oauth_app_backend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.substring.auth.oauth_app_backend.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {

}
