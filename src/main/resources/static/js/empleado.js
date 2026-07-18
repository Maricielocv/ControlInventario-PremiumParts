const abrirModalEditarEmpleado = (boton) => {
  const modal = document.getElementById("editarEmpleadoModal");
  if (!modal) return;

  const id = boton.getAttribute("data-id");
  const nombre = boton.getAttribute("data-nombre");
  const apellidoPaterno = boton.getAttribute("data-apellido_paterno");
  const apellidoMaterno = boton.getAttribute("data-apellido_materno");
  const email = boton.getAttribute("data-email");
  const password = boton.getAttribute("data-password");
  const rol = boton.getAttribute("data-rol");

  document.getElementById("editarEmpleadoId").value = id;
  document.getElementById("editarEmpleadoNombre").value = nombre;
  document.getElementById("editarEmpleadoApellidoPaterno").value = apellidoPaterno;
  document.getElementById("editarEmpleadoApellidoMaterno").value = apellidoMaterno;
  document.getElementById("editarEmpleadoEmail").value = email;
  document.getElementById("editarEmpleadoPassword").value = password;
  document.getElementById("editarEmpleadoRol").value = rol;

  modal.classList.remove("hidden");
  document.body.style.overflow = "hidden";
};

const cerrarModalEmpleado = () => {
  const modal = document.getElementById("editarEmpleadoModal");
  if (!modal) return;
  modal.classList.add("hidden");
  document.body.style.overflow = "";
};
