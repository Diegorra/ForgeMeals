Feature: Manejo de carrito
#Son tests genericos 
    Scenario: Seguir comprando
        Given driver baseUrl + '/login'
        And input('#username', 'b')
        And input('#password', 'aa')
        When submit().click("{button}Sign in")
        Then waitForUrl(baseUrl)
        #CheckOut
        Given driver baseUrl + '/user/checkout'
        #Seguir comprando
        When submit().click("#SegComp")
        Then waitForUrl(baseUrl)

    Scenario: Añadiendo 2 recetas al carritos, eliminando una y finaliza el pedido.
        Given driver baseUrl + '/login'
        And input('#username', 'b')
        And input('#password', 'aa')
        When submit().click("{button}Sign in")
        #Desde index. Primera receta
        Then waitForUrl(baseUrl)
        Given driver baseUrl + '/recipe/1'
        When submit().click('#addToCart')
        Then delay(1000)
        #Desde una receta concreta
        Given driver baseUrl + '/recipe/2'
        When submit().click('#addToCart')
        Then delay(1000)
        #CheckOut . Podria pasar casos de que no hemos tenido receta 1-2 y por lo cual no funcionaria el test. 
        Given driver baseUrl + '/user/checkout'
        #Tambien podriamos comprobar si la receta-1-2 ha añadido correctamente al carrito. Pero esa comprobacion puede depende de BD(receta-1-2-nombre??).
        Then waitForText('body','2 articulos')
        And  waitForEnabled('#elimButton').click()
        Then waitForText('body','1 articulo')
        And waitForEnabled('#RealPedido').click()
        Then waitForUrl(baseUrl + '/user/payment')

    Scenario: Realizar pedido sin dirección configurada
        Given driver baseUrl + '/login'
        And input('#username', 'a')
        And input('#password', 'aa')
        When submit().click("{button}Sign in")
        #Desde index. Añadimos al carrito la primera receta
        Then waitForUrl(baseUrl)
        Given driver baseUrl + '/recipe/1'
        When submit().click('#addToCart')
        Then delay(1000)
        #CheckOut. Tratamos de realizar el  pedido
        Given driver baseUrl + '/user/checkout'
        And waitForEnabled('#RealPedido').click()
        Then waitForUrl(baseUrl + '/error')