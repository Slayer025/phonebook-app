package com.phonebook.service;

import com.phonebook.model.Contact;
import com.phonebook.dto.ContactDto;

import java.util.List;

public interface IContactService {

    List<Contact> getAllContacts();

    Contact getContactById(Long id);

    Contact createContact(ContactDto dto);

    Contact updateContact(Long id, ContactDto dto);

    void deleteContact(Long id);
}