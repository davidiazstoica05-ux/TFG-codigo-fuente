![Banner de City Courier](docs/CityCourierBanner.png)

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

##  Tecnologías Utilizadas
### Backend
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)

### Frontend
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![Bootstrap](https://img.shields.io/badge/bootstrap-%238511FA.svg?style=for-the-badge&logo=bootstrap&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)

### Base de Datos & Arquitectura
![H2 Database](https://img.shields.io/badge/H2_Database-4479A1?style=for-the-badge&logo=databricks&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%234479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![MVC Architecture](https://img.shields.io/badge/Arquitectura-MVC-FF7139?style=for-the-badge)