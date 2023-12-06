document.addEventListener('DOMContentLoaded', function () {
    var sidebar = document.getElementById('sidebar');
    var toggleBtn = document.getElementById('sidebar-toggle');

    toggleBtn.addEventListener('click', function () {
        if (sidebar.style.width === '250px') {
            sidebar.style.width = '0';
            toggleBtn.innerHTML = '☰ Mostrar Perfil';
        } else {
            sidebar.style.width = '250px';
            toggleBtn.innerHTML = '☰ Ocultar Perfil';
        }
    });
  });
<<<<<<< HEAD
  
=======

  // de aqui en adelante lógica del panel dinámico

  const opciones = document.querySelectorAll(".opcion");
const contenedores = document.querySelectorAll(".contenedora");

// Recorremos las opciones y les añadimos un evento click
opciones.forEach((opcion) => {
  opcion.addEventListener("click", () => {
    // Obtenemos el valor del atributo data-target
    const target = opcion.dataset.target;

    // Recorremos los contenedores y les quitamos la clase active
    contenedores.forEach((contenedora) => {
      contenedora.classList.remove("active");
    });

    // Buscamos el contenedor que tenga el mismo id que el target y le añadimos la clase active
    const contenedora = document.getElementById(target);
    contenedora.classList.add("active");

    // Recorremos las opciones y les quitamos la clase active
    opciones.forEach((opcion) => {
      opcion.classList.remove("active");
    });

    // Añadimos la clase active a la opción que se ha clicado
    opcion.classList.add("active");
  });
});
>>>>>>> developer
