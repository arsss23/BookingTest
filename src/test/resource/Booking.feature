Feature: Booking feature
  #This test fails sometimes because the dropdown menu changes might be Booking doing some sort of AB testing or staged rollout because
  Scenario: Account creation
    Given I am in Sign Up page
    When I enter valid user email
    And click on GET STARTED button
    And I enter valid password
    And click on Create Account button
    And main page is opened
    And I click on My Dashboard button under account menu
    Then My Dashboard page is opened
    And correct value is prefilled in email verification placeholder
  #//based on registered email

  Scenario: Book first displayed hotel
    Given I have account created
    And main page is opened
    When I set up destination as "Liepaja"
    #I changed the dates to be in the future for the test case to make sense
    And I set dates "28-01-2021" - "31-01-2021"
    And I select "2" adults and "1" children
    And I click on Search button
    And I click on Choose your room for fist hotel in the list
    And Hotel Details page is opened for selected hotel
  #// verify at least that hotel name, rating is displayed, information in available rooms section matches your previously entered information
    And I click on Reserve button for recommended room
    And I click on I'll Reserve button
    Then Checkout page is displayed
  #//verify that dates are correct, check if price matches the price in details page, reservation time counter is decreasing, check other information based on previous inputs (amount of adults, etc.)
    And I enter valid booking information
    And I click on Next: Final Details button
    And Final Details page is displayed

  Scenario: Book cheapest hotel in city
    Given I have account created
    And main page is opened
    When I set up destination as "Riga"
  #//make sure you have strict method for selecting cities from dropdown and select city which strictly match entered name.
    And I set dates "20-01-2021" - "25-01-2021"
    And I select "2" adults and "0" children
    And I click on Search button
    And I click on Choose your room for the cheapest hotel in the list with a rating above "3" stars
  #//Choose most convenient way to achieve that
    And Hotel Details page is opened for selected hotel
  #// verify at least that hotel name, rating is displayed, information in available rooms section matches your previously entered information
    And I click on I'll Reserve button for the most expensive available room in the hotel
    Then Checkout page is displayed
  #//verify that dates are correct, check if price matches the price in details page, reservation time counter is decreasing, check other information based on previous inputs (amount of adults, etc.)
    And I enter valid booking information
    And I click on Next: Final Details button
    And Final Details page is displayed