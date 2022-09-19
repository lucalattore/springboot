package com.waveinformatica.demo.dto;

import java.util.Optional;

public class EditablePersonDTO {
    private Optional<String> firstName;
    private Optional<String> lastName;

    public Optional<String> getFirstName() {
        return firstName;
    }

    public void setFirstName(Optional<String> firstName) {
        this.firstName = firstName;
    }

    public Optional<String> getLastName() {
        return lastName;
    }

    public void setLastName(Optional<String> lastName) {
        this.lastName = lastName;
    }
}
