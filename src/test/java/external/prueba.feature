Feature: Prueba principal

  Scenario: Login como admin, eliminar heroe, mirar galería
    
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

    # Aqui volvemos a la página de inicio
    * click('#heroes-btn')

    * waitFor('#faccion')
    * select('#faccion', 'humanos')

    * click('#eliminarHeroe')
    * waitFor('#eliminarHeroe')
    * waitFor('#eliminarHeroe option')
    * select('#eliminarHeroe', '33')
    * waitFor('#eliminar-heroe-btn')
    * click('#eliminar-heroe-btn')

    # Esperar la alerta después de eliminar el héroe
    # TODO:

    #* match karate.getAlert().text == 'Héroe eliminado correctamente'
    #* karate.alert().accept()

    * click('#galeria-btn')
    
    * match text('body') !contains 'Tanque'