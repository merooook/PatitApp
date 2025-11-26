# PatitApp

Aplicación móvil desarrollada en Kotlin y Jetpack Compose que permite a los usuarios encontrar servicios cercanos para mascotas (veterinarias, tiendas y servicios de cuidado). El proyecto integra microservicios en Spring Boot, consumo de API externa, geolocalización y arquitectura MVVM.

---

## 1. Integrantes
- Fernanda Lazo  
- Matías Barraza
- Meritxell Arroyo

---

## 2. Descripción general
PatitApp ofrece registro, inicio de sesión, visualización de servicios cercanos, cálculo de distancias, navegación por mapa y trazado de rutas. La información se obtiene desde microservicios remotos. La aplicación implementa MVVM, corrutinas, Retrofit, Google Maps y DataStore.

---

## 3. Arquitectura
- MVVM  
- Jetpack Compose  
- Retrofit para comunicación con backend  
- DataStore para persistencia ligera  
- Google Maps Compose y Directions API  
- Corrutinas para operaciones asíncronas

---

## 4. Microservicios consumidos

### Microservicio de Usuarios (`/api/users`)
Endpoints:
- GET `/api/users` – Obtiene todos los usuarios  
- POST `/api/users` – Registra un nuevo usuario  

Entidad:
- id, nombre, correo, password, dirección

### Microservicio de Servicios (`/api/locations`)
Endpoints:
- GET `/api/locations` – Lista servicios  
- POST `/api/locations` – Crea un nuevo servicio  

Entidad:
- id, name, description, priceCLP, imageUrl, lat, lon, category

---

## 5. API externa
- Google Directions API: generación de rutas entre usuario y servicio  
- Google Maps Compose: visualización de mapas, marcadores y polylines  

---

## 6. Funcionalidades principales
- Registro y autenticación  
- Persistencia local del usuario con DataStore  
- Obtención de ubicación mediante FusedLocationProvider  
- Cálculo de distancia con Haversine  
- Vista en lista y en mapa  
- Consumo de microservicios REST remotos

---

## 7. Pruebas unitarias
Incluye pruebas básicas (JUnit). Se pueden extender para testing de ViewModels con MockK o similares.

---

## 8. APK firmado
El proyecto incluye APK firmado generado mediante Android Studio (Build > Generate Signed APK), junto con su archivo .jks correspondiente.

---

## 9. Ejecución del proyecto

Requisitos:
- Android Studio
- API level 24 o superior
- Backend activo y accesible desde el dispositivo

Pasos:
1. Clonar el repositorio.
2. Abrir en Android Studio.
3. Ejecutar en emulador o dispositivo físico.
4. Confirmar disponibilidad del backend.
