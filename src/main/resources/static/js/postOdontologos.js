const formulario = document.querySelector('#formulario');

formulario.addEventListener('submit', function (event) {
    event.preventDefault();

    const nombre = document.querySelector('#nombre').value;
    const apellido = document.querySelector('#apellido').value;
    const matricula = document.querySelector('#matricula').value;

    if (nombre.trim() === "" || apellido.trim() === "" || matricula.trim() === "") {
        alert("todos los campos son obligatorios")
        return;
    }

    fetch('/odontologos/guardar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "nombre": nombre,
            "apellido": apellido,
            "matricula": matricula
        })
    })
        .then(response => response.json())
})

