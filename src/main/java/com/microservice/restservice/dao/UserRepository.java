package com.microservice.restservice.dao;

import com.microservice.restservice.dto.User;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Modifying(clearAutomatically=true, flushAutomatically = true)
    @Query(nativeQuery = true, value = "CREATE DATABASE IF NOT EXISTS db")
    int createDb() throws DataAccessException;

    @Transactional
    @Modifying(clearAutomatically=true, flushAutomatically = true)
    @Query(nativeQuery = true, value = "USE db")
    int useDb() throws DataAccessException;

    @Transactional
    @Modifying(clearAutomatically=true, flushAutomatically = true)
    @Query(nativeQuery = true, value = "DROP DATABASE IF EXISTS db")
    int deleteDb() throws DataAccessException;

    String createUserQuery = "CREATE TABLE user (id int NOT NULL AUTO_INCREMENT, uuid varchar(255) NOT NULL, name varchar(255) NOT NULL, access_level varchar(255) NOT NULL, password varchar(255) NOT NULL, email varchar(255) NOT NULL, token_id varchar(255), PRIMARY KEY (id))";

    @Transactional
    @Modifying(clearAutomatically=true, flushAutomatically = true)
    @Query(nativeQuery = true, value = createUserQuery)
    int createUserTable() throws DataAccessException;

    String createMessageQuery = "CREATE TABLE user (id int NOT NULL AUTO_INCREMENT, uuid varchar(255) NOT NULL, name varchar(255) NOT NULL, access_level varchar(255) NOT NULL, password varchar(255) NOT NULL, token_id varchar(255), PRIMARY KEY (id))";

    @Transactional
    @Modifying(clearAutomatically=true, flushAutomatically = true)
    @Query(nativeQuery = true, value = createMessageQuery)
    int createMessageTable() throws DataAccessException;

    User save(User user) throws DataAccessException;

    @Transactional(readOnly = true)
    User findByEmail(String email) throws DataAccessException;

}
