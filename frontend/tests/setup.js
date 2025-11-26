// Este archivo se ejecuta antes de TODOS los tests de Vitest.
// Aquí cargo extensiones y mocks necesarios para evitar fallos en los componentes.

// FIX: Habilito matchers como toBeInTheDocument(), toHaveTextContent(), etc.
import "@testing-library/jest-dom";

// Vitest utilities
import { vi } from "vitest";


// ===========================================
// Mock de localStorage
// Necesario porque useCart usa localStorage y
// Vitest no lo incluye por defecto.
// ===========================================
class LocalStorageMock {
  constructor() {
    this.store = {};
  }

  clear() {
    this.store = {};
  }

  getItem(key) {
    return this.store[key] || null;
  }

  setItem(key, value) {
    this.store[key] = value.toString();
  }

  removeItem(key) {
    delete this.store[key];
  }
}

// Hago disponible el mock globalmente en los tests
global.localStorage = new LocalStorageMock();


// ===========================================
// Mock de Bootstrap.Modal
// Evita que el componente Header falle cuando se
// instancian los modales (Login y Registro).
// ===========================================
vi.mock("bootstrap", () => {
  return {
    Modal: class {
      show() {}
      hide() {}
    },
  };
});
