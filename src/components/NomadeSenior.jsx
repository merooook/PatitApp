import React from 'react';
import ProductCard from './cards/ProductCard';

export default function NomadeSenior({ onAdd }) {
  const data = {
    id: 3,
    imagenSrc: '/img/nomade_senior.webp',
    categoria: 'Perro',
    titulo: 'Nomade Senior 15Kg',
    precioAntiguo: '35.000',
    precioNuevo: '32.000'
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
