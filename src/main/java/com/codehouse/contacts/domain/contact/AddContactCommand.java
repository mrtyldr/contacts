package com.codehouse.contacts.domain.contact;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddContactCommand(
        @NotNull
        @Min(2)
        @Max(25)
        String name,
        @NotNull
        @Min(2)
        @Max(25)
        String surname,
        @NotNull
        String phoneNumber,
        @NotNull
        Gender gender
) {
}
