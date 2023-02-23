package com.codehouse.contacts.service;

import com.codehouse.contacts.domain.contact.AddContactCommand;
import com.codehouse.contacts.domain.contact.Contact;
import com.codehouse.contacts.domain.contact.ContactResponse;
import com.codehouse.contacts.exceptions.AlreadyExistsException;
import com.codehouse.contacts.exceptions.NotFoundException;
import com.codehouse.contacts.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<ContactResponse> getContacts() {
        return contactRepository.findAllSortedByName()
                .stream().map(this::toContactDto)
                .toList();
    }

    private ContactResponse toContactDto(Contact contact) {
        return new ContactResponse(
                contact.getId(),
                contact.getName(),
                contact.getSurname(),
                contact.getPhoneNumber()
        );
    }

    @Transactional
    public void save(AddContactCommand command) {
        var phoneNumber = command.phoneNumber().replaceAll(" ", "");

        if (contactRepository.existsByPhoneNumber(phoneNumber))
            throw new AlreadyExistsException("Phone Number already exists!");

        var contact = Contact.Builder.builder()
                .id(UUID.randomUUID())
                .name(command.name())
                .surname(command.surname())
                .fullName(command.name() + " " + command.surname())
                .phoneNumber(phoneNumber)
                .gender(command.gender())
                .build();
        contactRepository.save(contact);
    }

    @Transactional
    public void update(UUID id, AddContactCommand command) {
        var contact = contactRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contact with id: %s not found!".formatted(id)));
        contact.update(command.name(),
                command.surname(),
                command.gender(),
                command.phoneNumber());
        contactRepository.save(contact);
    }

    @Transactional
    public void delete(UUID id) {
        var contact = contactRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("contact not found"));

        contactRepository.delete(contact);
    }

    public List<ContactResponse> search(String seacrhQuery) {
        var query = "%" + seacrhQuery + "%";
        return contactRepository.search(query)
                .stream().map(this::toContactDto)
                .toList();
    }
}
