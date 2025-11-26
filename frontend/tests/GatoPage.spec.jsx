// TEST: GatoPage renderiza cats.js

import { render, screen } from "@testing-library/react";
import GatoPage from "../src/pages/GatoPage";
import cats from "../src/data/cats";

describe("GatoPage", () => {

  test("muestra al menos un producto de gatos", () => {
    render(<GatoPage productos={cats} addToCart={() => {}} />);

    const firstCat = cats[0].titulo;
    expect(screen.getByText(firstCat)).toBeInTheDocument();
  });

});
