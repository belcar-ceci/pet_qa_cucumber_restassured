@pets
Feature: (e2e) Validate pets

  @postPet
  Scenario Outline: (e2e) Validate post new pet
    Given the following post that add pet
    And the response is 200 for the post pet
    Then  the body response contains the "<name>" of the pet created

    Examples:
      | name  |
      | felix |

  @getListPets
  Scenario: (e2e) Validate that the response of the pets request is 200
    Given the get request that brings us the pets list
    Then the response is 200 for the get list pet

  @UpdatePet
  Scenario Outline: (e2e) Validate update a pet
    Given the following put request that update a pet
    And the response is 200 for the put pet
    Then the body response contains update "<name>"

    Examples:
      | name  |
      | blisa |

  @DeletePet
  Scenario: (e2e) Validate delete a pet
    Given the following post request that add one pet
    And the following delete request that delete a pet
    Then the body response is 200 for the delete pet