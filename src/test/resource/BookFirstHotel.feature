Feature: Book first Hotel
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
