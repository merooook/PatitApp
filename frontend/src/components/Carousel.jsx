import React from 'react';

function Carousel() {
  return (
    <div id="carouselExampleIndicators" className="carousel slide my-5">
      <div className="carousel-indicators">
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" className="active" aria-current="true" aria-label="Slide 1"></button>
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
      </div>

      <div className="carousel-inner">
        {/* Slide 1: Video */}
        <div className="carousel-item active">
          <div className="ratio ratio-16x9 carousel-media">
            <iframe 
              src="https://www.youtube.com/embed/7VJM_oCHdOc" 
              title="Fundación Rescatando Patitas - Apoyando comunidades" 
              allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
              allowFullScreen 
              className="rounded-3">
            </iframe>
          </div>
          <div className="carousel-caption d-none d-md-block">
            <h5>Apoyando comunidades</h5>
            <p>Donamos el 5% de cada venta a nuestras fundaciones aliadas en Chile.</p>
          </div>
        </div>

        {/* Slide 2: Imagen */}
        <div className="carousel-item">
          <div className="ratio ratio-16x9 carousel-media">
            <img src="https://images.pexels.com/photos/26834696/pexels-photo-26834696.jpeg" className="d-block w-100 rounded-3" alt="Voluntariado activo" />
          </div>
          <div className="carousel-caption d-none d-md-block">
            <h5>Voluntariado activo</h5>
          </div>
        </div>

        {/* Slide 3: Imagen */}
        <div className="carousel-item">
          <div className="ratio ratio-16x9 carousel-media">
            <img src="https://images.pexels.com/photos/12195433/pexels-photo-12195433.jpeg" className="d-block w-100 rounded-3" alt="Impacto ambiental" />
          </div>
          <div className="carousel-caption d-none d-md-block">
            <h5>Impacto ambiental</h5>
          </div>
        </div>
      </div>

      <button className="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
        <span className="carousel-control-prev-icon" aria-hidden="true"></span>
        <span className="visually-hidden">Anterior</span>
      </button>
      <button className="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
        <span className="carousel-control-next-icon" aria-hidden="true"></span>
        <span className="visually-hidden">Siguiente</span>
      </button>
    </div>
  );
}

export default Carousel;