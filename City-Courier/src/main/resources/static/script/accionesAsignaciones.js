//Esta tiene que vigilar siempre que se cierre el modal 
var modalCrearAsignacion = document.getElementById("modalCrearAsignacion");
var form = document.getElementById("asignacionForm");

var inputs = form.querySelectorAll("input:not([type='hidden']), select, textarea");

modalCrearAsignacion.addEventListener("hidden.bs.modal", function () {


    inputs.forEach(function (input) {
        input.value = "";
    });

    window.location.replace("/logistica/asignaciones");

});
