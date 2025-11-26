# 🐾 Huella Solidaria

Huella Solidaria es una aplicación web desarrollada con **React + Vite**, que combina un **e-commerce de productos para mascotas** con un enfoque solidario.  
El proyecto busca **fomentar la adopción responsable y el apoyo a fundaciones** dedicadas al bienestar animal, destinando un porcentaje de las ventas a organizaciones asociadas.

---

## 💡 Objetivo del proyecto

El propósito principal es ofrecer una experiencia de compra moderna, funcional y empática, donde las personas puedan adquirir productos para sus mascotas y, al mismo tiempo, contribuir con una causa social.

Esta versión consolida la **versión funcional completa del proyecto**, con una estructura optimizada, un diseño coherente y una implementación robusta del carrito de compras.

---

## 🧱 Tecnologías utilizadas

| Tipo | Tecnología / Librería |
|------|------------------------|
| Frontend | React + Vite |
| Lenguaje principal | JavaScript (JSX) |
| Estilos | Bootstrap 5 + CSS personalizado (paleta lavanda pastel) |
| Íconos | Bootstrap Icons |
| Control de versiones | Git + GitHub |
| Gestión del carrito | React Hooks + LocalStorage |
| Formateo de precios | `Intl.NumberFormat('es-CL', { style: 'currency', currency: 'CLP' })` |

---

## 🛒 Características principales

### 🧩 1. Carrito de compras funcional
- Añadir, eliminar, aumentar o disminuir cantidad de productos.
- Persistencia del carrito en `localStorage`.
- Límite de cantidad por producto.
- Contador dinámico de ítems con *badge* visible.
- Cálculo automático de subtotales y total general.

### 💰 2. Formateo de precios en pesos chilenos (CLP)
- Todos los valores se muestran con separación de miles (`$XX.XXX`).
- Implementado mediante un formateador reutilizable (`utils/format.js`).

### 🎨 3. Diseño visual consistente
- Interfaz en tonos lavanda pastel.
- Navbar con logo responsive e ícono de carrito en negro con sombra.
- Tarjetas de producto limpias, responsivas y legibles.
- Tipografía y botones coherentes con la paleta de colores.

### 🐶 4. Base de datos local (`data/db.js`)
- Listado de productos con ID, nombre, categoría, imagen y precios.
- Estructura adaptable a futuras integraciones con backend o API.

### 🧭 5. Componentización ordenada
- Separación modular de componentes:  
  `Header`, `Footer`, `Carousel`, `Productos`, `ProductCard`, `RegistroModal`, `LoginModal`, etc.
- Carpeta `/hooks` dedicada a lógica de negocio (`useCart`).
- Carpeta `/utils` para funciones reutilizables.

---

## 🧩 Estructura de carpetas principal

```
Huella_Solidaria/
├── public/
│   ├── img/                 # Imágenes y logo del proyecto
│   └── favicon.ico
├── src/
│   ├── assets/              # Archivos auxiliares (si los hubiera)
│   ├── components/          # Componentes React (UI)
│   ├── data/db.js           # Base de datos local de productos
│   ├── hooks/useCart.js     # Lógica del carrito
│   ├── utils/format.js      # Formateador CLP
│   ├── App.jsx              # Componente principal
│   ├── main.jsx             # Punto de entrada de la app
│   ├── styles.css           # Estilos globales
│   ├── App.css              # Estructura y animaciones
│   └── index.css            # Estilos base por defecto
├── vite.config.js
└── package.json
```

---

## 🧰 Instalación y ejecución

### 1️⃣ Clonar el repositorio
```bash
git clone https://github.com/fernandalazo/HSolidaria.git
cd HSolidaria
```

### 2️⃣ Instalar dependencias
```bash
npm install
```

### 3️⃣ Ejecutar en entorno local
```bash
npm run dev
```

### 4️⃣ Abrir en navegador
[http://localhost:5173/](http://localhost:5173/)


## 💜 Créditos

Proyecto académico desarrollado por **Fernanda Lazo**, **Matias Barraza** y **Mertixell Arroyo** 
Estudiantes de **Duoc UC – Ingeniería en Informática**  
📍 Viña del Mar, Chile  
✨ En el marco de la asignatura **Desarrollo FullStack Java II**
