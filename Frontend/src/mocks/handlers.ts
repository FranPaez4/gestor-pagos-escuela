import { http, HttpResponse } from "msw";

interface RegisterBody {
  email: string;
}

export const handlers = [
  // --- UC-01: Registro de Usuario ---
  http.post("/auth/register", async ({ request }) => {
    const data = (await request.json()) as RegisterBody;
    console.log("Registro recibido en el Mock:", data);

    // Devolvemos un 201 Created simulando éxito
    return HttpResponse.json(
      {
        message: "Usuario registrado exitosamente",
        user: { id: 1, email: data.email },
      },
      { status: 201 },
    );
  }),

  // --- OTRAS RUTAS ---
  http.get("/api/test", () => {
    return HttpResponse.json({ mensaje: "Mock funcionando" });
  }),
];
