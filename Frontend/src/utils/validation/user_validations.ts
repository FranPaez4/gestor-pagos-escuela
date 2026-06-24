import type { Errortype } from "../../types/errortypes";
function emptyName(name: string): Errortype {
  if (!name.trim()) {
    return { errorMessage: "El nombre es obligatorio.", hasError: true };
  }
  return { errorMessage: "", hasError: false };
}

export const userValidations = [emptyName];
