window.addEventListener("load", function () {

    consultarTareas();

    function consultarTareas() {
        console.log("Consultando mis odobntologos...");
        fetch("/odontologos")
            .then(response => response.json())
            .then(tareas => {
                console.log("Tareas del usuario");
                console.table(tareas);

                renderizarTareas(tareas);
                botonBorrarTarea();
            })
            .catch(error => console.log(error));
    };


    function renderizarTareas(listado) {

        const tablaOdontologos = document.querySelector('#tablaOdontologos');
        tablaOdontologos.innerHTML = '';

        listado.forEach(odontologo => {
            const fila = document.createElement('div');
            fila.classList.add('row', 'row-cols-6', 'mt-2', 'mb-2');
            fila.innerHTML = `<div class="col">${odontologo.nombre}</div>
          <div class="col">${odontologo.apellido}</div>
          <div class="col">${odontologo.matricula}</div>
          <div class="col">${odontologo.id}</div>
          <div class="col"><button type="button" id="${odontologo.id}" class="btn btn-danger">Eliminar</button></div>
          <div class="col"><button type="button" id="${odontologo.id}" class="btn btn-primary">Actualizar</button></div>`;

            const btnActualizar = fila.querySelector('.btn-primary');
            btnActualizar.addEventListener('click', event => {
                const id = event.target.id;

                const formulario = document.createElement('form');
                formulario.classList.add('row', 'mt-2', 'mb-2');
                formulario.innerHTML = `<div class="col">
          <label for="nombre">Nombre:</label>
          <input type="text" class="form-control" id="nombre" required>
        </div>
        <div class="col">
          <label for="apellido">Apellido:</label>
          <input type="text" class="form-control" id="apellido" required>
        </div>
        <div class="col">
          <label for="matricula">Matrícula:</label>
          <input type="text" class="form-control" id="matricula" required>
        </div>
        <div class="col">
          <button type="button" class="btn btn-primary guardar-cambios">Guardar</button>
        </div>
        <div class="col">
          <button type="button" class="btn btn-secondary cancelar">Cancelar</button>
        </div>
        `
                fila.parentNode.insertBefore(formulario, fila.nextSibling);

                const btnCancelar = formulario.querySelector('.cancelar');
                btnCancelar.addEventListener('click', event => {
                    fila.parentNode.removeChild(formulario);
                });

                const btnGuardarCambios = formulario.querySelector('.guardar-cambios');
                btnGuardarCambios.addEventListener('click', event => {
                    const nuevoNombre = formulario.querySelector('#nombre').value;
                    const nuevoApellido = formulario.querySelector('#apellido').value;
                    const nuevaMatricula = formulario.querySelector('#matricula').value;

                    if (!nuevoNombre || !nuevoApellido || !nuevaMatricula) {
                        alert('Todos los campos son obligatorios.');
                        return;
                    }

                    fetch(`/odontologos/actualizar`, {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({ "id": id, nombre: nuevoNombre, apellido: nuevoApellido, matricula: nuevaMatricula })
                    })
                        .then(response => {
                            console.log(response.status);
                            //vuelvo a consultar las tareas actualizadas y pintarlas nuevamente en pantalla
                            consultarTareas();
                        })
                });
            });

            tablaOdontologos.appendChild(fila);
        })
    }
    
    function botonBorrarTarea() {
        const btnBorrarTarea = document.querySelectorAll('.btn-danger');

        btnBorrarTarea.forEach(btn => {
            btn.addEventListener('click', event => {
                const id = event.target.id;
                //const id = this.dataset.id;

                fetch(`/odontologos/eliminar/${id}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                })
                    .then(response => {
                        console.log(response.status);
                        //vuelvo a consultar las tareas actualizadas y pintarlas nuevamente en pantalla
                        consultarTareas();
                    })
            });
        });

    };
})
