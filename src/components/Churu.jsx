import React from 'react';
import ProductCard from './cards/ProductCard';

export default function Churu({ onAdd }) {
  const data = {
    id: 4,
    imagenSrc: '/img/churu_gato_pollo.webp',
    categoria: 'Gato',
    titulo: 'Churu Gato Pollo & Pollo Ostión (10 und)',
    precioAntiguo: '5.990',
    precioNuevo: '5.490'
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

