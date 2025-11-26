// Página de categoría "Perros"
// Carga los productos del archivo dogs.js --> reutiliza el componente Productos para mantener el diseño general

import React from "react";
import Productos from "../components/Productos";
import dogs from "../data/dogs";

export default function PerroPage({ addToCart }) {

  return (
    <main>
      <div className="container mt-4">
        <h2 className="mb-4">Alimentos para Perros</h2>

        {/* Reutilizo Productos.jsx para que la grilla mantenga mismo estilo */}
        <Productos productos={dogs} addToCart={addToCart} />
      </div>
    </main>
  );
}
