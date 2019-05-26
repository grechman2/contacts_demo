package org.grechman.contacts_storage.loader;

import org.grechman.contacts_storage.entity.Contact;
import org.grechman.contacts_storage.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//@Component
public class ContactsLoader implements CommandLineRunner {

    private final ContactService contactService;
    private final DataSourceTransactionManager txManager;

    private static final Random randomGenerator = new Random();
    private static final String str = "абвгдежзийдклмнопрст";

    @Autowired
    public ContactsLoader(ContactService contactService,
                          DataSourceTransactionManager txManager) {
        this.contactService = contactService;
        this.txManager = txManager;
    }


    @Override
    public void run(String... args) throws Exception {

        final String str = "абвгдежзийдклмнопрст";
        int numberOfContactsToCreate = 1000000;
        int batchSize = 200;

        List<Contact> contactsBatch = new ArrayList<>();

        TransactionStatus transaction = txManager.getTransaction(new DefaultTransactionDefinition());
        for(int i = 1; i <= numberOfContactsToCreate; i++){

            Contact contact = new Contact(generateRandomString(4)+ " " + generateRandomString(5));
            contactsBatch.add(contact);

            if(i % batchSize == 0){
                contactService.addContacts(contactsBatch);
                contactsBatch.clear();
                txManager.commit(transaction);
                transaction = txManager.getTransaction(new DefaultTransactionDefinition());
            }
        }
        txManager.commit(transaction);
    }

    private String generateRandomString(int numberOfSymbols){
        char[] ch  = str.toCharArray();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i< numberOfSymbols; i++ ){
            builder.append(ch[randomGenerator.nextInt(ch.length)]);
        }
        return builder.toString();
    }
}
