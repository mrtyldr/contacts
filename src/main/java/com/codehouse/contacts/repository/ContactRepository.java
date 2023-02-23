package com.codehouse.contacts.repository;

import com.codehouse.contacts.domain.contact.Contact;
import com.codehouse.contacts.domain.contact.ContactResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {
    boolean existsByPhoneNumber(String phoneNumber);
    @Query("""
            select c
            from Contact c
            order by c.name
            """)
    List<Contact> findAllSortedByName();
    @Query("""
            select c 
            from Contact c
            where c.fullName ilike :query
            """)
    List<Contact> search(String query);
}
