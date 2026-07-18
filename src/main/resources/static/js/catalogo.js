const input = document.getElementById("buscarProducto");
const list = document.getElementById("sugerenciaProductos");
let timer;

const CART_KEY = "premiumPartsCart";
const WA_PHONE = "5180056245";

// Revisa si ya hay un carrito guardado en el navegador y lo carga, si no, crea uno vacío
function getCart() {
  return JSON.parse(localStorage.getItem(CART_KEY)) || [];
}

// Toma el arreglo actualizado del carrito, lo transforma en texto plano y lo guarda en el navegador
function saveCart(cart) {
  localStorage.setItem(CART_KEY, JSON.stringify(cart));
  updateCartBadge();
}

function findCartItem(cart, id) {
  return cart.find((item) => item.id == id);
}

// 1. Hace el llamado a getCart para obtener el estado actual del carrito
// 2. Mediante findCartItem revisa si el producto ya existe en el carrito, si ya existe, incrementa la cantidad, si no, lo agrega con cantidad 1
// 3. Guarda los cambios con saveCart
function addToCart(product) {
  const cart = getCart();
  const existente = findCartItem(cart, product.id);
  if (existente) {
    existente.cantidad++;
  } else {
    cart.push({
      id: product.id,
      nombre: product.nombre,
      precio: product.precio,
      cantidad: 1,
    });
  }
  saveCart(cart);
}

// Elimina un producto del carrito mediante su id, si la cantidad es mayor a 1, solo decrementa la cantidad, si es 0, lo elimina completamente del carrito
function removeFromCart(id) {
  let cart = getCart();
  const existente = findCartItem(cart, id);
  if (existente) {
    existente.cantidad--;
    if (existente.cantidad <= 0) {
      cart = cart.filter((item) => item.id != id);
    }
  }
  saveCart(cart);
}

function removeItemCompletely(id) {
  let cart = getCart();
  cart = cart.filter((item) => item.id != id);
  saveCart(cart);
}

function getProductQuantity(id) {
  const cart = getCart();
  const item = findCartItem(cart, id);
  return item ? item.cantidad : 0;
}

function syncCatalogCounters() {
  document.querySelectorAll(".contador-producto").forEach((contador) => {
    const btnSumar = contador.querySelector(".btn-sumar");
    const cantidad = contador.querySelector(".cantidad-producto");
    cantidad.textContent = getProductQuantity(btnSumar.dataset.id);
  });
}

//funcion actualizada
function updateCartBadge() {
  const cart = getCart();
  const total = cart.reduce((sum, item) => sum + item.cantidad, 0);
  
  // Buscamos ambos botones del navbar
  document.querySelectorAll("#cartBtnNav, #cartBtnMobile").forEach(cartBtn => {
    //lo mismo
    if (!cartBtn) return;
    let badge = cartBtn.querySelector(".cart-badge");
    
    if (total > 0) {
      if (!badge) {
        badge = document.createElement("span");
        badge.className = "cart-badge";
        
        Object.assign(badge.style, {
          position: "absolute",
          top: "-5px",
          right: "-5px",
          background: "#ef4444", // Rojo Tailwind
          color: "#ffffff",
          borderRadius: "9999px",
          width: "16px",
          height: "16px",
          fontSize: "9px",
          fontWeight: "800",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          lineHeight: "1",
          boxShadow: "0 1px 3px rgba(0,0,0,0.2)"
        });
        cartBtn.style.position = "relative";
        cartBtn.appendChild(badge);
      }
      badge.textContent = total > 99 ? "99+" : total;
    } else {
      badge?.remove();
    }
  });
}

function buildWhatsAppMessage() {
  const cart = getCart();
  if (cart.length === 0) return "";

  const lines = [];

  lines.push("*Hola, Premium Parts!*");
  lines.push("Me gustaría hacer el siguiente pedido:\n");

  cart.forEach((item) => {
    const subtotal = (item.precio * item.cantidad).toFixed(2);
    lines.push(`• *${item.nombre}*`);
    lines.push(`  Cantidad: ${item.cantidad} unidad(es)`);
    lines.push(`  Precio unitario: S/ ${item.precio.toFixed(2)}`);
    lines.push(`  Subtotal: S/ ${subtotal}`);
    lines.push("");
  });

  const total = cart.reduce(
    (sum, item) => sum + item.precio * item.cantidad,
    0
  );

  lines.push(`*TOTAL: S/ ${total.toFixed(2)}*`);
  lines.push("Espero su confirmación ");

  return lines.join("\n");
}


