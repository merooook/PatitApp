// Página de categoría "Gatos"
// Reutiliza el componente Productos para mantener el diseño uniforme
// Importa la lista cats.js con sus imágenes locales

import React from "react";
import Productos from "../components/Productos";
import cats from "../data/cats";

export default function GatoPage({ addToCart }) {
  return (
    <main>
      <div className="container mt-4">
        <h2 className="mb-4">Alimentos para Gatos</h2>

        {/* Se reutiliza Productos.jsx para mostrar la grilla completa */}
        <Productos productos={cats} addToCart={addToCart} />
      </div>
    </main>
  );
}
