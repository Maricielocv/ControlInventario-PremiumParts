const sidebar = document.getElementById("sidebar");
const toggleBtn = document.getElementById("toggleSidebar");
const openBtn = document.getElementById("openSidebar");
const closeBtn = document.getElementById("closeSidebar");
const overlay = document.getElementById("sidebarOverlay");
const parent = document.querySelector(".parent");

function closeMobileSidebar() {
  sidebar.classList.remove("open");
  overlay.classList.remove("active");
  document.body.style.overflow = "";
}

toggleBtn?.addEventListener("click", () => {
  parent.classList.toggle("sidebar-collapsed");
});

openBtn?.addEventListener("click", () => {
  sidebar.classList.add("open");
  overlay.classList.add("active");
  document.body.style.overflow = "hidden";
});

closeBtn?.addEventListener("click", closeMobileSidebar);
overlay?.addEventListener("click", closeMobileSidebar);

function actualizarReloj() {
  const ahora = new Date();

  const horas = String(ahora.getHours()).padStart(2, "0");
  const minutos = String(ahora.getMinutes()).padStart(2, "0");
  const segundos = String(ahora.getSeconds()).padStart(2, "0");
  const horaActual = `${horas}:${minutos}:${segundos}`;

  const opciones = { year: "numeric", month: "long", day: "numeric" };
  const fechaActual = ahora.toLocaleDateString("es-ES", opciones);

  const fechaFormateada =
    fechaActual.charAt(0).toUpperCase() + fechaActual.slice(1);

  const relojElement = document.getElementById("reloj");
  const fechaElement = document.getElementById("fecha");

  if (relojElement) {
    relojElement.textContent = horaActual;
  }

  if (fechaElement) {
    fechaElement.textContent = fechaFormateada;
  }
}

setInterval(actualizarReloj, 1000);

actualizarReloj();
