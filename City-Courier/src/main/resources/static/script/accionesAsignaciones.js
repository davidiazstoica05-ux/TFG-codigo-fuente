//Esta tiene que vigilar siempre que se abra el modal 

var modalCrearRepartidor = document.getElementById("modalCrearAsignacion");
var form = document.getElementById("asignacionForm");
var inputs = form.querySelectorAll("input, select, textarea");



modalCrearRepartidor.addEventListener("hidden.bs.modal", function () {


    console.log("pasa por aqui");

    inputs.forEach(function (input) {
        input.value = "";
    })

    window.location.replace("/logistica/asignaciones");


})