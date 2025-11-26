import React from 'react';
import ProductCard from './cards/ProductCard';

export default function NutriqueBabyCat({ onAdd }) {
  const data = {
    id:  1,
    imagenSrc: '/img/nutrique_baby_cat.webp',
    categoria: 'Gato',
    titulo: 'Nutrique Baby Cat & Kitten Pavo 2Kg',
    precioAntiguo: '22.500',
    precioNuevo: '17.400'
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
