import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import Header from "../src/components/Header";

describe("Header - badge del carrito", () => {

  test("debería mostrar el contador incluso cuando el carrito está vacío", () => {
    const cart = [];

    // FIX: envolver Header dentro de un MemoryRouter
    render(
      <MemoryRouter>
        <Header
          cart={cart}
          removeFromCart={() => {}}
          decreaseQuantity={() => {}}
          increaseQuantity={() => {}}
          clearCart={() => {}}
          onOpenLogin={() => {}}
          onOpenRegister={() => {}}
        />
      </MemoryRouter>
    );
    
    expect(screen.getByText("0")).toBeInTheDocument();
  });

});
