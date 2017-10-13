package io.practice.restAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import io.practice.restAPI.entities.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {

}
