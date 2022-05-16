Feature: Plan semanal
    Scenario: All to cart sin recetas
        Given driver baseUrl + '/login'
        And input('#username', 'b')
        And input('#password', 'aa')
        When submit().click("{button}Sign in")
        Then waitForUrl(baseUrl)
        #WeekPlan
        Given driver baseUrl + '/user/weekplan'
        #No consigo seleccionar la lista de recetas
        #And mouse('#dropdownMartesDesayuno').click()
        #And mouse('{^}receta').go()
        #And mouse('{}Hamburguesa').click()
        And mouse('#bAllCart').click()
        Given driver baseUrl + '/user/checkout'
        Then waitForText('body','0 articulo')
        
        
