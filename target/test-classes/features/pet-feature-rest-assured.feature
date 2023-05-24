@pets
Feature: (e2e) Validate pets

  @postPet
  Scenario: (e2e) Validate post new pet
    Given the following post that add pet
    Then the response is 200 for the post pet


  @postListPets
  Scenario: (e2e) Validate that the response of the pets list request is 200
    Given the following post request that create with a list
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
    Given the following request that delete a pet
    And the response is 200 for delete
    Then the body response contains the "<message>" of the pet delete


    Examples:
      | message |
      | 11115555  |