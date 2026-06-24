import Footer from '../components/Footer';
export default function Access({ setVista }: { setVista: (v: string) => void }) {
  return (
    <div className="w-full min-h-screen bg-[url('/Image/_Background.png')] bg-cover bg-center bg-no-repeat bg-fixed font-[Verdana]">
      
      {/* MENÚ SUPERIOR (Reducido para mantener la estética) */}
      <nav className="fixed top-0 left-0 z-[1000] flex w-full flex-row items-center justify-between box-border bg-[#423f3f] px-[50px] h-[70px]">
        {/* Botón para volver atrás */}
        <a
          href="#"
          onClick={(e) => { e.preventDefault(); setVista('home'); }}
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
        <div className="flex flex-col items-center text-center mb-[60px]">
          <h1 className="font-['Impact'] text-[40px] md:text-[100px] text-black drop-shadow-lg mb-[20px]">
            📚 Acceso al Aula DX 📷
          </h1>
        </div>

        {/* GALERÍA DE BOTONES */}
        <section className="flex flex-row flex-wrap justify-center items-center gap-[40px]">
          
          {/* BOTÓN INICIAR SESIÓN */}
          {/* En lugar de href, usamos el mando a distancia (onClick) para cambiar la vista */}
          <article>
            <button 
              onClick={() => setVista('login')}
              className="group flex flex-col items-center justify-center w-[300px] md:w-[500px] h-[150px] rounded-[15px] bg-[#423f3f] border-[4px] border-white transition-all duration-300 ease-in-out hover:bg-[antiquewhite] hover:scale-105 hover:border-[antiquewhite] cursor-pointer shadow-lg"
            >
              <span className="font-['Impact'] text-[40px] md:text-[50px] text-white transition-all duration-300 group-hover:text-black">
                Iniciar sesión
              </span>
            </button>
          </article>

          {/* BOTÓN REGISTRO */}
          <article>
            <button 
              onClick={() => setVista('register')}
              className="group flex flex-col items-center justify-center w-[300px] md:w-[500px] h-[150px] rounded-[15px] bg-[#423f3f] border-[4px] border-white transition-all duration-300 ease-in-out hover:bg-[antiquewhite] hover:scale-105 hover:border-[antiquewhite] cursor-pointer shadow-lg"
            >
              <span className="font-['Impact'] text-[35px] md:text-[50px] text-white transition-all duration-300 group-hover:text-black">
                ¿Eres nuevo?
              </span>
              <span className="block text-[15px] md:text-[20px] mt-[-10px] text-white transition-all duration-300 group-hover:text-black font-bold">
                ¡Regístrate!
              </span>
            </button>
          </article>

        </section>
      </main>

      <Footer />
    </div>
  );
}