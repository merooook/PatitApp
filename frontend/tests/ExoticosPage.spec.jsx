// TEST: ExoticosPage renderiza exoticos.js

import { render, screen } from "@testing-library/react";
import ExoticosPage from "../src/pages/ExoticosPage";
import exoticos from "../src/data/exoticos";

describe("ExoticosPage", () => {

  test("muestra productos exóticos correctamente", () => {
    render(<ExoticosPage productos={exoticos} addToCart={() => {}} />);

    const firstExotic = exoticos[0].titulo;
    expect(screen.getByText(firstExotic)).toBeInTheDocument();
  });

});
