import React from 'react'
import { formatCLP } from '../utils/format' // CAMBIO: usar el formateador reutilizable

export default function Header({
  cart,
  removeFromCart,
  decreaseQuantity,
  increaseQuantity,
  clearCart,
  onOpenLogin,
  onOpenRegister,
  currentUser,
  onLogout
}) {
  // CAMBIO: total con reduce 
  const total = cart.reduce((acc, it) => acc + (it.precioNuevo ?? 0) * (it.quantity ?? 1), 0)

  // CAMBIO: contar unidades reales del carrito (no solo número de líneas).
  // Motivo: si un producto tiene quantity=3, el badge mostrará 3 (mejor UX).
  const itemsCount = cart.reduce((acc, it) => acc + (it.quantity ?? 1), 0)

  return (
    <nav className="navbar navbar-expand-lg navbar-apple">
      <div className="container">
        <a className="navbar-brand d-flex align-items-center gap-2" href="/">
          {/* CAMBIO: logo PNG agregado */}
          <img
            src="/img/logo.png"
            alt="Logo Huella Solidaria"
            className="navbar-logo"
          />
          <span className="navbar-brand-text">Huella Solidaria</span>
        </a>

        {/* CAMBIO: toggler básico para móviles (no afecta desktop, mejora UX en pantallas pequeñas). */}
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#mainNavbar"
          aria-controls="mainNavbar"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div id="mainNavbar" className="collapse navbar-collapse">
          <ul className="navbar-nav ms-auto align-items-center">
            {/* Otros links... */}

            {/* CAMBIO: botón del carrito con ícono controlado, badge lavanda y mejoras de accesibilidad */}
            <li className="nav-item dropdown">
              <button
                type="button"
                className="btn btn-link nav-link position-relative cart-toggle-btn"
                data-bs-toggle="dropdown"
                data-bs-auto-close="outside"
                aria-expanded="false"
                aria-label={`Abrir carrito. ${itemsCount} artículo${itemsCount === 1 ? '' : 's'} en el carrito`}
              >
                {/* CAMBIO: icono más “grueso” usando la versión rellena y color controlado por CSS */}
                <i className="bi bi-cart-fill" aria-hidden="true"></i>

                {itemsCount > 0 && (
                  /* CAMBIO: badge con clase propia para controlar posición y evitar que se corte */
                  <span className="badge rounded-pill cart-count-badge cart-badge">
                    {itemsCount}
                    <span className="visually-hidden">artículos en el carrito</span>
                  </span>
                )}
              </button>

              {/* CAMBIO: dropdown estilizado (se mantiene tu maquetación) */}
              <div className="dropdown-menu dropdown-menu-end p-3 cart-dropdown">
                {cart.length === 0 ? (
                  <div className="text-center text-muted">Tu carrito está vacío</div>
                ) : (
                  <>
                    <div className="d-flex flex-column gap-2" style={{ maxHeight: 360, overflowY: 'auto' }}>
                      {cart.map((item) => (
                        <div key={item.id} className="cart-item">
                          {/* CAMBIO: imagen pequeña y contenida */}
                          <img
                            src={item.imagenSrc}
                            alt={item.titulo}
                            className="cart-item-img"
                            onError={(e) => { e.currentTarget.src = '/img/placeholder.webp' }}
                            loading="lazy"
                          />

                          {/* Info */}
                          <div>
                            <p className="cart-item-title">{item.titulo}</p>
                            <div className="cart-item-prices">
                              {item.precioAntiguo != null && (
                                <span className="old">{formatCLP(item.precioAntiguo)}</span>
                              )}
                              <span className="new">{formatCLP(item.precioNuevo ?? 0)}</span>
                            </div>

                            {/* CAMBIO: controles más compactos */}
                            <div className="cart-actions d-flex align-items-center gap-2 mt-1">
                              <button
                                type="button"                                   // CAMBIO: evitar submits
                                className="btn btn-sm btn-lavanda-outline"
                                onClick={() => decreaseQuantity(item.id)}
                                aria-label={`Disminuir cantidad de ${item.titulo}`}
                              >
                                -
                              </button>
                              <span className="px-2" aria-live="polite">{item.quantity ?? 1}</span>
                              <button
                                type="button"                                   // CAMBIO: evitar submits
                                className="btn btn-sm btn-lavanda"
                                onClick={() => increaseQuantity(item.id)}
                                aria-label={`Aumentar cantidad de ${item.titulo}`}
                              >
                                +
                              </button>
                              <button
                                type="button"                                   // CAMBIO: evitar submits
                                className="btn btn-sm btn-outline-secondary ms-2"
                                onClick={() => removeFromCart(item.id)}
                                aria-label={`Quitar ${item.titulo} del carrito`}
                              >
                                <i className="bi bi-trash" aria-hidden="true"></i>
                              </button>
                            </div>
                          </div>

                          {/* Subtotal por item */}
                          <div className="text-end">
                            <div className="fw-semibold">
                              {formatCLP((item.precioNuevo ?? 0) * (item.quantity ?? 1))}
                            </div>
                          </div>
                        </div>
                      ))}
                    </div>

                    {/* Footer */}
                    <div className="cart-footer mt-3 pt-3 d-flex flex-column gap-2">
                      <div className="d-flex justify-content-between">
                        <span className="fw-semibold">Total</span>
                        <span className="fw-bold" aria-live="polite">{formatCLP(total)}</span>
                      </div>
                      <div className="d-flex gap-2">
                        <button
                          type="button"                                       // CAMBIO: evitar submits
                          className="btn btn-sm btn-outline-secondary flex-grow-1"
                          onClick={clearCart}
                        >
                          Vaciar
                        </button>
                        <button
                          type="button"                                       // CAMBIO: evitar submits
                          className="btn btn-sm btn-lavanda flex-grow-1"
                        >
                          Pagar
                        </button>
                      </div>
                    </div>
                  </>
                )}
              </div>
            </li>

            {/* Login / Registro */}
            {/* Login / Registro / Usuario */}
            {currentUser ? (
              <>
                <li className="nav-item ms-2 d-flex align-items-center">
                  <span className="me-2 fw-semibold">Hola, {currentUser.nombre}</span>
                </li>
                <li className="nav-item ms-2">
                  <button className="btn btn-sm btn-outline-danger" onClick={onLogout}>Cerrar Sesión</button>
                </li>
              </>
            ) : (
              <>
                <li className="nav-item ms-2">
                  <button className="btn btn-sm btn-lavanda-outline" onClick={onOpenLogin}>Ingresar</button>
                </li>
                <li className="nav-item ms-2">
                  <button className="btn btn-sm btn-lavanda" onClick={onOpenRegister}>Registrarme</button>
                </li>
              </>
            )}
          </ul>
        </div>
      </div>
    </nav>
  )
}
