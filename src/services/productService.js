export const getAllProducts = async () => {
    try {
        // Updated URL to use relative path (proxied by Vite)
        const response = await fetch('/api/productos/v1/listarProductos');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();

        // Mapear datos para asegurar que imagenSrc exista
        return data.map(item => ({
            ...item,
            // Si el backend no trae imagen, asignamos una por defecto según categoría o genérica
            imagenSrc: item.imagenSrc || getImageByCategory(item.categoria)
        }));
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
        throw error;
    }
};

// Helper para asignar imágenes (puedes ajustar las rutas según tus assets)
const getImageByCategory = (categoria) => {
    if (!categoria) return '/img/placeholder.webp';
    const cat = categoria.toLowerCase();
    if (cat.includes('gato')) return '/img/nutrique_baby_cat.webp'; // Ejemplo
    if (cat.includes('perro')) return '/img/nomade_senior.webp';   // Ejemplo
    return '/img/placeholder.webp';
};
