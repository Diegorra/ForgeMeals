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
        #Ingredientes: Lista de instancia de elementos
        And waitForEnabled('{button}+ Añadir Ingrediente').click()
        * def ingredientNames = locateAll('#ingredient')
        * def ingredientCant = locateAll('#cant_ing')
        #Estaria bien si funciona .select() para dataList.
        And ingredientNames[0].input('Tomate')
        And ingredientCant[0].input('1')
        And ingredientNames[1].input('Tomate')
        And ingredientCant[1].input('2')
        #Descripcion
        And input('#description', 'Tomates') 
        And waitForEnabled('#sub_but').click()
        #Confir por img vacio
        Then dialog(true)
        Then waitForUrl(baseUrl)
        #Buscando el patron por la pagina principal. Demasiado general, si hubiera existido alguna receta con ese nombre habria pasado la prueba sin tener un exito real. 
        Then waitForText('body','Tomate con tomate')
        
