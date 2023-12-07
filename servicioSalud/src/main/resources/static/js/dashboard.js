$(document).ready(function () {
    $('.panel-item').click(function (event) {
        event.preventDefault();

        // Obtén el identificador de la sección correspondiente al botón
        let sectionId = $(this).data('section');

        // Oculta todas las secciones
        $('.dashboard-section').hide();

        // Muestra la sección correspondiente al botón
        $('#' + sectionId).show();
    });
});