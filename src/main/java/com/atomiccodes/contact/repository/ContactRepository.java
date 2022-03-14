package com.atomiccodes.contact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.atomiccodes.contact.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query(value = "SELECT * FROM contact c WHERE c.email = ?1", nativeQuery = true)
    List<Contact> findByEmail(String email);
}
