
import React from 'react';
import ProductCard from './cards/ProductCard';

export default function SnackCalcioPato({ onAdd }) {
  const data = {
    id: 7,
    imagenSrc: '/img/snack_calcio_perro.webp',
    categoria: 'Perro',
    titulo: 'Huesos de Calcio con Pato 90g',
    precioAntiguo: '4.100',
    precioNuevo: '3.300'
  };
  return <ProductCard {...data} onAdd={(id)=>onAdd?onAdd(id):alert(`Agregado: ${data.titulo}`)} />;
}
