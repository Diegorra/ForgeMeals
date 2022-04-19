# ForgeMeals
ForgeMeals es una aplicación web que como idea básica presenta un blog de recetas que permite adquirir sus ingredientes para realizarlas en casa de forma fácil, no solo esto, permite definir un menú semanal y adquirirlo de igual manera, otras cosas que permite hacer son valorar mediantes estrellas las recetas y añadir comentarios a las mismas, así como la gestión de la cuenta del usuario.
### Vistas
* [Index](http://localhost:8080/)-> Landing page, desde ella el usuario puede ver recetas, hacer loggin/registrarse y en caso de estar loggeado acceder a funcionalidad adicional como subir receta, ver su menú semanal o su carrito.
* [Checkout](http://localhost:8080/user/checkout)-> Muestra el carrito del usuario loggeado, el usuario puede establecer la cantidad de cada receta seleccionada y en caso de no querer alguna deseleccionarla, una ves conforme puede proceder al pago. Necesario estar logueado.
* [WeekPlan](http://localhost:8080/user/weekplan)-> En esta página el usuario previamente loggeado puede establecer su menú semanal y una vez configurado añadirlo al carrito y realizar el pedido. Necesario estar logueado.
* [Upload Recipe](http://localhost:8080/user/addRecipe)->Formulario a través del cual el usuario puede subir una receta siempre y cuando esté loggeado. Presenta diversos campos como el nombre de la receta,los ingredientes, una descripción de su elaboración y la imagén asociada. Necesario estar logueado.
*  [Página de la receta](http://localhost:8080/recipe/1)->Permite acceder a la página de la receta para una mejor consulta. En esta página el usuario puede añadir la receta a su carrito y verlo en tiempo real, valorarla y añadir comentarios. Necesario estar logueado.
*  [Búsqueda de recetas](http://localhost:8080/search?recipeName=pizza)->En la barra de navegación hay una barra de búsqueda. Esta barra permite buscar recetas y consultarlas.
*  [Profile](http://localhost:8080/user/1)-> Página personal de un usuario, en ella se muestran sus recetas, en caso de ser el propio usuario o un administrador puede gestionarlas. Necesario estar logueado.
*  [Settings](http://localhost:8080/user/1/settings)-> Página de ajustes del usuario, en esta pagina el usuario puede ver su información personal y cambiarla si así lo desea. Necesario estar logueado.
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
### Import
Para el acceso al sistema para pruebas se presentan dos usuarios:
- a: Usuario administrador. Contraseña: "aa".
- b: Usuario corriente. Contraseña: "aa".
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------
### Algunas Cosas a Mejorar
* Cuando subo una receta, limitar el tamaño de la previsualización de la imágen (para que no exceda los márgenes, por ejemplo). Y notificar que se ha subido bien.
* Las imágenes de las recetas deben ir en los fuentes de la aplicación. 
* En `recipeForm.html`, mejor poner todo el JS al final (o en un fichero aparte). Checkear métodos que no se usan en el JS.
* Implementar websockets. Propongo que los useis para que los administradores puedan hacer seguimiento de los pedidos: si alguien hace checkout mientras un admin está en la página donde se ven los pedidos, que se le actualice automáticamente, sin tener que recargar página. Creo que sería mejor para los comentarios, o en el carrito en la página de la receta.
* Implementar pruebas sobre más funcionalidad del sistema.