function renderCartModal() {
  const container = document.getElementById("cartItemsContainer");
  const cart = getCart();

  if (cart.length === 0) {
    container.innerHTML = `<div class="text-center py-8 text-gray-400 flex flex-col items-center">
      <ion-icon name="cart-outline" style="font-size: 3rem;"></ion-icon>
      <p class="mt-2 mb-0">Tu carrito está vacío</p>
    </div>`; // se cambió
    document.getElementById("cartTotal").textContent = "S/ 0.00";
    document.getElementById("btnWhatsApp").disabled = true;
    return;
  }

  let html = `<div class="overflow-x-auto w-full">
    <table class="w-full text-left text-sm text-gray-600 border-collapse">
      <thead class="bg-gray-100 text-xs uppercase text-gray-700 font-bold border-b border-gray-200">
        <tr>
          <th class="p-3 px-4">Producto</th>
          <th class="p-3 px-4 text-center">Cantidad</th>
          <th class="p-3 px-4 text-right">Precio</th>
          <th class="p-3 px-4 text-right">Subtotal</th>
          <th class="p-3 px-4 text-center">Acción</th>
        </tr>
      </thead>
      <tbody class="divide-y divide-gray-200 bg-white">`; //se cambio


  let total = 0;
  cart.forEach((item) => {
    const subtotal = item.precio * item.cantidad;
    total += subtotal;
    html += `<tr class="hover:bg-gray-50/70 transition">
      <td class="p-3 px-4 font-medium text-gray-950">${item.nombre}</td>
      <td class="p-3 px-4 text-center font-bold text-gray-900">${item.cantidad}</td>
      <td class="p-3 px-4 text-right whitespace-nowrap">S/ ${item.precio.toFixed(2)}</td>
      <td class="p-3 px-4 text-right font-semibold text-gray-900 whitespace-nowrap">S/ ${subtotal.toFixed(2)}</td>
      <td class="p-3 px-4 text-center">
        <button class="text-red-500 hover:text-red-700 transition remove-item cursor-pointer text-xl flex items-center justify-center mx-auto p-1 rounded hover:bg-red-50" data-id="${item.id}" type="button">
          <ion-icon name="trash-outline"></ion-icon>
        </button>
      </td>
    </tr>`; //se cambio
  });

  html += `</tbody></table></div>`;
  container.innerHTML = html;
  document.getElementById("cartTotal").textContent = `S/ ${total.toFixed(2)}`;
  document.getElementById("btnWhatsApp").disabled = false;

  container.querySelectorAll(".remove-item").forEach((btn) => {
    btn.addEventListener("click", () => {
      removeItemCompletely(btn.dataset.id);
      syncCatalogCounters();
      renderCartModal();
    });
  });
}
// se cambio del boot a tailwind
document.querySelectorAll("#cartBtnNav, #cartBtnMobile").forEach(btn => {
  btn.addEventListener("click", (e) => {
    e.preventDefault();
    renderCartModal();      // Dibuja los productos en la lista
    openModal('modalCarrito'); // Llama a la función nativa del HTML de Lucero
  });
});

document.getElementById("btnWhatsApp").addEventListener("click", () => {
  const msg = buildWhatsAppMessage();
  if (!msg) return;
  window.open(
    `https://wa.me/${WA_PHONE}?text=${encodeURIComponent(msg)}`,
    "_blank",
  );
});

document.querySelectorAll(".contador-producto").forEach((contador) => {
  const btnSumar = contador.querySelector(".btn-sumar");
  const btnRestar = contador.querySelector(".btn-restar");
  const cantidad = contador.querySelector(".cantidad-producto");
  const id = btnSumar.dataset.id;
  const nombre = btnSumar.dataset.nombre;
  const precio = parseFloat(btnSumar.dataset.precio);

  if (id) {
    cantidad.textContent = getProductQuantity(id);
  }

  btnSumar.addEventListener("click", () => {
    if (!id) return;
    addToCart({ id, nombre, precio });
    cantidad.textContent = getProductQuantity(id);
  });

  btnRestar.addEventListener("click", () => {
    if (!id) return;
    removeFromCart(id);
    cantidad.textContent = getProductQuantity(id);
  });
});

updateCartBadge();

input.addEventListener("input", () => {
  clearTimeout(timer);
  const q = input.value.trim();
  if (q.length < 2) {
    list.innerHTML = "";
    return;
  }

  timer = setTimeout(async () => {
    const res = await fetch(`/catalogo/sugerencia?q=${encodeURIComponent(q)}`);
    const data = await res.json();
    list.innerHTML = data.map((n) => `<option value="${n}"></option>`).join("");
  }, 300);
});
