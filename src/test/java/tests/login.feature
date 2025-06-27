Feature: Login to contact app

  Background:
    * url baseUrl

  Scenario: Login with valid credentials
    Given path 'users','login'
    And request {"email": "juan@caca.com", "password": "juancaca"}
    When method post
    Then status 200
    And match response == 
    """
    {
      "user": {
        "_id": "#string",
        "firstName": "#string",
        "lastName": "#string",
        "email": "#string",
        "__v": #number
      },
      "token": "#string"
    }
    """
    * def token = response.token
    * def userId = response.user._id
