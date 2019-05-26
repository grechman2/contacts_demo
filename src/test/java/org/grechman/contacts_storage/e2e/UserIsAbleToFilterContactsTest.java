package org.grechman.contacts_storage.e2e;


import org.assertj.core.api.Condition;
import org.grechman.contacts_storage.entity.Contact;
import org.grechman.contacts_storage.service.ContactService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.PostgreSQLContainer;


import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class UserIsAbleToFilterContactsTest extends AbstractE2ETest{

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    ContactService contactService;


    @Test
    public void filteringContacts_GivenThereAreSeveralContacts_WhenFilteringWithTemplateThatMatchesNamesOfSomeContacts_ThenOnlyContactsAreLeftThatDontMatch() throws Exception {

        // Arrange

        Contact filteredOutContact1 = new Contact( "Федор");
        Contact filteredOutContact2 =  new Contact("Фома");
        Contact shouldBePresentAfterFiltering =  new Contact("Рома");

        contactService.addContacts(Arrays.asList(
                filteredOutContact1,
                filteredOutContact2 ,
                shouldBePresentAfterFiltering));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity entity = new HttpEntity(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("http://localhost:%s/hello/contact/?", getServerPort()))
                .queryParam("nameFilter", "^.*[Ф].*$");

        // Act
        ResponseEntity<List<Contact>> response = testRestTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Contact>>(){});

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isGreaterThan(0);

        long numberOfItemsThatContainsFilteredSymbol = response.getBody().stream().filter(contact -> contact.getName().contains("Ф")).count();
        assertThat(numberOfItemsThatContainsFilteredSymbol).isZero();

        boolean isContactThatShouldBePresentActuallyPresent = response.getBody().stream()
                .filter(contact -> shouldBePresentAfterFiltering.getName().equals(contact.getName())).count() > 0;
        assertThat(isContactThatShouldBePresentActuallyPresent).isTrue();
    }


}