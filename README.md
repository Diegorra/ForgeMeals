# ForgeMeals
ForgeMeals es una aplicación web que como idea básica presenta un blog de recetas que permite adquirir sus ingredientes para realizarlas en casa de forma fácil, no solo esto, permite definir un menú semanal y adquirirlo de igual manera, otras cosas que permite hacer son valorar mediantes estrellas las recetas y añadir comentarios a las mismas, así como la gestión de la cuenta del usuario.

### Mejoras Introducidas
En la mejoras han participado: Jun Qiu, Félix Rodolfo Díaz Lorente y Diego Andrés Ramón Sanchis.
Las mejoras recogidas son: 
* Administración de ingredientes, ahora en la página de administración se pueden consultar todos los ingredientes, eliminarlos o añadir nuevos(unicamente los administradores pueden realizar estas acciones). Los usuarios, a la hora de subir una receta pueden coger de los ingredientes oficiales, que serán tenidos en cuenta en el checkout y a la hora de calcular el precio, o meter ingredientes propios que no esten recogidos en la lista de ingredientes oficiales, que no serán tenidos en cuenta en el checkout ni a la hora de calcular el precio del pedido.
* Administración de pedidos, ahora en la página de administración no ves unicamente los pedidos entrantes pendientes de enviar sino también aquellos pedidos que ya han sido enviados. Se ha corregido algunos aspectos a la hora de mostrar los pedidos como mostrar la dirección a la que son enviados, la lista de ingredientes asociados y la correcta actualización via websockets.
* Actualización de recetas, los usuarios ahora aparte de crear/eliminar recetas también pueden editarlas.

### Vistas
* [Index](http://localhost:8080/)-> Landing page, desde ella el usuario puede ver recetas, hacer loggin/registrarse y en caso de estar loggeado acceder a funcionalidad adicional como subir receta, ver su menú semanal o su carrito.
* [Checkout](http://localhost:8080/user/checkout)-> Muestra el carrito del usuario loggeado, el usuario puede establecer la cantidad de cada receta seleccionada y en caso de no querer alguna deseleccionarla, una ves conforme puede proceder al pago. Necesario estar logueado.
* [WeekPlan](http://localhost:8080/user/weekplan)-> En esta página el usuario previamente loggeado puede establecer su menú semanal y una vez configurado añadirlo al carrito y realizar el pedido. Necesario estar logueado.
* [Upload Recipe](http://localhost:8080/user/addRecipe)->Formulario a través del cual el usuario puede subir una receta siempre y cuando esté loggeado. Presenta diversos campos como el nombre de la receta,los ingredientes, una descripción de su elaboración y la imagén asociada. Necesario estar logueado.
*  [Página de la receta](http://localhost:8080/recipe/1)->Permite acceder a la página de la receta para una mejor consulta. En esta página el usuario puede añadir la receta a su carrito y verlo en tiempo real, valorarla y añadir comentarios. Necesario estar logueado.
*  [Búsqueda de recetas](http://localhost:8080/search?recipeName=pizza)->En la barra de navegación hay una barra de búsqueda. Esta barra permite buscar recetas y consultarlas.
*  [Profile](http://localhost:8080/user/1)-> Página personal de un usuario, en ella se muestran sus recetas, en caso de ser el propio usuario o un administrador puede gestionarlas. Necesario estar logueado.
*  [Settings](http://localhost:8080/user/1/settings)-> Página de ajustes del usuario, en esta pagina el usuario puede ver su información personal y cambiarla si así lo desea. Necesario estar logueado.
*  [Administración](http://localhost:8080/admin/ad)-> Página de administración. Incluye la administración de usuarios, pédidos e ingredientes, los usuario pueden ser eliminados o que se cambie su rol a administrador. Los pedidos pueden confirmarse para se enviados, y pueden consultarse aquellos que ya han sido enviados. Se pueden administrar ingredientes eliminandolos o añadiendo nuevos. La administración de recetas se lleva a cabo a través de la pagina de Index, donde los usuarios administradores tienen la opción de borrar recetas. Necesario estar logueado y tener perfil de administrador.
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
### Import
Para el acceso al sistema para pruebas se presentan dos usuarios:
- a: Usuario administrador. Contraseña: "aa".
- b: Usuario corriente. Contraseña: "aa".
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
