


INSERT INTO categorias (nombre) VALUES ('Aceites y Lubricantes');
INSERT INTO categorias (nombre) VALUES ('Filtros');
INSERT INTO categorias (nombre) VALUES ('Frenos');
INSERT INTO categorias (nombre) VALUES ('Baterías');
INSERT INTO categorias (nombre) VALUES ('Accesorios');
INSERT INTO categorias (nombre) VALUES ('Suspensión');
INSERT INTO categorias (nombre) VALUES ('Iluminación');
INSERT INTO categorias (nombre) VALUES ('Neumáticos');


INSERT INTO productos (nombre, categoria_id, stock, sku, precio_compra, precio_venta, descripcion, veces_vendido) VALUES
('Aceite Castrol GTX 5W-30', 1, 50, 'ACE-001', 92.70, 120.50, 'Aceite de motor de tecnología sintética para una máxima protección contra el desgaste.', 0),
('Aceite Mobil Super 10W-40', 1, 40, 'ACE-002', 76.10, 98.90, 'Aceite semisintético premium diseñado para prolongar la vida útil del motor.', 0),
('Aceite Shell Helix Ultra 5W-40', 1, 35, 'ACE-003', 111.50, 145.00, 'Lubricante 100% sintético formulado para un rendimiento superior en condiciones extremas.', 0),
('Aceite Valvoline 20W-50', 1, 25, 'ACE-004', 68.80, 89.50, 'Aceite mineral de alta viscosidad ideal para vehículos de alto kilometraje.', 0),
('Lubricante Total Quartz 9000', 1, 30, 'ACE-005', 103.80, 135.00, 'Aceite sintético avanzado que reduce la fricción y optimiza el consumo de combustible.', 0),

('Filtro de aceite Toyota', 2, 45, 'FIL-001', 18.20, 25.50, 'Filtro de aceite original Toyota para mantener el motor libre de impurezas.', 0),
('Filtro de aire Nissan', 2, 38, 'FIL-002', 25.00, 35.00, 'Filtro de aire de alto flujo que mejora la aceleración y la eficiencia del motor.', 0),
('Filtro de combustible Hyundai', 2, 22, 'FIL-003', 30.40, 42.50, 'Filtro de combustible diseñado para proteger el sistema de inyección y optimizar el consumo.', 0),
('Filtro de cabina Kia', 2, 28, 'FIL-004', 21.40, 30.00, 'Filtro de carbón activado que purifica el aire dentro del habitáculo, bloqueando polvo y olores.', 0),
('Filtro de aceite Bosch', 2, 50, 'FIL-005', 19.90, 27.90, 'Filtro de aceite de calidad premium con alta capacidad de retención de suciedad.', 0),

('Pastillas de freno Bosch', 3, 20, 'FRE-001', 110.70, 155.00, 'Juego de pastillas de freno de alto rendimiento con reducción de ruido y vibración.', 0),
('Discos de freno Brembo', 3, 15, 'FRE-002', 178.60, 250.00, 'Discos de freno ventilados para una disipación de calor óptima y frenado seguro.', 0),
('Zapatas de freno Toyota', 3, 18, 'FRE-003', 92.90, 130.00, 'Zapatas de freno originales para tambor, garantizando durabilidad y precisión.', 0),
('Líquido de frenos DOT4', 3, 60, 'FRE-004', 24.50, 35.50, 'Líquido de frenos sintético con alto punto de ebullición para máxima seguridad.', 0),
('Kit de frenos delanteros', 3, 10, 'FRE-005', 228.60, 320.00, 'Kit completo que incluye discos y pastillas cerámicas para el eje delantero.', 0),

('Batería Bosch 12V 60Ah', 4, 12, 'BAT-001', 259.30, 350.00, 'Batería de libre mantenimiento con excelente potencia de arranque en frío.', 0),
('Batería Etna 12V 70Ah', 4, 10, 'BAT-002', 311.10, 420.00, 'Batería de larga duración ideal para vehículos con alto requerimiento eléctrico.', 0),
('Batería Record 12V 55Ah', 4, 14, 'BAT-003', 229.60, 310.00, 'Batería confiable y resistente, diseñada para soportar cambios drásticos de temperatura.', 0),
('Cargador portátil batería', 4, 8, 'BAT-004', 200.00, 280.00, 'Arrancador portátil (Jump Starter) compacto con linterna y puertos USB adicionales.', 0),
('Probador de batería digital', 4, 16, 'BAT-005', 67.90, 95.00, 'Herramienta de diagnóstico para medir el estado de carga y vida útil de la batería.', 0),

('Soporte para celular', 5, 80, 'ACC-001', 15.60, 25.00, 'Soporte magnético universal para montaje en la rejilla de ventilación del auto.', 0),
('Cargador USB para auto', 5, 65, 'ACC-002', 11.60, 18.50, 'Cargador de carga rápida con doble puerto USB adaptable al encendedor.', 0),
('Ambientador vainilla', 5, 100, 'ACC-003', 7.50, 12.00, 'Ambientador colgante de larga duración con fragancia clásica a vainilla.', 0),
('Cobertor para volante', 5, 35, 'ACC-004', 25.80, 40.00, 'Funda de cuero sintético antideslizante para proteger y decorar el volante.', 0),
('Alfombras universales', 5, 20, 'ACC-005', 48.40, 75.00, 'Juego de 4 alfombras de goma resistentes, recortables y fáciles de lavar.', 0),

