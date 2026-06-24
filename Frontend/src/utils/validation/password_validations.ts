import type { Errortype } from "../../types/errortypes";
function emptyPassword(password: string): Errortype {
  if (!password.trim()) {
    return { errorMessage: "La contraseña es obligatoria.", hasError: true };
  }
  return { errorMessage: "", hasError: false };
}

function lengthPassword(password: string): Errortype {
  if (password.length < 8) {
    return {
      errorMessage: "La contraseña debe tener al menos 8 caracteres.",
      hasError: true,
    };
  }
  return { errorMessage: "", hasError: false };
}

function specialCharacterPassword(password: string): Errortype {
  if (!/[!@#$%^&*()-+]/.test(password)) {
    return {
      errorMessage: "La contraseña debe tener al menos un carácter especial.",
      hasError: true,
    };
  }
  return { errorMessage: "", hasError: false };
}

export const passwordValidations = [
  emptyPassword,
  lengthPassword,
  specialCharacterPassword,
];
