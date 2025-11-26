// Test: Productos renderiza todas las tarjetas de productos correctamente

import { render, screen } from "@testing-library/react";
import Productos from "../src/components/Productos";

const mockProductos = [
  { id: 1, titulo: "A", imagenSrc: "/img/a.webp", precioNuevo: 1000 },
  { id: 2, titulo: "B", imagenSrc: "/img/b.webp", precioNuevo: 2000 },
];

describe("Productos component", () => {

  test("renderiza la cantidad correcta de tarjetas", () => {
    render(<Productos productos={mockProductos} addToCart={() => {}} />);

    const cards = screen.getAllByText(/^\w$/); // busca "A", "B"
    expect(cards.length).toBe(2);
  });

});
