package com.codehouse.contacts.service;

import com.codehouse.contacts.domain.contact.AddContactCommand;
import com.codehouse.contacts.domain.contact.Gender;
import com.codehouse.contacts.exceptions.AlreadyExistsException;
import com.codehouse.contacts.exceptions.NotFoundException;
import com.codehouse.contacts.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ContactServiceTest {

    ContactRepository contactRepository;

    ContactService contactService;

    @BeforeEach
    void beforeEach() {
        contactRepository = Mockito.mock(ContactRepository.class);
        contactService = new ContactService(contactRepository);
    }

    @Test
    void should_throw_already_exists_exception() {
        Mockito.when(contactRepository.existsByPhoneNumber("+905060520077"))
                .thenReturn(true);
        assertThrows(AlreadyExistsException.class, () ->
                contactService.save(new AddContactCommand("jane",
                        "doe",
                        "+905060520077",
                        Gender.FEMALE
                )));
    }

    @Test
    void should_throw_not_found_exception() {
        var id = UUID.randomUUID();
        Mockito.when(contactRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () ->
                contactService.update(id,
                        new AddContactCommand("james",
                                "bond",
                                "5110125",
                                Gender.FEMALE)));
    }

}