Feature: Prueba principal

  Scenario: Login como admin, eliminar heroe, mirar galería         # añadir heroe, mirar galeria
    
    Given I open the page 'http://localhost:8080'

    # Página de inicio
    Then I can read 'Estrategia'
    Then I can read 'Batallas'
    Then I can read 'Sinergias'

    And I Click ('#inicio-sesion-btn')

    # Página de inicio de sesión

    And I enter 'a' into '#username'
    And I enter 'aa' into '#password'
    And I Click ('#login-btn')

    # Aqui volvemos a la página de inicio

    And I Click ('#heroes-btn')
    And I select '#faccion' to 'Humanos'        # TODO: puede ser humanos
    And I select '#eliminarHeroe' to 'Tanque'   # TODO: puede ser humanos
    When I Click ('#eliminar-heroe-btn')

    # Aqui tiene que leer un mensaje de página emergente

    And I assert alert() == "Héroe eliminado correctamente"
    And I accept the alert()

    And I Click ('#galeria-btn')

    # Aqui comprobamos en la galería

    Then I Should not see 'Tanque'