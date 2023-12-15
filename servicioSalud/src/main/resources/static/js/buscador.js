document.addEventListener("DOMContentLoaded", function () {
    let searchForm = document.getElementById("searchForm");
    let searchInput = document.querySelector("#searchForm input");
    let optionsContainer = document.getElementById("searchOptions");

    // Define los términos de búsqueda y sus respectivas URL de redirección
    let searchTerms = {
        Registro: "/paciente/registrar",
        Servicios: "/servicios",
        Ambulancias: "/servicios",
        Doctores: "/profesionales",
        Atención: "/servicios",
    };

    // Función para mostrar las opciones al usuario
    function showOptions(matchingTerms) {
        // Limpiar las opciones anteriores
        optionsContainer.innerHTML = "";

        if (matchingTerms.length > 0) {
            // Mostrar opciones al usuario0
            let optionsList = document.createElement("ul");

            matchingTerms.forEach(function (term) {
                let optionItem = document.createElement("li");
                optionItem.textContent = term;
                optionItem.addEventListener("click", function () {
                    // Redirigir al hacer clic en la opción
                    window.location.href = searchTerms[term];
                });

                optionsList.appendChild(optionItem);
                optionItem.style.listStyle = "none";
                optionItem.style.textAlign = "left";
                // Agregar eventos de ratón para cambiar el color
                optionItem.addEventListener("mouseover", function () {
                    optionItem.style.backgroundColor = "#27ef70b1"; // Cambia el color al pasar el ratón
                });

                optionItem.addEventListener("mouseout", function () {
                    optionItem.style.backgroundColor = "transparent"; // Restaura el color original al salir el ratón
                });
            });

            optionsContainer.appendChild(optionsList);
            optionsContainer.style.backgroundColor = "white";
            optionsContainer.style.width = "12em";

            // Mostrar el contenedor de opciones
            optionsContainer.style.display = "block";
        } else {
            // Ocultar el contenedor de opciones si no hay coincidencias
            optionsContainer.textContent = "No se encontraron coincidencias";
            optionsContainer.style.display = "none";
        }
    }

    // Evento input para detectar cambios en el campo de búsqueda
    searchInput.addEventListener("input", function () {
        let searchTerm = searchInput.value.toLowerCase();
        let matchingTerms = [];

        // Filtrar los términos de búsqueda que incluyen la cadena escrita
        for (let term in searchTerms) {
            if (term.toLowerCase().includes(searchTerm)) {
                matchingTerms.push(term);
            }
        }

        // Mostrar las opciones al usuario
        showOptions(matchingTerms);
    });

    // Evento submit del formulario (puedes mantener esto si también deseas manejar la búsqueda al enviar el formulario)
    searchForm.addEventListener("submit", function (event) {
        event.preventDefault();
        let searchTerm = searchInput.value.toLowerCase();
        let matchingTerms = [];

        // Filtrar los términos de búsqueda que incluyen la cadena escrita
        for (let term in searchTerms) {
            if (term.toLowerCase().includes(searchTerm)) {
                matchingTerms.push(term);
            }
        }

        // Mostrar las opciones al usuario
        showOptions(matchingTerms);
    });

    // Ocultar el contenedor de opciones al hacer clic en cualquier parte de la página
    document.addEventListener("click", function (event) {
        if (!optionsContainer.contains(event.target)) {
            optionsContainer.style.display = "none";
        }
    });
});