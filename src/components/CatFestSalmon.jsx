
import React from 'react';
import ProductCard from './cards/ProductCard';

export default function CatFestSalmon({ onAdd }) {
  const data = {
    id: 5,
    imagenSrc: '/img/catfest_salmon.webp',
    categoria: 'Gato',
    titulo: 'Snack Pillows Crema de Salmón 30g',
    precioAntiguo: '1.500',
    precioNuevo: '1.250'
  };
  return <ProductCard {...data} onAdd={(id)=>onAdd?onAdd(id):alert(`Agregado: ${data.titulo}`)} />;
}
