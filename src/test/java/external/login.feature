Feature: Gestión de héroes

  Scenario: Login como admin, modificar héroe y validar en galería
      
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
    * click('#heroes-btn')

    # Esperar a que la sección de facción esté disponible
    * waitFor('#faccionSelect')
    * select('#faccionSelect', 'humanos')
    * delay(5000)

    # Seleccionar el formulario del héroe "Tanque" y modificar sus campos (excepto ruta imagen)
    * waitFor('.cardAdmin-form[action="/admin/gestGaleria/update/1"]')
    * input('form[action="/admin/gestGaleria/update/1"] input[name="nombre"]', 'TanqueModificado')
    * input('form[action="/admin/gestGaleria/update/1"] input[name="vida"]', '350')
    * input('form[action="/admin/gestGaleria/update/1"] input[name="armadura"]', '80')
    * input('form[action="/admin/gestGaleria/update/1"] input[name="daño"]', '160')
    * input('form[action="/admin/gestGaleria/update/1"] input[name="velocidad"]', '70')
    * input('form[action="/admin/gestGaleria/update/1"] input[name="precio"]', '4')
    * input('form[action="/admin/gestGaleria/update/1"] input[name="probabilidad"]', '0.5')
    * input('form[action="/admin/gestGaleria/update/1"] input[name="descripcion"]', 'Héroe modificado para pruebas, más fuerte y resistente.')
    * click('form[action="/admin/gestGaleria/update/1"] button[type="submit"]')

    # Esperar respuesta o redirección después de modificar
    * waitFor('#heroes-btn')

    # Navegar a la galería
    * waitFor('#galeria-btn')
    * click('#galeria-btn')

    * waitFor('#galeria')
    * delay(3000)

    # Verificar que existe la carta con todos los datos modificados
    * def cardCorrecta = script(`
      () => {
        const cards = document.querySelectorAll('#galeria .card');
        for (let card of cards) {
          const title = card.querySelector('.card-title')?.textContent.trim();
          const desc = card.querySelector('.card-text small')?.textContent.trim();

          // Extraer valores de las barras de progreso según el orden de las estadísticas en el div.unit-stats
          const stats = card.querySelectorAll('.unit-stats .progress-bar');
          if (!stats || stats.length < 4) continue;

          // Vida
          const vidaText = stats[0].textContent.trim();
          // Armadura
          const armaduraText = stats[1].textContent.trim();
          // Daño
          const danoText = stats[2].textContent.trim();
          // Velocidad
          const velocidadText = stats[3].textContent.trim();

          if (title === 'TanqueModificado' &&
              desc.includes('Héroe modificado para pruebas') &&
              vidaText === '350' &&
              armaduraText === '80' &&
              danoText === '160' &&
              velocidadText === '70') {
            return true;
          }
        }
        return false;
      }
    `)
    * match cardCorrecta == true
