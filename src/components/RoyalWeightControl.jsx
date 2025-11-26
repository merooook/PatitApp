import React from 'react';
import ProductCard from './cards/ProductCard';

export default function RoyalWeightControl({ onAdd }) {
  const data = {
    id: 2,
    imagenSrc: '/img/royal_weight_control.webp',
    categoria: 'Gato',
    titulo: 'Royal Canin Gato Adulto Castrados Weight Control 1.5Kg (Seco)',
    precioAntiguo: '20.000',
    precioNuevo: '17.580'
  };

  return (
    <ProductCard
      id={data.id}
      imagenSrc={data.imagenSrc}
      categoria={data.categoria}
      titulo={data.titulo}
      precioAntiguo={data.precioAntiguo}
      precioNuevo={data.precioNuevo}
      onAdd={(id) => onAdd ? onAdd(id) : alert(`Agregado: ${data.titulo}`)}
    />
  );
}
