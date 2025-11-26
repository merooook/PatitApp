import React from 'react';

function Footer() {
  return (
    <footer id="contacto" className="text-center text-lg-start mt-5">
      <div className="container p-4">
        <div className="row">
          <div className="col-lg-6 col-md-12 mb-4 mb-md-0">
            <h5 className="text-uppercase">Huella Solidaria</h5>
            <p>
              Trabajando por un impacto positivo en nuestras comunidades.
            </p>
          </div>
          <div className="col-lg-3 col-md-6 mb-4 mb-md-0">
            <h5 className="text-uppercase">Enlaces útiles</h5>
            <ul className="list-unstyled mb-0">
              <li><a href="#" className="text-dark">Inicio</a></li>
              <li><a href="#" className="text-dark">Acerca de</a></li>
              <li><a href="#" className="text-dark">Proyectos</a></li>
              <li><a href="#" className="text-dark">Contacto</a></li>
            </ul>
          </div>
          <div className="col-lg-3 col-md-6 mb-4 mb-md-0">
            <h5 className="text-uppercase">Contáctanos</h5>
            <ul className="list-unstyled mb-0">
              <li><a href="#" className="text-dark">Email: info@huellasolidaria.org</a></li>
              <li><a href="#" className="text-dark">Teléfono: +56 9 1234 5678</a></li>
              <li><a href="#" className="text-dark">Dirección: Quilpué, Valparaíso, Chile</a></li>
            </ul>
          </div>
        </div>
      </div>
      <div className="text-center p-3" style={{ backgroundColor: 'rgba(0, 0, 0, 0.2)' }}>
        &copy; 2025 Huella Solidaria. Todos los derechos reservados.
      </div>
    </footer>
  );
}

export default Footer;