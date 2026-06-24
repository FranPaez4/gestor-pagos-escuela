import { useState } from 'react';
import Home from './pages/Home';
import Access from './pages/Access';
import Login from './pages/Login';
import Register from './pages/Register';

export default function App() {
  // Este es el "semáforo". Empezamos viendo la 'home'
  const [vista, setVista] = useState('home');

  // Dependiendo del valor del semáforo, mostramos un archivo u otro,
  // y les pasamos el 'mando a distancia' (setVista) para que puedan cambiar el semáforo.
  if (vista === 'home') return <Home setVista={setVista} />;
  if (vista === 'access') return <Access setVista={setVista} />;
  if (vista === 'login') return <Login setVista={setVista} />;
  if (vista === 'register') return <Register setVista={setVista} />;

  return null;
}
