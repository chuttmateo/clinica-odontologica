window.addEventListener("load", function () {

  consultarTareas();

  function consultarTareas() {
      console.log("Consultando mis odobntologos...");
      fetch("/pacientes")
          .then(response => response.json())
          .then(tareas => {
              console.log("Tareas del usuario");
              console.table(tareas);

              renderizarTareas(tareas);
              //botonesCambioEstado();
              botonBorrarTarea();
          })
          .catch(error => console.log(error));
  };


  function renderizarTareas(listado) {

      const tablaOdontologos = document.querySelector('#tablaOdontologos');
      tablaOdontologos.innerHTML = '';

      listado.forEach(paciente => {
          const fila = document.createElement('div');
          fila.classList.add('row', 'row-cols-13', 'mt-2', 'mb-2');
          fila.innerHTML = `
          <div class="col">${paciente.id}</div>
          <div class="col">${paciente.nombre}</div>
          <div class="col">${paciente.apellido}</div>
          <div class="col">${paciente.cedula}</div>
          <div class="col">${paciente.fechaDeIngreso}</div>
          <div class="col">${paciente.email}</div>
          <div class="col">${paciente.domicilio.id}</div>
          <div class="col">${paciente.domicilio.calle}</div>
          <div class="col">${paciente.domicilio.numero}</div>
          <div class="col">${paciente.domicilio.localidad}</div>
          <div class="col">${paciente.domicilio.provincia}</div>
        <div class="col"><button type="button" id="${paciente.id}" class="btn btn-danger">Eliminar</button></div>
        <div class="col"><button type="button" id="${paciente.id}" class="btn btn-primary">Actualizar</button></div>`;

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
        <label for="cedula">Cedula:</label>
        <input type="text" class="form-control" id="cedula" required>
      </div>
      <div class="col">
        <label for="fechaDeIngreso">fecha:</label>
        <input type="date" class="form-control" id="fechaDeIngreso" required>
      </div>
      <div class="col">
        <label for="email">Email:</label>
        <input type="text" class="form-control" id="email" required>
      </div>
      <div class="col">
        <label for="domicilio_id">Domicilio id:</label>
        <input type="number" class="form-control" id="domicilio_id" required>
      </div>
      <div class="col">
        <label for="calle">Calle:</label>
        <input type="text" class="form-control" id="calle" required>
      </div>
      <div class="col">
        <label for="numero">Numero:</label>
        <input type="number" class="form-control" id="numero" required>
      </div>
      <div class="col">
        <label for="localidad">Localidad:</label>
        <input type="text" class="form-control" id="localidad" required>
      </div>
      <div class="col">
        <label for="provincia">Provincia:</label>
        <input type="text" class="form-control" id="provincia" required>
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
                  const nuevaCedula = formulario.querySelector('#cedula').value;
                  const nuevaFecha = formulario.querySelector('#fechaDeIngreso').value;
                  const nuevoEmail = formulario.querySelector('#email').value;
                  const nuevoDomicilio_id = formulario.querySelector('#domicilio_id').value;
                  const nuevaCalle = formulario.querySelector('#calle').value;
                  const nuevoNumero = formulario.querySelector('#numero').value;
                  const nuevaLocalidad = formulario.querySelector('#localidad').value;
                  const nuevaProvincia = formulario.querySelector('#provincia').value;


                  if (!nuevoNombre || !nuevoApellido) {
                      alert('Todos los campos son obligatorios.');
                      return;
                  }

                  fetch('/pacientes/actualizar', {
                    method: 'PUT',
                    headers: {
                      'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                      "id":id,
                      nombre: nuevoNombre,
                      apellido: nuevoApellido,
                      cedula: nuevaCedula,
                      fechaDeIngreso: nuevaFecha,
                      email: nuevoEmail,
                      domicilio:{
                        "id": nuevoDomicilio_id,
                        calle: nuevaCalle,
                        numero: nuevoNumero,
                        localidad: nuevaLocalidad,
                        provincia: nuevaProvincia
                      }
                    })
                  }).then(response => {
                    if(response.ok) {
                      // Petición exitosa, realizar acciones necesarias
                      consultarTareas();
                    } else {
                      throw new Error('Error en la petición');
                    }
                  }).catch(error => {
                    console.error('Error:', error);
                  });

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

              fetch(`/pacientes/eliminar/${id}`, {
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
