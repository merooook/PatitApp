import React from 'react';

export default function ProductCard({
  id,
  imagenSrc,
  categoria,
  titulo,
  precioAntiguo,
  precioNuevo,
  onAdd
}) {
  return (
    <div className="col-12 col-sm-6 col-lg-4 mb-4">
      <div className="card h-100 shadow-sm">
        <img
          src={imagenSrc}
          alt={titulo}
          className="card-img-top img-fluid"
          onError={(e) => { e.currentTarget.src = '/img/placeholder.webp'; }}
        />
        <div className="card-body d-flex flex-column">
          <span className="badge bg-secondary mb-2">{categoria}</span>
          <h5 className="card-title">{titulo}</h5>
          <div className="mt-auto">
            {precioAntiguo && (
              <div className="text-muted text-decoration-line-through">
                ${precioAntiguo}
              </div>
            )}
            <div className="fs-5 fw-semibold">${precioNuevo}</div>
            <button
              className="btn btn-light w-100 mt-3"
              onClick={() => onAdd?.(id)}
            >
              Agregar
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}