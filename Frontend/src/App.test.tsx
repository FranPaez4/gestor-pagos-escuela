import { describe, it, expect, beforeAll, afterEach, afterAll } from "vitest";
import { server } from "./mocks/server"; // Importas el server que acabas de crear

// 1. Esto prepara a MSW para interceptar las peticiones antes de los tests
beforeAll(() => server.listen());
afterEach(() => server.resetHandlers());
afterAll(() => server.close());

describe("Registro de Usuario", () => {
  it("debería recibir una respuesta exitosa del mock", async () => {
    // Simulamos la petición POST que hace tu frontend
    const response = await fetch("/auth/register", {
      method: "POST",
      body: JSON.stringify({ email: "test@ejemplo.com" }),
    });

    const data = await response.json();

    // Verificamos que el mock ha funcionado
    expect(response.status).toBe(201);
    expect(data.message).toBe("Usuario registrado exitosamente");
  });
});
