import Footer from '../components/Footer';
export default function Home({ setVista }: { setVista: (v: string) => void }) {
  return (
    <div className="w-full min-h-screen bg-[url('/Image/_Main_Wallpaper.png')] bg-cover bg-center bg-no-repeat bg-fixed font-[Verdana]">
      <header>
        {/* NAV SUPERIOR */}
        <nav className="fixed top-0 left-0 z-[1000] flex w-full flex-row items-center justify-between box-border bg-[rgb(66,63,63)] px-[50px]">
          
          {/* BOTÓN DE INICIO */}
          <a
            href="#" onClick={(e)=> {e.preventDefault(); setVista('access');}}
            className="group flex h-[50px] w-[220px] m-[10px] items-center justify-center rounded-[10px] transition-all duration-300 ease-in-out hover:bg-[antiquewhite]"
          >
            <img 
              src="Image/_User_Icon.png" 
              alt="Usuario"
              className="invert transition-all duration-300 ease-in-out group-hover:invert-0" 
            />
            <p className="m-0 font-[Impact] text-[50px] text-white transition-all duration-300 ease-in-out group-hover:text-black">
              Inicio
            </p>
          </a>

          {/* CONTENEDOR REDES SOCIALES */}
          <div className="flex items-center gap-[15px]">
            <a href="https://www.facebook.com/" target="_blank" rel="noreferrer">
              <img 
                src="Image/Facebook Logo.jpg" 
                alt="Facebook" 
                title="Facebook" 
                className="mt-[10px] w-[50px] box-border transition-all duration-50 hover:scale-110 hover:border-[5px] hover:border-[antiquewhite] hover:shadow-[0px_0px_50px_white]"
              />
            </a>
            <a href="https://x.com/" target="_blank" rel="noreferrer">
              <img 
                src="Image/Twitter Logo.jpg" 
                alt="Twitter" 
                title="Twitter" 
                className="mt-[10px] w-[50px] box-border transition-all duration-50 hover:scale-110 hover:border-[5px] hover:border-[antiquewhite] hover:shadow-[0px_0px_50px_white]"
              />
            </a>
            <a href="https://www.instagram.com/" target="_blank" rel="noreferrer">
              <img 
                src="Image/Instagram Logo.jpg" 
                alt="Instagram" 
                title="Instagram" 
                className="mt-[10px] w-[50px] box-border transition-all duration-50 hover:scale-110 hover:border-[5px] hover:border-[antiquewhite] hover:shadow-[0px_0px_50px_white]"
              />
            </a>
            <a href="https://www.youtube.com/" target="_blank" rel="noreferrer">
              <img 
                src="Image/YouTube Logo.jpg" 
                alt="YouTube" 
                title="YouTube" 
                className="mt-[10px] w-[50px] box-border transition-all duration-50 hover:scale-110 hover:border-[5px] hover:border-[antiquewhite] hover:shadow-[0px_0px_50px_white]"
              />
            </a>
          </div>
        </nav>

        <div className="h-[200px]"></div> {/* Espaciador */}
      </header>

      <main>
        
      </main>

      {/* PIE DE PÁGINA */}
      <Footer/>
      
    </div>
  );
}