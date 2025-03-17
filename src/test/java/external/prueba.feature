Feature: Prueba principal

  Scenario: Login como admin, eliminar heroe, mirar galería
    
    * configure driver = { type: 'chrome', headless: false }
    * driver 'http://localhost:8080'

    # Página de inicio
    * waitFor('#inicio-sesion-btn', 10)
    #* text('body') contains 'Estrategia'
    #* text('body') contains 'Batallas'
    #* text('body') contains 'Sinergias'

    * click('#inicio-sesion-btn')

    # Página de inicio de sesión
    * input('#username', 'a')
    * input('#password', 'aa')
    * click('#login-btn')

    # Aqui volvemos a la página de inicio
    * click('#heroes-btn')
    * select('#faccion', 'Humanos')
    * select('#eliminarHeroe', 'Tanque')
    * click('#eliminar-heroe-btn')

    * match alert().text == 'Héroe eliminado correctamente'
    * alert().accept()

    * click('#galeria-btn')
    * waitFor('#galeria')

    * match text('body') !contains 'Tanque'
