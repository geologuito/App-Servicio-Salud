// Obtenemos los elementos del DOM
const opciones = document.querySelectorAll(".opcion");
const contenedores = document.querySelectorAll(".contenedor");

// Función para ocultar todos los contenedores
function ocultarContenedores() {
    contenedores.forEach((contenedor) => {
        contenedor.classList.remove("active");
    });
}

// Recorremos las opciones y les añadimos un evento click
opciones.forEach((opcion) => {
    opcion.addEventListener("click", () => {
        const target = opcion.dataset.target;

        // Ocultamos todos los contenedores
        ocultarContenedores();

        // Mostramos solo el contenedor correspondiente al target
        const contenedor = document.getElementById(target);
        contenedor.classList.add("active");

        // Ajustamos la posición del contenedor activo
        contenedor.style.marginTop = '10px';
    });
});

// Ajustamos la altura del contenedor activo al cargar la página
window.addEventListener('load', () => {
    const contenedorActivo = document.querySelector('.contenedor.active');
    if (contenedorActivo) {
        contenedorActivo.style.marginTop = '10px';
    }
});