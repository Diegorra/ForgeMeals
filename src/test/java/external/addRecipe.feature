Feature: Añadiendo recetas
    Scenario: Subir una receta sin imagen
        Given driver baseUrl + '/login'
            And input('#username', 'a')
            And input('#password', 'aa')
            When submit().click("{button}Sign in")
            Then waitForUrl(baseUrl)
            #addRecipe
            Given driver baseUrl + '/user/addRecipe'
            #Info de receta
            And input('#name', 'Tomate con tomate')
            And input('#ingredient', 'Tomate')
            And input('#cant_ing', '255')
            #Hay que investigar como introducir mas ingredientes con karate
            And input('#description', 'Tomates') 
            And waitForEnabled('#sub_but').click()
            #Confir por img vacio
            Then dialog(true)
            Then waitForUrl(baseUrl)
            #Buscando el patron por la pagina principal. Demasiado general, si hubiera existido alguna receta con ese nombre habria pasado la prueba sin tener un exito real. 
            Then waitForText('body','Tomate con tomate')
        