('Amortiguador delantero KYB', 6, 14, 'SUS-001', 200.00, 280.00, 'Amortiguador a gas presurizado para restaurar la estabilidad original del vehículo.', 0),
('Amortiguador trasero Monroe', 6, 16, 'SUS-002', 185.70, 260.00, 'Amortiguador de reemplazo directo que ofrece confort y control superior en ruta.', 0),
('Resorte de suspensión Toyota', 6, 12, 'SUS-003', 107.10, 150.00, 'Resorte helicoidal de acero de alta resistencia para la suspensión delantera.', 0),
('Brazo de suspensión Nissan', 6, 10, 'SUS-004', 125.00, 175.00, 'Brazo de control inferior completo con rótula y bujes preinstalados.', 0),
('Kit de suspensión deportiva', 6, 6, 'SUS-005', 607.10, 850.00, 'Conjunto de espirales y amortiguadores para reducir la altura y mejorar la aerodinámica.', 0),

('Faro delantero LED', 7, 18, 'LUC-001', 157.10, 220.00, 'Faro de reemplazo con tecnología LED para una mayor visibilidad y alcance nocturno.', 0),
('Foco halógeno H4', 7, 50, 'LUC-002', 13.30, 20.00, 'Foco halógeno estándar de luz blanca cálida para luces principales.', 0),
('Luces LED interiores', 7, 45, 'LUC-003', 29.00, 45.00, 'Kit de tiras LED RGB con control remoto y sincronización de música para el habitáculo.', 0),
('Neblineros universales', 7, 15, 'LUC-004', 128.60, 180.00, 'Faros antiniebla de alta penetración ideales para lluvia intensa o neblina.', 0),
('Barra LED off-road', 7, 8, 'LUC-005', 278.60, 390.00, 'Barra de luces LED de alta potencia con carcasa de aluminio, ideal para rutas todoterreno.', 0),

('Neumático Michelin 205/55R16', 8, 24, 'NEU-001', 323.10, 420.00, 'Llanta de alto desempeño con excelente agarre en suelo mojado y frenado preciso.', 0),
('Neumático Bridgestone 195/65R15', 8, 20, 'NEU-002', 300.00, 390.00, 'Llanta duradera y de bajo ruido de rodadura, ideal para el manejo urbano diario.', 0),
('Neumático Pirelli 17 pulgadas', 8, 18, 'NEU-003', 392.30, 510.00, 'Neumático deportivo que ofrece máxima estabilidad y control en curvas pronunciadas.', 0),
('Neumático Goodyear Wrangler', 8, 14, 'NEU-004', 476.90, 620.00, 'Llanta todoterreno (A/T) robusta con paredes laterales reforzadas para off-road.', 0),
('Kit reparador de neumáticos', 8, 30, 'NEU-005', 43.30, 65.00, 'Set de herramientas y tarugos (mechas) para la reparación rápida de pinchazos.', 0),

('Aceite Repsol Elite 5W-30', 1, 28, 'ACE-006', 96.20, 125.00, 'Lubricante sintético de última generación que cumple con las exigencias de fabricantes europeos.', 0),
('Aceite Motul 8100 X-clean', 1, 18, 'ACE-007', 138.50, 180.00, 'Aceite 100% sintético diseñado específicamente para motores con filtro de partículas (DPF).', 0),
('Filtro Mann Filter', 2, 26, 'FIL-006', 27.10, 38.00, 'Filtro de aire de calidad de equipo original para máxima protección contra contaminantes.', 0),
('Pastillas Brembo Cerámicas', 3, 11, 'FRE-006', 150.00, 210.00, 'Pastillas premium de compuesto cerámico que reducen drásticamente el polvo en los rines.', 0),
('Batería portátil para auto', 4, 9, 'BAT-006', 333.30, 450.00, 'Estación de energía portátil con pinzas inteligentes y protección contra polaridad inversa.', 0),

('Organizador para maletera', 5, 40, 'ACC-006', 36.70, 55.00, 'Caja plegable con múltiples compartimentos para mantener el baúl ordenado y los objetos seguros.', 0),
('Cámara de retroceso HD', 5, 22, 'ACC-007', 110.30, 160.00, 'Cámara trasera a prueba de agua con visión nocturna y líneas guías de estacionamiento.', 0),
('Sensor de estacionamiento', 5, 19, 'ACC-008', 100.00, 145.00, 'Sistema de 4 sensores ultrasónicos con pequeña pantalla LED indicadora y alerta sonora.', 0),

('Amortiguador deportivo Bilstein', 6, 7, 'SUS-006', 342.90, 480.00, 'Amortiguador de alto desempeño diseñado para entusiastas de la conducción deportiva.', 0),
('Kit elevación suspensión', 6, 5, 'SUS-007', 657.10, 920.00, 'Kit de levante (lift kit) de 2 pulgadas para mejorar el despeje del suelo en camionetas y 4x4.', 0),

('Foco LED H7', 7, 44, 'LUC-006', 22.60, 35.00, 'Bombilla LED ultrabrillante tipo H7 con disipador térmico de aluminio integrado.', 0),
('Luces DRL universales', 7, 17, 'LUC-007', 89.70, 130.00, 'Luces de conducción diurna LED de bajo consumo, mejorando la visibilidad del vehículo de día.', 0),

('Neumático Continental Sport', 8, 12, 'NEU-006', 430.80, 560.00, 'Neumático premium con tecnología de frenado superior a altas velocidades y pavimento mojado.', 0),
('Compresor portátil de aire', 8, 21, 'NEU-007', 96.60, 140.00, 'Inflador de llantas digital de 12V con apagado automático al alcanzar la presión programada.', 0);
