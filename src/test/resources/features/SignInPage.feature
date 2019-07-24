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

@Regression
Feature: SignIn Page

  Background: Open Browser and navigate to HomePage
    And Clear All Cookies
    Given User navigates to HomePage

  @Regression
  Scenario: User Signs In from WWW
    Then User Verifies "/home" Page is displayed
    When User Clicks Sign In/Up  Button in header and modal
   # Then User Verifies Sign In or Sign Up modal at www pages should be displayed
    When User Clicks Sign In Button in header and modal
#    Then User Validates Sign Message in www page
    When User Enters Email ID and Password
    Then User Clicks Submit Button
    Then User Verifies "/home" Page is displayed

