package com.phonebook.controller;

import com.phonebook.dto.ContactDto;
import com.phonebook.model.Contact;
import com.phonebook.service.IContactService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@CrossOrigin("*")
public class ContactController {

    private final IContactService contactService;

    public ContactController(IContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public List<Contact> getAll() {
        return contactService.getAllContacts();
    }

    @GetMapping("/{id}")
    public Contact getById(@PathVariable Long id) {
        return contactService.getContactById(id);
    }

    @PostMapping
    public Contact create(@Valid @RequestBody ContactDto dto) {
        return contactService.createContact(dto);
    }

    @PutMapping("/{id}")
    public Contact update(@PathVariable Long id, @Valid @RequestBody ContactDto dto) {
        return contactService.updateContact(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        contactService.deleteContact(id);
    }
}