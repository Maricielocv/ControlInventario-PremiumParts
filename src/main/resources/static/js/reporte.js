const colores = [
  '#2563EB', '#16A34A', '#D97706', '#DC2626', '#8B5CF6',
  '#EC4899', '#06B6D4', '#F97316', '#14B8A6', '#A855F7'
];
const input = document.getElementById("buscarProducto");
const list = document.getElementById("sugerenciaProductos");
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
      `/reporte/sugerencia?q=${encodeURIComponent(q)}`,
    );
    const data = await res.json();
    list.innerHTML = data.map((n) => `<option value="${n}"></option>`).join("");
  }, 300);
});

document.addEventListener('DOMContentLoaded', () => {
  const top10Canvas = document.getElementById('top10Chart');
  if (top10Canvas) {
    const labels = [];
    const data = [];
    document.querySelectorAll('#top10DataContainer > div').forEach(el => {
      labels.push(el.getAttribute('data-name'));
      data.push(Number(el.getAttribute('data-value')));
    });

    if (labels.length > 0) {
      new Chart(top10Canvas, {
        type: 'bar',
        data: {
          labels,
          datasets: [{
            label: 'Unidades vendidas',
            data,
            backgroundColor: colores.slice(0, data.length),
            borderRadius: 6
          }]
        },
        options: {
          responsive: true,
          plugins: { legend: { display: false } },
          scales: {
            y: {
              beginAtZero: true,
              ticks: { stepSize: 1, color: '#A1A1AA' },
              grid: { color: '#27272A' }
            },
            x: {
              ticks: { color: '#A1A1AA' },
              grid: { display: false }
            }
          }
        }
      });
    }
  }

  const categoriaCanvas = document.getElementById('categoriaChart');
  if (categoriaCanvas) {
    const labels = [];
    const data = [];
    document.querySelectorAll('#categoriaDataContainer > div').forEach(el => {
      labels.push(el.getAttribute('data-name'));
      data.push(Number(el.getAttribute('data-value')));
    });

    if (labels.length > 0) {
      new Chart(categoriaCanvas, {
        type: 'doughnut',
        data: {
          labels,
          datasets: [{
            data,
            backgroundColor: colores.slice(0, data.length),
            borderWidth: 0
          }]
        },
        options: {
          responsive: true,
          plugins: {
            legend: {
              position: 'bottom',
              labels: { color: '#A1A1AA', padding: 12 }
            }
          }
        }
      });
    }
  }
});
