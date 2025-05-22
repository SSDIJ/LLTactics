Feature: Gestión de objetos

Scenario: Login como admin, modificar objeto, mirar galería

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

    # Esperar a que el formulario de edición del objeto esté visible
    * waitFor("form[action='/admin/gestGaleria/updateObjeto/1']")

    # Cambiar el valor del campo 'nombre'
    * clear("form[action='/admin/gestGaleria/updateObjeto/1'] input[name='nombre']")
    * input("form[action='/admin/gestGaleria/updateObjeto/1'] input[name='nombre']", 'TestObjeto')

    # Hacer clic en el botón de modificar
    * click("#updateObj")

    # Esperamos que el botón de la galería esté disponible
    * waitFor('#galeria-btn')
    * click('#galeria-btn')

    # Esperamos a que se cargue la galería
    * waitFor('#galeria')
    * delay(3000)

    * def textos = script("Array.from(document.querySelectorAll('.card-title')).map(e => e.textContent.trim())")
    * match textos contains 'TestObjeto'
  
  Scenario: Login como admin, eliminar objeto, mirar galería

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

    * click("#eliminarObj-1")

    # Esperamos que el botón de la galería esté disponible
    * waitFor('#galeria-btn')
    * click('#galeria-btn')

    # Esperamos a que se cargue la galería
    * waitFor('#galeria')
    * delay(3000)

    * def textos = script("Array.from(document.querySelectorAll('.card-title')).map(e => e.textContent.trim())")
    * match textos !contains 'Collar Ankh'
