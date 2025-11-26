// Página de categoría "Exóticos"
// Se reutiliza el componente <Productos> para mantener el mismo diseño del sitio.

import React from "react";
import Productos from "../components/Productos";
import exoticos from "../data/exoticos"; // lista local

export default function ExoticosPage({ addToCart }) {
  return (
    <main>
      <div className="container mt-4">
        <h2 className="mb-4">Alimentos para Mascotas Exóticas</h2>

        {/* Se reusa el mismo componente de grilla que en las otras categorías */}
        <Productos productos={exoticos} addToCart={addToCart} />
      </div>
    </main>
  );
}
