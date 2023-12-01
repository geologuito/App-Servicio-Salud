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
  