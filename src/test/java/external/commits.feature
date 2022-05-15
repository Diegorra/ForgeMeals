Feature: Comentarios de recetas
Scenario: Comentar una receta
    Given driver baseUrl + '/login'
    And input('#username', 'a')
    And input('#password', 'aa')
    When submit().click("{button}Sign in")
    Then waitForUrl(baseUrl)
    #Receta
    Given driver baseUrl + '/recipe/2'
    #Comentario
    And input('#text', "Esto es un comentario")
    * def elements = locateAll('{}★')
    #5--1 
    #4Estrella
    And elements[1].click();
    #And mouse('{}★').click()
    And waitForEnabled('#comment').click()
    Then waitForText('body','Esto es un comentario')
    
