Feature: Gestión de héroes

  Scenario: Login como admin, modificar héroe y validar en galería

    * configure driver = { type: 'chrome', headless: false }
    * driver 'http://localhost:8080'

    # Página de inicio
    * waitFor('#inicio-sesion-btn')
    * def texto = script("document.querySelector('#keywords-container').textContent.trim()")
    * match texto contains 'Estrategia'
    * click('#inicio-sesion-btn')

    # Login
    * input('#username', 'a')
    * input('#password', 'aa')
    * click('#login-btn')

    # Gestión de héroes
    * click('#gestionDropdown')
    * waitFor('#gallery-management')
    * click("#gallery-management")

    * waitFor('#faccionSelect')
    * select('#faccionSelect', 'humanos')
    * delay(2000)

    * def idHeroe = 1
    * def selector = '#form-update-' + idHeroe

    * waitFor(selector)

    * clear(selector + " input[name='nombre']")
    * input(selector + " input[name='nombre']", 'TanqueModificado')

    * clear(selector + " input[name='vida']")
    * input(selector + " input[name='vida']", '999')

    * clear(selector + " input[name='armadura']")
    * input(selector + " input[name='armadura']", '999')

    * clear(selector + " input[name='daño']")
    * input(selector + " input[name='daño']", '999')

    * clear(selector + " input[name='velocidad']")
    * input(selector + " input[name='velocidad']", '999')

    * clear(selector + " input[name='precio']")
    * input(selector + " input[name='precio']", '999')

    * clear(selector + " input[name='probabilidad']")
    * input(selector + " input[name='probabilidad']", '999')

    * click("#updateUnit-1")
    * delay(1000)

    # Ir a la galería
    * click('#galeria-btn')
    * waitFor('#galeria')
    * delay(3000)

    # Validar nombre modificado visible en la galería
    * def textos = script("Array.from(document.querySelectorAll('.card-title')).map(e => e.textContent.trim())")
    * match textos contains 'TanqueModificado'

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

    # Vamos a la gestión de héroes y objetos
    * click('#gestionDropdown')
    * waitFor('#gallery-management')
    * click("#gallery-management")

    # Esperar a que la sección de facción esté disponible
    * waitFor('#faccionSelect')
    * select('#faccionSelect', 'humanos')
    * delay(5000)

    # Eliminar héroe
    * waitFor('#eliminar-4')  
    * click('#eliminar-4')   
    * delay(2000)         

    # Esperamos que el botón de la galería esté disponible
    * waitFor('#galeria-btn')
    * click('#galeria-btn')

    * waitFor('#galeria')
    * delay(3000)
    # Verificamos que la página ya no contiene 'Arquero'

    * match text('#div-humanos') !contains 'Arquero'

  Scenario: Login como admin, añadir héroe, mirar galería

    * configure driver = { type: 'chrome', headless: false }
    * driver 'http://localhost:8080'

    # Página de inicio
    * waitFor('#inicio-sesion-btn')
    * def texto = script("document.querySelector('#keywords-container').textContent.trim()")
    * match texto contains 'Estrategia'
    * click('#inicio-sesion-btn')

    # Página de login
    * input('#username', 'a')
    * input('#password', 'aa')
    * click('#login-btn')

    # Ir a gestión de héroes
    * click('#gestionDropdown')
    * waitFor('#gallery-management')
    * click("#gallery-management")

    # Esperar a que el formulario esté listo
    * waitFor('#formAnadirHeroe')

    # Rellenar el formulario
    * input('#unidad-nombre', 'UnidadTest')
    * select('#unidad-faccion', 'Humanos')
    * input('#unidad-vida', '300')
    * input('#unidad-armadura', '70')
    * input('#unidad-velocidad', '45')
    * input('#unidad-daño', '110')
    * input('#unidad-precio', '3')
    * input('#unidad-probabilidad', '0.25')

    * input('#unidad-descripcion', 'Un poderoso héroe nacido del código.')

    * delay(2000)     
    # Enviar formulario
    * click('#submit-hero-btn')
        

    # Esperamos que el botón de la galería esté disponible
    * waitFor('#galeria-btn')
    * click('#galeria-btn')

    # Esperamos a que se cargue la galería
    * waitFor('#galeria')
    * delay(3000)

    * def textos = script("Array.from(document.querySelectorAll('.card-title')).map(e => e.textContent.trim())")
    * match textos contains 'UnidadTest'

    