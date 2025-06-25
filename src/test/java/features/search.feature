Feature: NHS Job Search

  Scenario: Open NHS Jobs site
    Given I am on the NHS Jobs search page

  Scenario: Search using all available preferences
    Given I am on the NHS Jobs search page
    When I fill all search fields with:
      | keyword      | analyst                        |
      | location     | London                         |
      | distance     | 50                             |
      | jobReference |                                |
      | employer     | NHS                            |
      | payRange     | 30000 to 40000                 |
    And I click the search button
    Then I should see job results listed
    And the results should be within the specified pay range


  Scenario: Search for jobs with working pattern
    Given I am on the NHS Jobs search page
    When I fill all search fields with:
      | keyword      | analyst                        |
      | location     | Birmingham                     |
      | distance     | 100                            |
      | jobReference |                                |
      | employer     | NHS                            |
      | payRange     | 30000 to 40000                 |
    And I click the search button
    Then I should see job results listed
    And the results should be within the specified pay range

    When I select working pattern "Full time"
    And I apply the search filters
    Then I should see only jobs with working pattern "Full time"

  Scenario: Search results are sorted by closing date
    Given I am on the NHS Jobs search page
    When I fill all search fields with:
      | keyword      | analyst        |
      | location     | Birmingham     |
      | distance     | 100            |
      | jobReference |                |
      | employer     | NHS            |
      | payRange     | 30000 to 40000 |
    And I click the search button
    And I select sort by "Closing Date"
    Then I should see job results sorted by closing date

  Scenario: Search results are sorted by Date Posted (newest)
    Given I am on the NHS Jobs search page
    When I fill all search fields with:
      | keyword      | analyst        |
      | location     | Birmingham     |
      | distance     | 100            |
      | jobReference |                |
      | employer     | NHS            |
      | payRange     | 30000 to 40000 |
    And I click the search button
    And I select sort by "Date Posted (newest)"
    Then I should see job results sorted by Date Posted newest














  # Scenario: Clear search filters
  #   Given I am on the NHS Jobs search page
  #   When I enter job preferences with keyword "admin" and location "Birmingham"
  #   And I click the clear filters button
  #   Then all fields should be reset

  # Scenario Outline: NHS Job search with multiple data sets
  #   Given I am on the NHS Jobs search page
  #   When I enter "<jobTitle>" in the What field
  #   And I enter "<location>" in the Where field
  #   And I select "<distance>" from the Distance dropdown
  #   And I click the Search button
  #   Then I should see jobs matching my preferences

  #   Examples:
  #   | jobTitle     | location   |distance |
  #   | nurse        | London     |50       |
  #   | analyst      | Newcastle  |50       |
  #   | developer    | Birmingham |30       |

  # Scenario Outline: NHS Job search with full input coverage
  #   Given I am on the NHS Jobs search page
  #   When I enter "<jobTitle>" in the What field
  #   And I enter "<location>" in the Where field
  #   And I select "<distance>" from the Distance dropdown
  #   And I enter "<jobReference>" in the Job Reference field
  #   And I enter "<employer>" in the Employer field
  #   And I select "<payRange>" from the Pay Range dropdown
  #   And I click the Search button
  #   Then I should see jobs matching my preferences

  #   Examples:
  #     | jobTitle | location | distance | jobReference | employer         | payRange              |
  #     | nurse    | London   | 10       | 123-ABC      | NHS England      | £30,000 to £40,000    |
  #     | analyst  | Bristol  | 50       | 914-JOB-123  | NHS Digital      | £40,000 to £50,000    |

  # Scenario: NHS Job search result validations
  #   Given I am on the NHS Jobs search page
  #   When I enter "analyst" in the What field
  #   And I enter "Birmingham" in the Where field
  #   And I select "100" from the Distance dropdown
  #   And I click the Search button
  #   Then I should see the results header
  #   And I should see job result titles
  #   When I apply filters like full-time, permanent, and pay range 30-40
  #   Then I should see job result titles or a no result message
  #   And I should see pagination controls
