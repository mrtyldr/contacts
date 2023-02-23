package com.codehouse.contacts.domain.contact;

import java.util.UUID;

public record ContactResponse(
        UUID id,
        String name,
        String surname,
        String phoneNumber
) {

}
