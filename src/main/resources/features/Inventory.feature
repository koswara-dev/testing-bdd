Feature: Add to Cart Functionality
    Scenario: Add a product to the cart
        Given the user is on the inventory page
        When the user clicks the "Add to Cart" button for a product
        Then the product should be added to the cart
        And the cart icon should display the number of items in the cart

    Scenario Outline: Add multiple products to the cart
        Given the user is on the inventory page
        When the user clicks the "Add to Cart" button for "<product>"
        Then "<product>" should be added to the cart
        And the cart icon should display the number of items in the cart

    Examples:
        | product           |
        | Sauce Labs Backpack |
        | Sauce Labs Bike Light |
        | Sauce Labs Bolt T-Shirt |