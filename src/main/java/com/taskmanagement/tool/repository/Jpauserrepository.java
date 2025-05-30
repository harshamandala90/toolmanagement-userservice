package com.taskmanagement.tool.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmanagement.tool.model.User;

@Repository
public interface Jpauserrepository extends JpaRepository<User, UUID> {

}

