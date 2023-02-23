package com.codehouse.contacts.controller;

import com.codehouse.contacts.domain.contact.Contact;
import com.codehouse.contacts.domain.contact.Gender;
import com.codehouse.contacts.repository.ContactRepository;
import com.codehouse.contacts.service.ContactService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ContactControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    ContactService contactService;

    @Test
    void should_add_contact() throws Exception {
        var request = post("/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                             "name": "jane",
                             "surname": "doe",
                             "phoneNumber": "+905065110125",
                             "gender": "FEMALE"
                        }
                        """);

        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        assertThat(contactRepository.count()).isGreaterThan(0);
        assertThat(contactService.getContacts()).isNotEmpty();
        assertThat(contactRepository.existsByPhoneNumber("+905065110125")).isTrue();
        contactRepository.deleteAll();
    }

    @Test
    void should_get_contacts() throws Exception {
        var contacts = add_contacts();
        mockMvc.perform(get("/contacts"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                         {
                         "result":[
                         {
                         "id":"%s",
                         "name":"jane",
                         "surname":"doe",
                         "phoneNumber":"+905065110125"
                         },
                         {
                         "id":"%s",
                         "name":"john",
                         "surname":"doe",
                         "phoneNumber":"+905065110126"
                         }
                        ]
                        }
                          """.formatted(contacts.get(0).getId(), contacts.get(1).getId())));

        contactRepository.deleteAll();

    }

    @Test
    void should_update_contact() throws Exception {
        var contactId = add_contacts().get(0).getId();
        var request = put("/contacts/{id}", contactId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                          {
                             "name": "james",
                             "surname": "bond",
                             "phoneNumber": "+905065110007",
                             "gender": "FEMALE"
                             }
                        """);

        mockMvc.perform(request)
                .andExpect(status().isNoContent());
        var updatedContact = contactRepository.findById(contactId).orElseThrow();
        assertThat(updatedContact.getName()).isEqualTo("james");
        assertThat(updatedContact.getSurname()).isEqualTo("bond");
        assertThat(updatedContact.getPhoneNumber()).isEqualTo("+905065110007");
        assertThat(updatedContact.getFullName()).isEqualTo("james bond");

        contactRepository.deleteAll();
    }


    private List<Contact> add_contacts() {
        var jane = Contact.Builder.builder()
                .id(UUID.randomUUID())
                .name("jane")
                .surname("doe")
                .fullName("jane doe")
                .gender(Gender.FEMALE)
                .phoneNumber("+905065110125")
                .build();
        var joe = Contact.Builder.builder()
                .id(UUID.randomUUID())
                .name("john")
                .surname("doe")
                .fullName("john doe")
                .gender(Gender.MALE)
                .phoneNumber("+905065110126")
                .build();
        return contactRepository.saveAll(List.of(jane, joe));
    }

    @Test
    void should_delete_contact() throws Exception {
        var contact = add_contacts().get(0);
        assertThat(contactRepository.findById(contact.getId())).isNotEmpty();

        mockMvc.perform(delete("/contacts/{id}", contact.getId()));
        assertThat(contactRepository.findById(contact.getId())).isEmpty();
    }

    @Test
    void should_throw_not_found_exception() throws Exception {
        mockMvc.perform(delete("/contacts/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(content().json(
                        """
                                {
                                "code": 404,
                                "message":"contact not found",
                                "cause":"Required item cannot be found in db!"
                                }
                                """
                ));
    }
    @Test
    void should_search_in_contacts() throws Exception {
        var id = add_contacts().get(0).getId();
        mockMvc.perform(get("/contacts/search")
                        .param("searchQuery", "ane"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                        "result": [
                          {
                          "id":"%s",
                         "name":"jane",
                         "surname":"doe",
                         "phoneNumber":"+905065110125"
                         }
                        ]
                        }
                        """.formatted(id)));
    }


}