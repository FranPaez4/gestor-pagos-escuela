import type { Errortype } from "../../types/errortypes";
function emptySurname(surname: string): Errortype {
  if (!surname.trim()) {
    return { errorMessage: "Los apellidos son obligatorios.", hasError: true };
  }
  return { errorMessage: "", hasError: false };
}

export const surnameValidations = [emptySurname];
