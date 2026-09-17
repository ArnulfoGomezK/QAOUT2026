Feature: Iniciar sesión en la plataforma Colprepo

  @Test
  Scenario: Inicio de sesión exitoso con credenciales válidas
    Given Ingresamos a la url de Colprepo
    When Ingrsamos usuario
    And Ingrsamos contraseña
    And Damos clic en el boton acceder
    And Damos clic en el boton acceder de la extension
    And Damos clic en el boton si

    Then Validamos que estemos dentro de Colprepo