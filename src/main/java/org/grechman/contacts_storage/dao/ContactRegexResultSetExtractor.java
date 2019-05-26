package org.grechman.contacts_storage.dao;

import org.grechman.contacts_storage.entity.Contact;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ContactRegexResultSetExtractor implements ResultSetExtractor<List<Contact>> {

    private List<Contact> contacts = new ArrayList<>();
    private final Pattern regexPattern;

    public ContactRegexResultSetExtractor(String regexPattern) {
        this.regexPattern = Pattern.compile(regexPattern == null ? "" : regexPattern);
    }

    @Override
    public List<Contact> extractData(ResultSet rs) throws SQLException, DataAccessException {
        while (rs.next()){
            String name = rs.getString("name");
            if(!regexPattern.matcher(name).find()){
                long id = rs.getLong("id");
                contacts.add(new Contact(id, name));
            }
            continue;
        }
        return contacts;
    }
}
