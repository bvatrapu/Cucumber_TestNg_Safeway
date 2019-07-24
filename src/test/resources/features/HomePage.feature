#AuthorName:
#AuthorEmail:
#Keywords Summary : Home Page Validations
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios

@Desktop @DesktopHome
Feature: Home Page

  Background: Open Browser and navigate to HomePage
    Given User navigates to HomePage

  @Regression
  Scenario Outline: Search for the products as a Guest User - All Direct Link
    Then User Verifies "/home" Page is displayed
    When Click on "<link>" link in the primary navigation
    Then User Verifies "<URL>" Page is displayed
    Then Verify searchBar should be displayed

    Examples:
      | link       | URL        |
      | Pharmacy   | /pharmacy  |
