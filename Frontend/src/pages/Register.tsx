import Footer from "../components/Footer";
import { useForm } from "../hooks/useForm";
import type { RegisterErrorFormType } from "../types/formtypes";
import { validateFormField } from "../utils/utils";
import { emailValidations } from "../utils/validation/email_validations";
import { passwordValidations } from "../utils/validation/password_validations";
import { surnameValidations } from "../utils/validation/surname_validations";
import { userValidations } from "../utils/validation/user_validations";

export default function Register({
  setVista,
}: {
  setVista: (v: string) => void;
}) {
  const { formData, setFormData, errors, setErrors, handleChange } = useForm({
    nombre: "",
    apellidos: "",
    email: "",
    contrasena: "",
    rgpd: false,
  });
  // Función para validar al darle al botón "Continuar"
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault(); // Evita que la página recargue de golpe
    const newErrors: RegisterErrorFormType = {
      nombre: "",
      apellidos: "",
      email: "",
      contrasena: "",
      rgpd: "",
    };
    let hasError = false;

    // if (!formData.nombre.trim()) {
    //   newErrors.nombre = "El nombre es obligatorio.";
    //   hasError = true;
    // }
    const nameError = validateFormField(formData.nombre, userValidations);
    if (nameError.hasError) {
      newErrors.nombre = nameError.errorMessage;
      hasError = nameError.hasError;
    }
    console.log(nameError);

    const surnameError = validateFormField(
      formData.apellidos,
      surnameValidations,
    );

    if (surnameError.hasError) {
      newErrors.apellidos = surnameError.errorMessage;
      hasError = surnameError.hasError;
    }
    const emailError = validateFormField(formData.email, emailValidations);
    if (emailError.hasError) {
      newErrors.email = emailError.errorMessage;
      hasError = emailError.hasError;
    }
    const passwordError = validateFormField(
      formData.contrasena,
      passwordValidations,
    );
    if (passwordError.hasError) {
      newErrors.contrasena = passwordError.errorMessage;
      hasError = passwordError.hasError;
    }
    if (!formData.rgpd) {
      newErrors.rgpd = "Debes aceptar los términos y condiciones.";
      hasError = true;
    }

    setErrors(newErrors);

    if (!hasError) {
      console.log("Datos listos para enviar:", formData);
      alert("¡Validación correcta!");
    }
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
            ¡Crea una cuenta!
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
                Datos personales
              </legend>

              <div className="flex flex-col gap-[5px]">
                <label
                  htmlFor="nombreTxt"
                  className="text-[25px] font-['Impact'] tracking-wide"
                >
                  ⇨ Nombre:
                </label>
                <input
                  type="text"
                  id="nombreTxt"
                  name="nombre"
                  placeholder="Introduzca su nombre"
                  value={formData.nombre}
                  onChange={handleChange}
                  minLength={3}
                  maxLength={25}
                  required
                  className={`bg-white p-[10px] rounded-[5px] text-black w-full outline-none focus:ring-4 transition-all ${errors.nombre ? "border-[3px] border-red-500 focus:ring-red-300" : "focus:ring-[antiquewhite]"}`}
                />
                {errors.nombre && (
                  <span className="text-red-400 font-bold text-sm">
                    {errors.nombre}
                  </span>
                )}
              </div>

              <div className="flex flex-col gap-[5px]">
                <label
                  htmlFor="apellidosTxt"
                  className="text-[25px] font-['Impact'] tracking-wide"
                >
                  ⇨ Apellidos:
                </label>
                <input
                  type="text"
                  id="apellidosTxt"
                  name="apellidos"
                  placeholder="Introduzca sus apellidos"
                  value={formData.apellidos}
                  onChange={handleChange}
                  minLength={3}
                  maxLength={40}
                  required
                  className={`bg-white p-[10px] rounded-[5px] text-black w-full outline-none focus:ring-4 transition-all ${errors.apellidos ? "border-[3px] border-red-500 focus:ring-red-300" : "focus:ring-[antiquewhite]"}`}
                />
                {errors.apellidos && (
                  <span className="text-red-400 font-bold text-sm">
                    {errors.apellidos}
                  </span>
                )}
              </div>

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
                  placeholder="Por favor, elabore una contraseña"
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

            {/* CHECKBOX RGPD (Novedad) */}
            <div className="flex flex-col gap-[5px] mt-[10px]">
              <div className="flex items-center gap-[10px] mt-[10px]">
                <input
                  type="checkbox"
                  id="rgpd"
                  name="rgpd"
                  checked={!!formData.rgpd}
                  onChange={handleChange}
                  className={`w-[20px] h-[20px] cursor-pointer accent-[antiquewhite] ${errors.rgpd ? "outline outline-[2px] outline-red-500" : ""}`}
                />
                <label htmlFor="rgpd" className="text-[15px]">
                  Acepto{" "}
                  <a
                    href="#"
                    className="underline hover:text-[antiquewhite] font-bold"
                  >
                    RGPD y Política de Privacidad
                  </a>
                </label>
              </div>
              {errors.rgpd && (
                <span className="text-red-400 font-bold text-sm">
                  {errors.rgpd}
                </span>
              )}
            </div>

            {/* BOTONES */}
            <div className="flex flex-col justify-center items-center gap-[15px] mt-[20px] w-full">
              <button
                type="submit"
                className="font-['Impact'] text-[25px] bg-white text-black px-[30px] py-[10px] rounded-[10px] transition-all hover:bg-[antiquewhite] hover:scale-105 cursor-pointer"
              >
                Continuar
              </button>
              <button
                type="button"
                onClick={() => {
                  setFormData({
                    nombre: "",
                    apellidos: "",
                    email: "",
                    contrasena: "",
                    rgpd: false,
                  });
                  setErrors({
                    nombre: "",
                    apellidos: "",
                    email: "",
                    contrasena: "",
                    rgpd: "",
                  });
                }}
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
