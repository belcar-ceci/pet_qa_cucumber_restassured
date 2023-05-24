@users
Feature: (e2e) Validate users

  @postUser
  Scenario Outline: (e2e) Validate post one user
    Given the following post request that add one user
    And the response is 200 for the post
    Then the body response contains the "<message>" of the user created


    Examples:
      | message |
      | 111222  |


  @postUserList
  Scenario: (e2e) Validate post create users with list
    Given the following post request that create with a list
    Then the response is 200 for the post users list

  @getUsers
  Scenario Outline: (e2e) Validate that the response has the new user
    Given the following get request which brings us "<username>"
    Then the response is 200 for the get user
    Examples:
      | username  |
      | fepoLopez |

  @userLogin
  Scenario Outline: (e2e) Validate login into the system
    Given the user login with "<username>" and "<password>"
    Then the response is 200 for login

    Examples:
      | username  | password |
      | fepoLopez | catwhite |

  @userLogout
  Scenario: (e2e) Validate logout current logged in user session
    Given the user logout the current session
    Then the response is 200 got logout

  @UpdateUser
  Scenario: (e2e) Validate updated user
    Given the following put request that update users
    Then the response is 200 for the update

  @DeleteUser
  Scenario: (e2e) Validate deleted user
    Given the following post request that add one user
    Then the following delete request that delete user
