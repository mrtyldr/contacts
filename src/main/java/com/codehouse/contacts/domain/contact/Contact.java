package com.codehouse.contacts.domain.contact;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;
import java.util.UUID;

@Entity
public class Contact {
    @Id
    UUID id;

    String name;

    String surname;

    String fullName;
    Gender gender;

    String phoneNumber;

    public Contact() {
    }

    public Contact(UUID id,
                   String name,
                   String surname,
                   String fullName,
                   Gender gender,
                   String phoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    private Contact(Builder builder) {
        id = builder.id;
        name = builder.name;
        surname = builder.surname;
        fullName = builder.fullName;
        gender = builder.gender;
        phoneNumber = builder.phoneNumber;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getFullName() {
        return fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return id.equals(contact.id) && name.equals(contact.name) && Objects.equals(surname, contact.surname) && fullName.equals(contact.fullName) && gender == contact.gender && phoneNumber.equals(contact.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, fullName, gender, phoneNumber);
    }

    public void update(String name, String surname, Gender gender, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.fullName = name + " " + surname;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public static final class Builder {
        private UUID id;
        private String name;
        private String surname;
        private String fullName;
        private Gender gender;
        private String phoneNumber;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(UUID val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder surname(String val) {
            surname = val;
            return this;
        }

        public Builder fullName(String val) {
            fullName = val;
            return this;
        }

        public Builder gender(Gender val) {
            gender = val;
            return this;
        }

        public Builder phoneNumber(String val) {
            phoneNumber = val;
            return this;
        }

        public Contact build() {
            return new Contact(this);
        }
    }
}
