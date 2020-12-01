Feature: Book Cheapest Hotel
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
