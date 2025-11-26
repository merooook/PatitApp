import React from 'react';
import ProductCard from './cards/ProductCard';

export default function VitalcanUrinary({ onAdd }) {
  const data = {
    id: 308,
    imagenSrc: '/img/vitalcan_urinary.webp',
    categoria: 'Gato',
    titulo: 'Feline Urinary Health 2Kg',
    precioAntiguo: '23.900',
    precioNuevo: '20.100'
  };
  return <ProductCard {...data} onAdd={(id)=>onAdd?onAdd(id):alert(`Agregado: ${data.titulo}`)} />;
}
