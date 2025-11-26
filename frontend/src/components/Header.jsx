import React from 'react'
import { formatCLP } from '../utils/format'
import { Link, useLocation } from "react-router-dom"  // Detecta la ruta actual para resaltar el link activo

export default function Header({
  cart,
  removeFromCart,
  decreaseQuantity,
  increaseQuantity,
  clearCart,
  onOpenLogin,
  onOpenRegister
}) {

  // Detecta la ruta actual --> permite aplicar clase "active" en el menú
  const location = useLocation();

  // Total del carrito 
  const total = cart.reduce((acc, it) => acc + (it.precioNuevo ?? 0) * (it.quantity ?? 1), 0)

  // Conteo real de items (sumando quantity)
  const itemsCount = cart.reduce((acc, it) => acc + (it.quantity ?? 1), 0)

  return (
    <nav className="navbar navbar-expand-lg navbar-apple">
      <div className="container">

        {/* Marca y logo  */}
        <Link to="/" className="navbar-brand d-flex align-items-center gap-2">
          <img
            src="/img/logo.png"
            alt="Logo Huella Solidaria"
            className="navbar-logo"
          />
          <span className="navbar-brand-text">Huella Solidaria</span>
        </Link>

        {/* Toggler móvil */}
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

            {/* Menú con estado activo dinámico */}
            <li className="nav-item">
              <Link
                to="/"
                className={`nav-link ${location.pathname === "/" ? "active" : ""}`}
              >
                Inicio
              </Link>
            </li>

            <li className="nav-item">
              <Link
                to="/perro"
                className={`nav-link ${location.pathname === "/perro" ? "active" : ""}`}
              >
                Perros
              </Link>
            </li>

            <li className="nav-item">
              <Link
                to="/gato"
                className={`nav-link ${location.pathname === "/gato" ? "active" : ""}`}
              >
                Gatos
              </Link>
            </li>

            <li className="nav-item">
              <Link
                to="/exoticos"
                className={`nav-link ${location.pathname === "/exoticos" ? "active" : ""}`}
              >
                Exóticos
              </Link>
            </li>

            {/* CARRITO */}
            <li className="nav-item dropdown">
              <button
                type="button"
                className="btn btn-link nav-link position-relative cart-toggle-btn"
                data-bs-toggle="dropdown"
                data-bs-auto-close="outside"
                aria-expanded="false"
                aria-label={`Abrir carrito. ${itemsCount} artículo${itemsCount === 1 ? '' : 's'} en el carrito`}
              >
                <i className="bi bi-cart-fill" aria-hidden="true"></i>

                {/* FIX: siempre mostrar badge, incluso si itemsCount = 0 */}
                <span className="badge rounded-pill cart-count-badge cart-badge">
                  {itemsCount}
                  <span className="visually-hidden">artículos en el carrito</span>
                </span>
              </button>

              <div className="dropdown-menu dropdown-menu-end p-3 cart-dropdown">
                {cart.length === 0 ? (
                  <div className="text-center text-muted">Tu carrito está vacío</div>
                ) : (
                  <>
                    <div className="d-flex flex-column gap-2" style={{ maxHeight: 360, overflowY: 'auto' }}>
                      {cart.map((item) => (
                        <div key={item.id} className="cart-item">
                          <img
                            src={item.imagenSrc}
                            alt={item.titulo}
                            className="cart-item-img"
                            onError={(e) => { e.currentTarget.src = '/img/placeholder.webp' }}
                            loading="lazy"
                          />

                          <div>
                            <p className="cart-item-title">{item.titulo}</p>

                            <div className="cart-item-prices">
                              {item.precioAntiguo != null && (
                                <span className="old">{formatCLP(item.precioAntiguo)}</span>
                              )}
                              <span className="new">{formatCLP(item.precioNuevo ?? 0)}</span>
                            </div>

                            <div className="cart-actions d-flex align-items-center gap-2 mt-1">
                              <button
                                type="button"
                                className="btn btn-sm btn-lavanda-outline"
                                onClick={() => decreaseQuantity(item.id)}
                              >
                                -
                              </button>

                              <span className="px-2">{item.quantity ?? 1}</span>

                              <button
                                type="button"
                                className="btn btn-sm btn-lavanda"
                                onClick={() => increaseQuantity(item.id)}
                              >
                                +
                              </button>

                              <button
                                type="button"
                                className="btn btn-sm btn-outline-secondary ms-2"
                                onClick={() => removeFromCart(item.id)}
                              >
                                <i className="bi bi-trash"></i>
                              </button>
                            </div>
                          </div>

                          <div className="text-end fw-semibold">
                            {formatCLP((item.precioNuevo ?? 0) * (item.quantity ?? 1))}
                          </div>
                        </div>
                      ))}
                    </div>

                    <div className="cart-footer mt-3 pt-3 d-flex flex-column gap-2">
                      <div className="d-flex justify-content-between">
                        <span className="fw-semibold">Total</span>
                        <span className="fw-bold">{formatCLP(total)}</span>
                      </div>

                      <div className="d-flex gap-2">
                        <button className="btn btn-sm btn-outline-secondary flex-grow-1" onClick={clearCart}>
                          Vaciar
                        </button>
                        <button className="btn btn-sm btn-lavanda flex-grow-1">
                          Pagar
                        </button>
                      </div>
                    </div>
                  </>
                )}
              </div>
            </li>

            {/* LOGIN / REGISTRO */}
            <li className="nav-item ms-2">
              <button className="btn btn-sm btn-lavanda-outline" onClick={onOpenLogin}>Ingresar</button>
            </li>

            <li className="nav-item ms-2">
              <button className="btn btn-sm btn-lavanda" onClick={onOpenRegister}>Registrarme</button>
            </li>

          </ul>
        </div>
      </div>
    </nav>
  )
}
