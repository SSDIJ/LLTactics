Feature: Gestión de usuarios

  Scenario: Usuario no se puede reportar a sí mismo
    
    * configure driver = { type: 'chrome', headless: false }
    * driver 'http://localhost:8080'

    # Página de inicio
    * waitFor('#inicio-sesion-btn')
    * def texto = script("document.querySelector('#keywords-container').textContent.trim()")
    * match texto contains 'Estrategia'
    * click('#inicio-sesion-btn')

    # Página de inicio de sesión
    * input('#username', 'a')
    * input('#password', 'aa')
    * click('#login-btn')

    # Vamos a la gestión de héroes y objetos
    * click('#profile-link')

    * waitFor('#searchPlayer2')
    * input('#searchPlayer2', 'a')
    * click('#searchBtn2')

    * waitFor('#report-btn')
    * click('#report-btn')
    
    * waitFor('#razonBaneo')
    * script("document.querySelector('#razonBaneo').value = 'Usuario comportándose de forma inapropiada';")
    * delay(1000)

    * click('#send-report-btn')
    * delay(1000)

    * click('#gestionDropdown')
    * waitFor('#user-management')
    * click("#user-management")

    * waitFor('#table-reported')
    * def tablaTexto = script("document.querySelector('#table-reported').textContent")
    * match tablaTexto !contains 'a\n'

  Scenario: Login como admin, reportar a b y banearle
    
    * configure driver = { type: 'chrome', headless: false }
    * driver 'http://localhost:8080'

    # Página de inicio
    * waitFor('#inicio-sesion-btn')
    * def texto = script("document.querySelector('#keywords-container').textContent.trim()")
    * match texto contains 'Estrategia'
    * click('#inicio-sesion-btn')

    # Página de inicio de sesión
    * input('#username', 'a')
    * input('#password', 'aa')
    * click('#login-btn')

    # Vamos a la gestión de héroes y objetos
    * click('#profile-link')

    * waitFor('#searchPlayer2')
    * input('#searchPlayer2', 'b')
    * click('#searchBtn2')

    * waitFor('#report-btn')
    * click('#report-btn')
    
    * waitFor('#razonBaneo')
    * script("document.querySelector('#razonBaneo').value = 'Usuario comportándose de forma inapropiada';")
    * delay(1000)

    * click('#send-report-btn')
    * delay(1000)

    * click('#gestionDropdown')
    * waitFor('#user-management')
    * click("#user-management")

    * waitFor('#table-reported')
    * def tablaTexto = script("document.querySelector('#table-reported').textContent")
    * match tablaTexto contains 'b\n'

    * click('#ban-2')
    * delay(1000)



