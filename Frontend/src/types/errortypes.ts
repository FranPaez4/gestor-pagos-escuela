export type Errortype = {
  errorMessage: string;
  hasError: boolean;
};

export type RegisterDataFormType = {
  nombre: string;
  apellidos: string;
  email: string;
  contrasena: string;
  rgpd: boolean;
};

export type RegisterErrorFormType = {
  nombre: string;
  apellidos: string;
  email: string;
  contrasena: string;
  rgpd: string;
};

export type LoginDataFormType = {
  email: string;
  contrasena: string;
  recordarme: boolean;
};

export type LoginErrorFormType = {
  email: string;
  contrasena: string;
  recordarme: string;
};
