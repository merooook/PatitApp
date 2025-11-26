import React, { useState } from 'react'

// Estilos
import './App.css'

// Importación componentes
import Header from './components/Header'
import Productos from './components/Productos'
import Carousel from './components/Carousel'
import Footer from './components/Footer'
import RegistroModal from './components/RegistroModal'
import LoginModal from './components/LoginModal'

// CAMBIO: elimino import db (no se usa directamente).
// Motivo: ahora los productos vienen desde el hook useCart.
import { useCart } from './hooks/useCart'

function App() {
  const {
    data: productos,
    cart,
    addToCart,
    removeFromCart,
    decreaseQuantity,
    increaseQuantity,
    clearCart,
    loading,
    error
  } = useCart()

  const [isLoginModalOpen, setLoginModalOpen] = useState(false)
  const [isRegisterModalOpen, setRegisterModalOpen] = useState(false)
  const [currentUser, setCurrentUser] = useState(null)

  const handleLogin = (user) => {
    setCurrentUser(user);
    setLoginModalOpen(false);
  }

  const handleLogout = () => {
    setCurrentUser(null);
  }

  return (
    <>
      <Header
        cart={cart}
        removeFromCart={removeFromCart}
        decreaseQuantity={decreaseQuantity}
        increaseQuantity={increaseQuantity}
        clearCart={clearCart}
        onOpenLogin={() => setLoginModalOpen(true)}
        onOpenRegister={() => setRegisterModalOpen(true)}
        currentUser={currentUser}
        onLogout={handleLogout}
      />

      <main>
        <div className="container mt-4">
          <section className="my-5">
            <h2 className="mb-4">Ofertas</h2>
            {loading && <p className="text-center">Cargando productos...</p>}
            {error && <p className="text-center text-danger">Error al cargar productos: {error}</p>}
            {!loading && !error && (
              /* CAMBIO: verificar que <Productos> pase el objeto completo "producto"
                 a <ProductCard> para aprovechar imagenSrc y precios formateados. */
              <Productos productos={productos} addToCart={addToCart} />
            )}
          </section>

          <Carousel />
        </div>
      </main>

      <Footer />

      {/* Modales */}
      <RegistroModal
        isOpen={isRegisterModalOpen}
        onClose={() => setRegisterModalOpen(false)}
      />
      <LoginModal
        isOpen={isLoginModalOpen}
        onClose={() => setLoginModalOpen(false)}
        onLogin={handleLogin}
      />
    </>
  )
}

export default App
