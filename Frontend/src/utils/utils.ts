import type { Errortype } from "../types/errortypes";

export function validateFormField(
  field: string,
  callback: Array<(value: string) => Errortype>,
): Errortype {
  let globalError = { errorMessage: "", hasError: false };
  callback.forEach((fun) => {
    if (!globalError.hasError) {
      const response = fun(field);

      if (response.hasError) {
        globalError = response;
      }
    }
  });

  return globalError;
}
