Feature: Prueba principal

  Scenario: Login como admin, eliminar héroe, mirar galería
    
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

    # Regresamos a la página de inicio
    * click('#heroes-btn')

    # Esperar a que la sección de facción esté disponible
    * waitFor('#faccion')
    * select('#faccion', 'humanos')
    * delay(5000)

    # Eliminar héroe
    * click('#eliminarHeroe')
    * waitFor('#eliminarHeroe')
    * waitFor('#eliminarHeroe option')
    * select('#eliminarHeroe', '4')
    * delay(2000)
    * click('#eliminar-heroe-btn')
    * delay(2000)
    * waitFor('#confirmacionModal')
    * delay(2000)
    * script("closeModal()")
    # Esperamos que el botón de la galería esté disponible
    * waitFor('#galeria-btn')
    * click('#galeria-btn')

    * waitFor('#galeria')
    * delay(3000)
    # Verificamos que la página ya no contiene 'Arquero'
    * match text('#div-humanos') !contains 'Arquero'
