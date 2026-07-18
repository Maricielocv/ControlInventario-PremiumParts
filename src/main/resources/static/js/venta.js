const input = document.getElementById("productoVenta");
const list = document.getElementById("sugerenciaProductosVenta");
// CONTENEDORES DE LOS ELEMENOS LABEL + INPUT
const formaPago = document.getElementById("metodoPago");
const pagoRecibido = document.getElementById("pago-recibido");
const vuelto = document.getElementById("vuelto");
// INPUT DEL PRECIO DEL PRODUCTO
const inputPrecio = document.getElementById("precioVenta");
// INPUT DEL STOCK DEL PRODUCTO
const inputStock = document.getElementById("stockVenta");
// INPUT DE PAGO RECIBIDO Y VUELTO
const inputPagoRecibido = document.getElementById("pagoRecibido");
const inputVuelto = document.getElementById("vueltoPago");

// MOSTRA R PRECIO DEL PRODUCTO INGRESADO EN EL INPUT DE PRODUCTO
input.addEventListener("change", async (e) => {
  const nombreProducto = e.target.value.trim();
  if (nombreProducto.length > 0) {
    const res = await fetch(
      `/ventas/venta/precio?nombreProducto=${encodeURIComponent(nombreProducto)}`,
    );
    if (res.ok) {
      const precio = await res.json();
      if (precio > 0) {
        inputPrecio.value = precio;
      }
    }
  } else {
    inputPrecio.value = "";
  }
});

// MOSTRAR EL STOCK DEL PRODUCTO INGRESADO EN EL INPUT
input.addEventListener("change", async (e) => {
  const nombreProducto = e.target.value.trim();
  if (nombreProducto.length > 0) {
    const res = await fetch(
      `/ventas/venta/stock?nombreProducto=${encodeURIComponent(nombreProducto)}`,
    );
    if (res.ok) {
      const stock = await res.json();
      if (stock > 0) {
        inputStock.value = stock;
      }
    }
  } else {
    inputStock.value = "";
  }
});


// CALCULAR VUELTO EN TIEMPO REAL SEGUN PAGO RECIBIDO
inputPagoRecibido.addEventListener("input", async () => {
  const pago = parseFloat(inputPagoRecibido.value);
  if (!isNaN(pago) && pago >= 0) {
    const res = await fetch("/ventas/venta/total");
    if (res.ok) {
      const total = await res.json();
      const cambio = pago - total;
      inputVuelto.value = cambio >= 0 ? cambio.toFixed(2) : "0.00";
    }
  } else {
    inputVuelto.value = "";
  }
});



// COMPORTAMIENTO VISUAL DE LOS CAMPOS DE PAGO SEGUN EL METODO DE PAGO SELECCIONADO
formaPago.addEventListener("change", (e) => {
  const valorSeleccionado = e.target.value;
  if (
    valorSeleccionado.trim() === "tarjeta" ||
    valorSeleccionado.trim() === "transferencia"
  ) {
    pagoRecibido.classList.add("hidden");
    vuelto.classList.add("hidden");
  } else {
    pagoRecibido.classList.remove("hidden");
    vuelto.classList.remove("hidden");
  }
});

// VALIDAR PAGO RECIBIDO ANTES DE COMPLETAR VENTA EN EFECTIVO
// ESTE METODO VALIDA CUANDO EL INPUT ESTA VACIO Y CUANDO EL PAGO RECIBIDO ES MENOR AL TOTAL A PAGAR, SI SE CUMPLE LAS CONDICIONES DE VALIDACION SE MUESTRA UN ALERT Y NO SE COMPLETA LA VENTA
document
  .getElementById("completarForm")
  .addEventListener("submit", async (e) => {
    const metodo = formaPago.value;
    if (metodo === "efectivo") {
      e.preventDefault();
      const pago = parseFloat(inputPagoRecibido.value);
      const res = await fetch("/ventas/venta/total");
      if (res.ok) {
        const total = await res.json();
        if (isNaN(pago) || pago < total) {
          alert(
            `El pago recibido (S/ ${pago || 0}) debe ser mayor o igual al total (S/ ${total})`,
          );
          return;
        }
      }
      e.target.submit();
    }
  });

// MOSTAR DATALIST CON COINCIDENCIAS DE PRODUCTOS
let timer;
input.addEventListener("input", () => {
  clearTimeout(timer);
  const q = input.value.trim();
  if (q.length < 2) {
    list.innerHTML = "";
    return;
  }

  timer = setTimeout(async () => {
    const res = await fetch(
      `/ventas/productos/sugerencia?q=${encodeURIComponent(q)}`,
    );
    const data = await res.json();
    list.innerHTML = data.map((n) => `<option value="${n}"></option>`).join("");
  }, 300);
});
