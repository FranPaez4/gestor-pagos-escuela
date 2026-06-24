// src/components/Footer.tsx
export default function Footer() {
  return (
    <footer className="fixed bottom-0 left-0 z-[1000] flex w-full flex-col items-center gap-[15px] box-border bg-[#423f3f] py-[15px] px-[50px] font-bold text-white text-center">
      
      <div className="flex w-full flex-row flex-wrap items-center justify-between gap-[30px] text-white">
        <p className="m-0 text-center">Teléfono: +34 676 00 40 60</p>
        <p className="m-0 text-center">Dirección: C. Córdoba, 9. CP: 29001. Málaga</p>
        <p className="m-0 text-center">Email: ObjetivoDX@gmail.com</p>
      </div>
      
      <div className="w-full text-center text-[15px] italic text-white">
        <p className="m-0">Copyright© 2026 - 2027 "OBJETIVO DX" | Todos los derechos reservados</p>
      </div>
      
    </footer>
  );
}