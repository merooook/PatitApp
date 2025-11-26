import React, { useState, useEffect, useRef } from 'react';
import { Modal } from 'bootstrap';
import { registerUser } from '../services/userService';

function RegistroModal({ isOpen, onClose }) {
  // Un estado para cada campo del formulario
  const [nombre, setNombre] = useState('');
  const [apellido, setApellido] = useState('');
  const [rut, setRut] = useState('');
  const [username, setUsername] = useState('');
  const [region, setRegion] = useState('');
  const [ciudad, setCiudad] = useState('');
  const [telefono, setTelefono] = useState('');
  const [password, setPassword] = useState('');

  // Un estado para manejar el mensaje de error
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const modalRef = useRef();
  const modalInstance = useRef(null);

  useEffect(() => {
    if (modalRef.current) {
      modalInstance.current = new Modal(modalRef.current, {
        backdrop: 'static', // Evita que se cierre al hacer clic fuera
        keyboard: false // Evita que se cierre con la tecla Esc
      });
    }
  }, []);

  useEffect(() => {
    if (isOpen) {
      modalInstance.current?.show();
    } else {
      modalInstance.current?.hide();
    }
  }, [isOpen]);

  // Función que se ejecuta al enviar el formulario
  const handleSubmit = async (e) => {
    e.preventDefault(); // Evita que la página se recargue
    setError(''); // Limpia errores anteriores

    // --- REGLAS DE VALIDACIÓN (del JS original) ---

    // 1. Validar que ningún campo esté vacío
    if ([nombre, apellido, rut, username, region, ciudad, telefono, password].some(val => val.trim() === '')) {
      setError('Por favor, completa todos los campos.');
      return;
    }

    // 2. Validar nombre y apellido (solo letras)
    const soloLetrasRegex = /^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$/;
    if (!soloLetrasRegex.test(nombre)) {
      setError('El nombre solo puede contener letras.');
      return;
    }
    if (!soloLetrasRegex.test(apellido)) {
      setError('El apellido solo puede contener letras.');
      return;
    }

    // 3. Validar RUT
    const rutRegex = /^\d{7,8}-[0-9Kk]$/;
    if (!rutRegex.test(rut)) {
      setError('El RUT debe tener un formato válido (ej: 12345678-9).');
      return;
    }

    // 4. Validar Username (largo mínimo)
    if (username.trim().length < 4) {
      setError('El nombre de usuario debe tener al menos 4 caracteres.');
      return;
    }

    // 5. Validar Teléfono
    const telefonoRegex = /^9\d{8}$/;
    if (!telefonoRegex.test(telefono)) {
      setError('El teléfono debe comenzar con 9 y tener 9 dígitos.');
      return;
    }

    // 6. Validar Contraseña (largo mínimo)
    if (password.trim().length < 8) {
      setError('La contraseña debe tener al menos 8 caracteres.');
      return;
    }

    // Si todas las validaciones pasan, llamamos a la API
    setLoading(true);
    try {
      const newUser = {
        nombre,
        apellido,
        rut,
        username,
        region,
        ciudad,
        telefono,
        password
      };
      await registerUser(newUser);
      alert('¡Registro exitoso!');
      onClose();
    } catch (err) {
      setError('Error al registrar usuario. Inténtalo nuevamente.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal fade" ref={modalRef} id="registroModal" tabIndex="-1" aria-labelledby="registroModalLabel" aria-hidden="true">
      <div className="modal-dialog modal-lg">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="registroModalLabel">Crear una Cuenta</h5>
            <button type="button" className="btn-close" onClick={onClose} aria-label="Close"></button>
          </div>
          <div className="modal-body">
            {/* El formulario ahora llama a handleSubmit */}
            <form className="row g-3" id="form2" onSubmit={handleSubmit} noValidate>
              <div className="col-md-6">
                <label htmlFor="registroNombre" className="form-label">Nombre</label>
                <input type="text" className="form-control" id="registroNombre" value={nombre} onChange={(e) => setNombre(e.target.value)} required />
              </div>
              <div className="col-md-6">
                <label htmlFor="registroApellidos" className="form-label">Apellidos</label>
                <input type="text" className="form-control" id="registroApellidos" value={apellido} onChange={(e) => setApellido(e.target.value)} required />
              </div>
              <div className="col-md-6">
                <label htmlFor="registroRUT" className="form-label">RUT</label>
                <input type="text" className="form-control" id="registroRUT" value={rut} onChange={(e) => setRut(e.target.value)} required />
                <div className="form-text">Por favor proporciona un RUT válido. Sin puntos y con guión.</div>
              </div>
              <div className="col-md-6">
                <label htmlFor="registroUsername" className="form-label">Username</label>
                <div className="input-group">
                  <span className="input-group-text">@</span>
                  <input type="text" className="form-control" id="registroUsername" value={username} onChange={(e) => setUsername(e.target.value)} required />
                </div>
              </div>
              <div className="col-md-6">
                <label htmlFor="registroRegion" className="form-label">Región</label>
                <select className="form-select" id="registroRegion" value={region} onChange={(e) => setRegion(e.target.value)} required>
                  <option disabled value="">Seleccionar...</option>
                  <option>Región de Arica y Parinacota</option>
                  <option>Región de Tarapacá</option>
                  <option>Región de Antofagasta</option>
                  <option>Región de Atacama</option>
                  <option>Región de Coquimbo</option>
                  <option>Región de Valparaíso</option>
                  <option>Región Metropolitana de Santiago</option>
                  <option>Región del Libertador General Bernardo O'Higgins</option>
                  <option>Región del Maule</option>
                  <option>Región de Ñuble</option>
                  <option>Región del Biobío</option>
                  <option>Región de La Araucanía</option>
                  <option>Región de Los Ríos</option>
                  <option>Región de Los Lagos</option>
                  <option>Región de Aysén</option>
                  <option>Región de Magallanes y de la Antártica Chilena</option>
                </select>
              </div>
              <div className="col-md-6">
                <label htmlFor="registroCiudad" className="form-label">Ciudad</label>
                <input type="text" className="form-control" id="registroCiudad" value={ciudad} onChange={(e) => setCiudad(e.target.value)} required />
              </div>
              <div className="col-md-6">
                <label htmlFor="registroTelefono" className="form-label">Teléfono</label>
                <input type="text" className="form-control" id="registroTelefono" value={telefono} onChange={(e) => setTelefono(e.target.value)} required />
              </div>
              <div className="col-md-6">
                <label htmlFor="registroPassword" className="form-label">Contraseña</label>
                <input type="password" className="form-control" id="registroPassword" value={password} onChange={(e) => setPassword(e.target.value)} required />
              </div>
              <div className="col-12">
                <div className="form-check">
                  <input className="form-check-input" type="checkbox" id="invalidCheck3" required />
                  <label className="form-check-label" htmlFor="invalidCheck3">
                    Acepto los términos y condiciones
                  </label>
                </div>
              </div>
              {/* Renderizado condicional del mensaje de error */}
              {error && (
                <div className="col-12 mt-3">
                  <div className="alert alert-danger">{error}</div>
                </div>
              )}
              <div className="col-12 mt-3">
                <button className="btn btn-light" type="submit" disabled={loading}>
                  {loading ? 'Registrando...' : 'Registrarse'}
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default RegistroModal;