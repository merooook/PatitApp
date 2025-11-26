// TEST: PerroPage renderiza dogs.js

import { render, screen } from "@testing-library/react";
import PerroPage from "../src/pages/PerroPage";
import dogs from "../src/data/dogs";

describe("PerroPage", () => {

  test("muestra los productos de la categoría perros", () => {
    render(<PerroPage addToCart={() => {}} />);

    const firstDog = dogs[0].titulo;
    expect(screen.getByText(firstDog)).toBeInTheDocument();
  });

});
