# Taller 2
#### Cristian Camilo Ruiz Santa

Este proyecto implementa un servidor HTTP simple que obtiene información sobre películas utilizando la API de OMDb. Incluye un cliente HTML básico que permite a los usuarios buscar películas y muestra detalles sobre el primer resultado.

## Cómo Ejecutar

1. **Configuración del Servidor:**

    - Compila y ejecuta la clase `HttpServer`.
    - El servidor se iniciará en el puerto 35000.

## Uso del servidor

Una vez que el servidor esté en funcionamiento, se puede acceder a los archivos estáticos almacenados en el disco local a través de un navegador web.

Para este ejercicio se crearon archivos html, css, js y jpg y se juntaron en una página web sencilla para probar el funcionamiento del servidor. Aquí hay un ejemplo de cómo cargar la página web guardada en disco local:

- http://localhost:35000/file.html - en este caso el archivo file.html tiene asociada su hoja de estilos css y una alerta por medio de JavaScript. Aquí se ve la respuesta que nos muestra el servidor al hacer esta solicitud en un navegador:

![Imagen evidencia](/src/img/respuesta.JPG)

Se puede ver que devuelve la página html con sus estilos y su código en JavaScript, sin embargo no reconoce la imagen


