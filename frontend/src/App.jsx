import React from 'react'
import './App.css'

// COMPONENTES DEL HOME
import Productos from './components/Productos'
import Carousel from './components/Carousel'

function App({ productos, addToCart }) {
  return (
    <main>
      <div className="container mt-4">
        <section className="my-5">
          <h2 className="mb-4">Ofertas</h2>
          <Productos productos={productos} addToCart={addToCart} />
        </section>

        <Carousel />
      </div>
    </main>
  )
}

export default App
