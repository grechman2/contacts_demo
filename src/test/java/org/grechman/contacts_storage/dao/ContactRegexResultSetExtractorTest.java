package org.grechman.contacts_storage.dao;

import org.grechman.contacts_storage.entity.Contact;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ContactRegexResultSetExtractorTest {

    ContactRegexResultSetExtractor extractor;

    @Mock
    ResultSet resultSet;

    @Test
    public void ifFilterMatchToContactNameValueIsNotPresent() throws SQLException {

        // arrange
        extractor = new ContactRegexResultSetExtractor("^Том.*$");
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getString("name")).thenReturn("Том Хэнкс");

        // act
        List<Contact> result = extractor.extractData(resultSet);

        // assert
        assertThat(result).isEmpty();

    }

    @Test
    public void ifFilterDoesntMatchToContactNameValueIsPresent() throws SQLException {

        // arrange
        extractor = new ContactRegexResultSetExtractor("^ом.*$");
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getString("name")).thenReturn("Том Хэнкс");

        // act
        List<Contact> result = extractor.extractData(resultSet);

        // assert
        assertThat(result.size()).isEqualTo(1);
    }

    @Test(expected = PatternSyntaxException.class)
    public void ifFilterNotNotValidRegexPatterThrowsException() throws SQLException {

        // arrange
        extractor = new ContactRegexResultSetExtractor("[^]"); // not valid regex
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getString("name")).thenReturn("Том Хэнкс");

        // act
        extractor.extractData(resultSet);
    }

    @Test()
    public void ifFilterIsEmptyItMatchesNothing() throws SQLException {

        // arrange
        extractor = new ContactRegexResultSetExtractor("");
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getString("name")).thenReturn("Том Хэнкс");

        // act
        List<Contact> result = extractor.extractData(resultSet);

        // assert
        assertThat(result).isEmpty();
    }

    @Test()
    public void ifFilterIsNullItMatchesNothing() throws SQLException {

        // arrange
        extractor = new ContactRegexResultSetExtractor(null);
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getString("name")).thenReturn("Том Хэнкс");

        // act
        List<Contact> result = extractor.extractData(resultSet);

        // assert
        assertThat(result).isEmpty();
    }
}