Feature: Plan semanal
    Scenario: All to cart sin recetas
        Given driver baseUrl + '/login'
        And input('#username', 'b')
        And input('#password', 'aa')
        When submit().click("{button}Sign in")
        Then waitForUrl(baseUrl)
        #WeekPlan
        Given driver baseUrl + '/user/weekplan'
        And mouse('#bAllCart').click()
        Given driver baseUrl + '/user/checkout'
        Then waitForText('body','0 articulo')

    Scenario: All to cart con recetas
        Given driver baseUrl + '/login'
        And input('#username', 'a')
        And input('#password', 'aa')
        When submit().click("{button}Sign in")
        Then waitForUrl(baseUrl)
        #WeekPlan
        Given driver baseUrl + '/user/weekplan'
        And mouse('#dropdownMartesDesayuno').click()
        # Se abre haciendo hover
        And mouse().move('.aReceta').go()
        And mouse('{}Hamburguesa').click()
        And mouse('#bAllCart').click()
        Given driver baseUrl + '/user/checkout'
        Then waitForText('body','1 articulo')
        
