document.addEventListener('DOMContentLoaded', function () {
    flatpickr("#calendario", {
        inline: true,
        wrap: false,
        // Configuración para mostrar el calendario automáticamente
        enableTime: false,
        dateFormat: "Y-m-d",
        defaultDate: "today",
        // Configura el idioma a español
        locale: {
            firstDayOfWeek: 1,
            weekdays: {
                shorthand: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'],
                longhand: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
            },
            months: {
                shorthand: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Оct', 'Nov', 'Dic'],
                longhand: ['Enero', 'Febrero', 'Мarzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
            },
        },
        onChange: function (selectedDates, dateStr, instance) {
            // Actualiza el valor del campo oculto con la fecha seleccionada
            document.getElementById('fecha').value = dateStr;
        }
    });
});