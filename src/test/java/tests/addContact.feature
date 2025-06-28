Feature: Add contact

  Background:
    * url baseUrl
    * def login = call read('login.feature')

  Scenario: Successfully add a contact
    Given path "contacts"
    And header Authorization = "Bearer " + login.token
    And request 
    """
    {
      "firstName": "John",
      "lastName": "Doe",
      "birthdate": "1970-01-01",
      "email": "jdoe@fake.com",
      "phone": "8005555555",
      "street1": "1 Main St.",
      "street2": "Apartment A",
      "city": "Anytown",
      "stateProvince": "KS",
      "postalCode": "12345",
      "country": "Karate Country"
    }
    """
    When method post
    Then status 201
    And match response ==
    """
    {
      "_id": "#string",
      "__v": "#number",
      "owner": "#(login.userId)",
      "firstName": "John",
      "lastName": "Doe",
      "birthdate": "1970-01-01",
      "email": "jdoe@fake.com",
      "phone": "8005555555",
      "street1": "1 Main St.",
      "street2": "Apartment A",
      "city": "Anytown",
      "stateProvince": "KS",
      "postalCode": "12345",
      "country": "Karate Country"
    }
    """

  Scenario: Deny new contact with duplicated email
    Given path "contacts"
    And header Authorization = "Bearer " + login.token
    And request 
    """
    {
      "firstName": "John",
      "lastName": "Doe",
      "birthdate": "1970-01-01",
      "email": "jdoe@fake.com",
      "phone": "8005555555",
      "street1": "1 Main St.",
      "street2": "Apartment A",
      "city": "Anytown",
      "stateProvince": "KS",
      "postalCode": "12345",
      "country": "Karate Country"
    }
    """
    When method post
    Then match responseStatus != 201

