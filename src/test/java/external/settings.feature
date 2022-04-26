Feature: Configuraciones de usuario
    #Pruebas para settings. Parece mas de estilo prueba que de test teniendo mente contagiada. !!Revise
    Scenario: Cambiar a un usernanme existente
        #Login.DRY
        Given driver baseUrl + '/login'
        And input('#username', 'a')
        And input('#password', 'aa')
        When submit().click("{button}Sign in")
        Then waitForUrl(baseUrl)
        #Settings
        Given driver baseUrl + '/user/1/settings'
        And clear('#firstname')
        And input('#firstname', 'b')
        #When submit().click("#dataButton"). not expected to trigger a new page
        And waitForEnabled('#dataButton').click()
        #Buscando el patron por toda la pagina. Posiblemente se puede especificar mas
        Then waitForText('body','usuario existente')
    
    Scenario: Cambiar a una contraseña "insegura"
        Given driver baseUrl + '/login'
        And input('#username', 'a')
        And input('#password', 'aa')
        When submit().click("{button}Sign in")
        Then waitForUrl(baseUrl)
        #Settings
        Given driver baseUrl + '/user/1/settings'
        And input('#inputOldPassword', 'b')
        And input('#inputNewPassword', 'asdfghjkl')
        And input('#inputConfPassword', 'asdfghjkl')
        And waitForEnabled('#passwordButton').click()
        #Buscando el patron por toda la pagina.
        Then waitForText('body','insegura')
