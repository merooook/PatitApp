// Test: ProductCard muestra título y precio correctamente

import { render, screen } from "@testing-library/react";
import ProductCard from "../src/components/ProductCard";

const mockProducto = {
  id: 1,
  titulo: "Nutrique Puppy",
  imagenSrc: "/img/dog_01.webp",
  precioNuevo: 9990
};

describe("ProductCard", () => {

  test("muestra título y precio", () => {
    render(<ProductCard producto={mockProducto} addToCart={() => {}} />);

    expect(screen.getByText("Nutrique Puppy")).toBeInTheDocument();
    expect(screen.getByText("$9.990")).toBeInTheDocument(); 
  });

});
