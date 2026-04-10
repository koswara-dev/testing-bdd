Feature: SauceDemo Login Functionality
    Scenario: Successful login with valid credentials
        Given the user is on the login page
        When the user enters valid username and password
        And clicks the login button
        Then the user should be redirected to the inventory page
        And the page header should display Products
    
    Scenario: Login failure with incorrect credentials
        When the user enters wrong username and password
        And clicks the login button
        Then the user should see an error message