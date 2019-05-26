package org.grechman.contacts_storage.controller;

import org.grechman.contacts_storage.entity.Contact;
import org.grechman.contacts_storage.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "/contact", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Contact>> getContacts(@RequestParam("nameFilter") String nameFilter){
        List<Contact> contacts = contactService.getContactsIgnoringMatchedByFilter(nameFilter);
        return ResponseEntity.ok(contacts);
    }
}
