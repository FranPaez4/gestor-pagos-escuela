import type { Errortype } from "../../types/errortypes";
function emptyEmail(email: string): Errortype {
  if (!email.trim()) {
    return {
      errorMessage: "El correo electrónico es obligatorio.",
      hasError: true,
    };
  }
  return { errorMessage: "", hasError: false };
}

function invalidEmail(email: string): Errortype {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email)) {
    return {
      errorMessage: "El formato del correo electrónico no es válido.",
      hasError: true,
    };
  }
  return { errorMessage: "", hasError: false };
}

export const emailValidations = [emptyEmail, invalidEmail];
