import React, { useState, useEffect, useRef } from 'react';
import { Modal } from 'bootstrap';

function LoginModal({ isOpen, onClose }) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const modalRef = useRef();
  const modalInstance = useRef(null);

  useEffect(() => {
    if (modalRef.current) {
      modalInstance.current = new Modal(modalRef.current, {
        backdrop: 'static',
        keyboard: false
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

  const handleSubmit = (e) => {
    e.preventDefault();
    setError('');

    if (username.trim() === '' || password.trim() === '') {
      setError('Por favor, completa todos los campos.');
      return;
    }
    if (username.length < 4) {
      setError('El nombre de usuario debe tener al menos 4 caracteres.');
      return;
    }
    if (password.length < 8) {
      setError('La contraseña debe tener al menos 8 caracteres.');
      return;
    }

    alert('¡Inicio de sesión exitoso!');
    onClose(); // Cerramos el modal
  };

  return (
    <div className="modal fade" ref={modalRef} id="loginModal" tabIndex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="loginModalLabel">Iniciar Sesión</h5>
            <button type="button" className="btn-close" onClick={onClose} aria-label="Close"></button>
          </div>
          <div className="modal-body">
            <form className="row g-3" id="loginForm" onSubmit={handleSubmit} noValidate>
              <div className="col-md-12">
                <label htmlFor="loginUsername" className="form-label">Username</label>
                <input 
                  type="text" 
                  className="form-control" 
                  id="loginUsername"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)} 
                  required 
                />
              </div>
              <div className="col-md-12">
                <label htmlFor="loginPassword" className="form-label">Contraseña</label>
                <input 
                  type="password" 
                  className="form-control" 
                  id="loginPassword"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)} 
                  required 
                />
              </div>
              <div className="col-md-12">
                <a href="#" className="form-label">¿Olvidaste tu contraseña?</a>
              </div>
              {error && (
                <div className="col-12 mt-3">
                  <div id="mensajeErrorLogin" className="alert alert-danger">{error}</div>
                </div>
              )}
              <div className="col-md-12 mt-3">
                <button className="btn btn-light" id="btnIniciarLogin" type="submit">Iniciar Sesión</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default LoginModal;