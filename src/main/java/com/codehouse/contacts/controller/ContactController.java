package com.codehouse.contacts.controller;

import com.codehouse.contacts.domain.contact.AddContactCommand;
import com.codehouse.contacts.domain.contact.ContactResponse;
import com.codehouse.contacts.response.Response;
import com.codehouse.contacts.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contacts")
    Response<List<ContactResponse>> getContacts(){
        return Response.of(contactService.getContacts());
    }

    @PostMapping("/contacts")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addContact(@RequestBody AddContactCommand command){
        contactService.save(command);
    }

    @PutMapping("/contacts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateContact(@PathVariable UUID id, @RequestBody AddContactCommand command){
        contactService.update(id,command);
    }

    @DeleteMapping("/contacts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteContact(@PathVariable UUID id){
        contactService.delete(id);
    }

    @GetMapping("/contacts/search")
    Response<List<ContactResponse>> searchContacts(String searchQuery){
        return Response.of(contactService.search(searchQuery));
    }

}
