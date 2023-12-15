// Obtener todas las secciones de calificación
const ratings = document.querySelectorAll('.rating');

// Recorrer todas las secciones de calificación
ratings.forEach(rating => {
    const stars = rating.querySelectorAll('.star');

    stars.forEach((star, index) => {
        star.addEventListener('click', () => {
            const matricula = rating.getAttribute('data-profesional');
            const value = index + 1;

            // Enviar la puntuación (aquí puedes hacer una petición AJAX a tu backend)
            console.log(`Puntuación: ${value} para el profesional con matrícula ${matricula}`);

            // Remover todas las clases 'active' de estrellas previas
            stars.forEach(s => s.classList.remove('active'));

            // Agregar la clase 'active' a las estrellas seleccionadas
            for (let i = 0; i < value; i++) {
                stars[i].classList.add('active');
            }

            // Actualizar el valor 'data-value' en el div 'rating'
            rating.setAttribute('data-value', value);
        });

        star.addEventListener('mouseover', () => {
            const value = index + 1;
            const starsToHighlight = rating.querySelectorAll('.star:nth-child(-n+' + value + ')');

            starsToHighlight.forEach(s => s.classList.add('active'));
        });

        star.addEventListener('mouseout', () => {
            stars.forEach(s => s.classList.remove('active'));

            const value = parseInt(rating.getAttribute('data-value'));
            const starsToHighlight = rating.querySelectorAll('.star:nth-child(-n+' + value + ')');

            starsToHighlight.forEach(s => s.classList.add('active'));
        });
    });
});