const formulario = document.querySelector('#formulario');

formulario.addEventListener('submit', function (event) {
    event.preventDefault();

    const nombre = document.querySelector('#nombre').value;
    const apellido = document.querySelector('#apellido').value;
    const cedula = document.querySelector('#cedula').value;
    const fechaDeIngreso = document.querySelector('#fechaDeIngreso').value;
    const email = document.querySelector('#email').value;
    const calle = document.querySelector('#calle').value;
    const numero = document.querySelector('#numero').value;
    const localidad = document.querySelector('#localidad').value;
    const provincia = document.querySelector('#provincia').value;

    if (nombre.trim() === "" || apellido.trim() === "" || cedula.trim() === "") {
        alert("todos los campos son obligatorios")
        return;
    }

    fetch('/pacientes/guardar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "nombre": nombre,
            "apellido": apellido,
            "cedula": cedula,
            "fechaDeIngreso": fechaDeIngreso,
            "email": email,
            "domicilio": {
                "calle": calle,
                "numero": numero,
                "localidad": localidad,
                "provincia": provincia
            }
        })
    })
        .then(response => response.json())
        .then()
})
