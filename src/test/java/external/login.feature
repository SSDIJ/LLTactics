Feature: login en servidor

  Scenario: login malo en plantilla
    Given driver baseUrl + '/login'
    And input('#username', 'dummy')
    And input('#password', 'world')
    When submit().click(".form-signin button")
    Then match html('.error') contains 'Error en nombre de usuario o contraseña'

  @login_b
  Scenario: login correcto como b
    Given driver baseUrl + '/login'
    And input('#username', 'b')
    And input('#password', 'aa')
    When submit().click(".form-signin button")
    Then waitForUrl(baseUrl + '/user/2')
    And match driver.url == baseUrl + '/user/2'

  @login_a
  Scenario: login correcto como a
    Given driver baseUrl + '/login'
    And input('#username', 'a')
    And input('#password', 'aa')
    When submit().click(".form-signin button")
    Then waitForUrl(baseUrl + '/admin')
    And match driver.url == baseUrl + '/admin/'

  Scenario: logout after login
    Given driver baseUrl + '/login'
    And input('#username', 'a')
    And input('#password', 'aa')
    When submit().click(".form-signin button")
    Then waitForUrl(baseUrl + '/admin')
    And match driver.url == baseUrl + '/admin/'
    When click("{button}Cerrar sesión")
    Then waitForUrl(baseUrl + '/login')
    And match driver.url == baseUrl + '/login'
