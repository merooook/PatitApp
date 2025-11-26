/*ESTE NO ES UN COMPONENTE DE RUTA, SOLO RENDERIZA TARJETAS DE PRODUCTOS*/
import React from 'react'
import ProductCard from './ProductCard.jsx' // CAMBIO: extensión explícita

// CAMBIO: componente mínimo que respeta props y pasa el objeto completo a ProductCard
export default function Productos({ productos, addToCart }) {
  if (!Array.isArray(productos)) return null

  return (
    <div className="row g-3">
      {productos.map((p) => (
        <ProductCard key={p.id} producto={p} addToCart={addToCart} />
      ))}
    </div>
  )
}
