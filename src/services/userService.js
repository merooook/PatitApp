

// URL base para usuarios (Relative path proxied by Vite)
const API_URL = '/api/usuarios/v1';


export const registerUser = async (userData) => {
    try {
        const response = await fetch(`${API_URL}/guardarUsuario`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userData),
        });

        if (!response.ok) {
            throw new Error('Error al registrar usuario');
        }

        return await response.json();
    } catch (error) {
        console.error('Error en registerUser:', error);
        throw error;
    }
};

export const loginUser = async (username, password) => {
    try {
        // 1. Obtener todos los usuarios
        const response = await fetch(`${API_URL}/listarUsuarios`);
        if (!response.ok) {
            // Si devuelve 204 No Content, es que no hay usuarios, así que no puede loguear
            if (response.status === 204) {
                throw new Error('Usuario o contraseña incorrectos');
            }
            throw new Error('Error al conectar con el servidor');
        }

        const usuarios = await response.json();

        // 2. Buscar usuario que coincida
        // NOTA: Esto es inseguro y solo para fines demostrativos/académicos
        const userFound = usuarios.find(
            u => u.username === username && u.password === password
        );

        if (!userFound) {
            throw new Error('Usuario o contraseña incorrectos');
        }

        return userFound;
    } catch (error) {
        console.error('Error en loginUser:', error);
        throw error;
    }
};
