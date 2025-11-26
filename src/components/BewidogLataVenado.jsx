
import React from 'react';
import ProductCard from './cards/ProductCard';

export default function BewidogLataVenado({ onAdd }) {
  const data = {
    id: 11,
    imagenSrc: '/img/bewidog_lata_perro.webp',
    categoria: 'Perro',
    titulo: 'Bewidog Lata Perro Adulto Venado 800g',
    precioAntiguo: '5.100',
    precioNuevo: '4.250'
  };
  return <ProductCard {...data} onAdd={(id)=>onAdd?onAdd(id):alert(`Agregado: ${data.titulo}`)} />;
}
