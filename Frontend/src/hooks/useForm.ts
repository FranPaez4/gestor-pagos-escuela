import { useState } from "react";

export const useForm = <T extends Record<string, string | boolean>>(
  initialState: T,
) => {
  const [formData, setFormData] = useState<T>(initialState);
  const [errors, setErrors] = useState<Record<keyof T, string>>(
    Object.keys(initialState).reduce(
      (acc, key) => {
        acc[key as keyof T] = "";
        return acc;
      },
      {} as Record<keyof T, string>,
    ),
  );

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]:
        type === "checkbox" ? (checked as T[keyof T]) : (value as T[keyof T]),
    }));
  };

  return { formData, setFormData, errors, setErrors, handleChange };
};
