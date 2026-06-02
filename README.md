# City Courier - Sistema Integrado de Gestión Logística

City Courier es una aplicación web desarrollada para la gestión eficiente de flotas de reparto, rutas y envíos de paquetería. Está diseñada para optimizar los procesos de una empresa de mensajería, automatizando la toma de decisiones y facilitando el control de tiempos de entrega mediante un sistema de alertas en tiempo real.

---

## Accesos y Roles del Sistema (Entorno de Pruebas)

El sistema cuenta con seguridad basada en roles (Role-Based Access Control). Para probar las diferentes vistas y permisos de la aplicación, puedes utilizar las siguientes credenciales configuradas por defecto:

* **Rol RRHH (Acceso Básico):** * **Usuario:** `user`
    * **Contraseña:** `user`
    * *Permisos:* Acceso a la gestión del personal (Alta, modificación y consulta de repartidores).

* **Rol Logística (Acceso Operativo):** * **Usuario:** `logistica`
    * **Contraseña:** `logistica`
    * *Permisos:* Gestión del núcleo del negocio (Rutas, Envíos, Asignaciones, SLA).

* **Rol Admin (Acceso Total):** * **Usuario:** `admin`
    * **Contraseña:** `admin`
    * *Permisos:* Acceso absoluto a todos los módulos del sistema (RRHH + Logística) y gestión de otros operadores.

---

## Funcionalidades Principales

La aplicación está dividida en varios módulos operativos para cubrir todo el ciclo de vida de un envío:

### 1. Gestión de Recursos Humanos
* **Gestión de Flota:** Alta, baja y modificación de repartidores.
* **Asignación de Vehículos:** Control de las capacidades de carga máxima (furgonetas, motos ecológicas) y disponibilidad del personal.

### 2. Gestión de Rutas y Zonas
* **Creación de Rutas Dinámicas:** Generación de rutas conectando múltiples puntos de entrega y cálculo de distancias.
* **Asignación de Repartidores:** Vinculación directa entre conductores y rutas de reparto específicas.

### 3. Operativa de Envíos y Asignaciones
* **Recepción de Envíos:** Registro de nuevos paquetes con destino, peso y urgencia.
* **Auto-Asignación Inteligente (Algoritmo Core):** El sistema es capaz de escanear paquetes huérfanos y asignarlos automáticamente a los repartidores disponibles basándose en:
    * Coincidencia de la zona de destino con la ruta del repartidor.
    * Validación en tiempo real del peso del paquete frente a la capacidad máxima del vehículo del repartidor.
* **Asignación Manual Segura:** Permite a los operadores forzar asignaciones, lanzando excepciones de seguridad si se intenta asignar a un repartidor sin ruta o si el vehículo excede su capacidad.

### 4. Monitorización de Tiempo 
* **Semáforo de Tiempos:** Sistema visual dinámico que evalúa en tiempo real el estado de cada envío:
    * **A Tiempo:** Amplio margen de entrega.
    * **En Riesgo:** Menos de 24 horas para la hora límite.
    * **Atrasado:** El paquete ha superado su fecha de entrega acordada.

---

## 🛠️ Tecnologías Utilizadas

* **Backend:** Java, Spring Boot, Spring Data JPA, Spring Security.
* **Frontend:** Thymeleaf, HTML5, CSS3, Bootstrap 5.
* **Base de Datos:** H2 (In-Memory) / MySQL.
* **Arquitectura:** MVC (Model-View-Controller) con inyección de dependencias y repositorios.