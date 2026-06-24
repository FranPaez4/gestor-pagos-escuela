import Footer from "../components/Footer";
import { useForm } from "../hooks/useForm";
import type { LoginErrorFormType } from "../types/errortypes";
import { validateFormField } from "../utils/utils";
import { emailValidations } from "../utils/validation/email_validations";
import { passwordValidations } from "../utils/validation/password_validations";

// type LoginErrorFormType = {
//   email: string;
//   contrasena: string;
//   recordarme: string;
// };

export default function Login({ setVista }: { setVista: (v: string) => void }) {
  const { formData, setFormData, errors, setErrors, handleChange } = useForm({
    email: "",
    contrasena: "",
    recordarme: false,
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const newErrors: LoginErrorFormType = {
      email: "",
      contrasena: "",
      recordarme: "",
    };

    // valida aquí email y contraseña
    const emailError = validateFormField(formData.email, emailValidations);
    if (emailError.hasError) {
      newErrors.email = emailError.errorMessage;
    }

    const passwordError = validateFormField(
      formData.contrasena,
      passwordValidations,
    );
    if (passwordError.hasError) {
      newErrors.contrasena = passwordError.errorMessage;
    }

    setErrors(newErrors);

    if (!newErrors.email && !newErrors.contrasena) {
      console.log("Login listo para enviar:", formData);
    }
  };

  const handleReset = () => {
    setFormData({
      email: "",
      contrasena: "",
      recordarme: false,
    });
    setErrors({
      email: "",
      contrasena: "",
      recordarme: "",
    });
  };

  return (
    <div className="w-full min-h-screen bg-[url('/Image/_Background.png')] bg-cover bg-center bg-no-repeat bg-fixed font-[Verdana]">
      {/* MENÚ SUPERIOR */}
      <nav className="fixed top-0 left-0 z-[1000] flex w-full flex-row items-center justify-between box-border bg-[#423f3f] px-[50px] h-[70px]">
        <a
          href="#"
          onClick={(e) => {
            e.preventDefault();
            setVista("access");
          }}
          className="group flex h-[50px] w-[220px] items-center justify-center rounded-[10px] transition-all duration-300 ease-in-out hover:bg-[antiquewhite]"
        >
          <p className="m-0 font-[Impact] text-[30px] text-white transition-all duration-300 ease-in-out group-hover:text-black">
            ⬅ Volver
          </p>
        </a>
      </nav>

      {/* CONTENIDO PRINCIPAL */}
      <main className="relative flex flex-col items-center justify-center w-full min-h-screen pt-[100px] pb-[100px]">
        {/* CABECERA */}
        <div className="flex flex-col items-center text-center mb-[40px]">
          <h1 className="font-['Impact'] text-[40px] md:text-[80px] text-black drop-shadow-lg">
            ¡Hola! Identifícate
          </h1>
        </div>

        {/* CAJA DEL FORMULARIO */}
        <div className="bg-[#423f3f] p-[40px] rounded-[15px] border-[4px] border-white shadow-2xl w-full max-w-[600px] text-white">
          <form
            onSubmit={handleSubmit}
            noValidate
            className="flex flex-col gap-[20px]"
          >
            <fieldset className="border-none p-0 m-0 flex flex-col gap-[15px]">
              <legend className="font-['Impact'] text-[40px] text-[white] text-center w-full mb-[20px]">
                Datos de acceso
              </legend>

              <div className="flex flex-col gap-[5px]">
                <label
                  htmlFor="emailTxt"
                  className="text-[25px] font-['Impact'] tracking-wide"
                >
                  ⇨ Email:
                </label>
                <input
                  type="email"
                  id="emailTxt"
                  name="email"
                  placeholder="Introduzca su correo electrónico"
                  value={formData.email}
                  onChange={handleChange}
                  maxLength={40}
                  required
                  className={`bg-white p-[10px] rounded-[5px] text-black w-full outline-none focus:ring-4 transition-all ${errors.email ? "border-[3px] border-red-500 focus:ring-red-300" : "focus:ring-[antiquewhite]"}`}
                />
                {errors.email && (
                  <span className="text-red-400 font-bold text-sm">
                    {errors.email}
                  </span>
                )}
              </div>

              <div className="flex flex-col gap-[5px]">
                <label
                  htmlFor="contraseñaTxt"
                  className="text-[25px] font-['Impact'] tracking-wide"
                >
                  ⇨ Contraseña:
                </label>
                <input
                  type="password"
                  pattern="(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}"
                  id="contraseñaTxt"
                  name="contrasena"
                  title="La contraseña debe contener minúsculas, mayúsculas, números, caracteres especiales y tener al menos 8 caracteres."
                  placeholder="Introduzca su contraseña"
                  value={formData.contrasena}
                  onChange={handleChange}
                  minLength={8}
                  maxLength={30}
                  required
                  className={`bg-white p-[10px] rounded-[5px] text-black w-full outline-none focus:ring-4 transition-all ${errors.contrasena ? "border-[3px] border-red-500 focus:ring-red-300" : "focus:ring-[antiquewhite]"}`}
                />
                {errors.contrasena && (
                  <span className="text-red-400 font-bold text-sm">
                    {errors.contrasena}
                  </span>
                )}
              </div>
            </fieldset>

            {/* CHECKBOX RECORDARME Y OLVIDÉ CONTRASEÑA (Novedad) */}
            <div className="flex flex-col gap-[10px] mt-[10px]">
              <div className="flex items-center gap-[10px]">
                <input
                  type="checkbox"
                  id="recordarme"
                  name="recordarme"
                  checked={formData.recordarme}
                  onChange={handleChange}
                  className="w-[20px] h-[20px] cursor-pointer accent-[antiquewhite]"
                />
                <label
                  htmlFor="recordarme"
                  className="text-[16px] cursor-pointer hover:text-[antiquewhite]"
                >
                  Recordarme
                </label>
              </div>
              <a
                href="#"
                className="text-[14px] underline hover:text-[antiquewhite] italic text-right"
              >
                Olvidé mi contraseña
              </a>
            </div>

            {/* BOTONES */}
            <div className="flex flex-col items-center gap-[20px] mt-[20px]">
              <button
                type="submit"
                className="font-['Impact'] text-[25px] bg-white text-black px-[30px] py-[10px] rounded-[10px] transition-all hover:bg-[antiquewhite] hover:scale-105 cursor-pointer"
              >
                Continuar
              </button>
              <button
                type="button"
                onClick={handleReset}
                className="text-[14px] underline hover:text-[antiquewhite] italic text-right cursor-pointer self-end"
              >
                Borrar
              </button>
            </div>
          </form>
        </div>
      </main>

      {/* FOOTER */}
      <Footer />
    </div>
  );
}
