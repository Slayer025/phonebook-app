package com.phonebook.service;

import com.phonebook.dto.ContactDto;
import com.phonebook.model.Contact;
import com.phonebook.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService implements IContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Contact getContactById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    @Override
    public Contact createContact(ContactDto dto) {
        Contact contact = new Contact();
        contact.setName(dto.getName());
        contact.setPhoneNumber(dto.getPhoneNumber());
        contact.setEmail(dto.getEmail());
        contact.setAddress(dto.getAddress());

        return contactRepository.save(contact);
    }

    @Override
    public Contact updateContact(Long id, ContactDto dto) {
        Contact contact = getContactById(id);

        contact.setName(dto.getName());
        contact.setPhoneNumber(dto.getPhoneNumber());
        contact.setEmail(dto.getEmail());
        contact.setAddress(dto.getAddress());

        return contactRepository.save(contact);
    }

    @Override
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}