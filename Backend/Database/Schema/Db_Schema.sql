-- Crear la base de datos
CREATE DATABASE objetivo_dx_DB;

-- Conectar a la base de datos (esto debe ejecutarse en el cliente PostgreSQL)
-- \c objetivo_dx_DB;

-- Crear la tabla Role primero si no existe (asumiendo que es necesaria para la FK)
CREATE TABLE IF NOT EXISTS Role (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL
);

-- Crear la tabla User
CREATE TABLE IF NOT EXISTS "User" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    role_id UUID NOT NULL,
    name VARCHAR(25) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    phone VARCHAR(11) NULL,
    email VARCHAR(25) NOT NULL UNIQUE,
    password VARCHAR(25) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL,
    FOREIGN KEY (role_id) REFERENCES Role(id)
);

-- Crear la tabla Token
CREATE TABLE IF NOT EXISTS Token (
    id UUID PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    value VARCHAR(255) NOT NULL,
    revoked BOOLEAN NOT NULL,
    expired_at TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "User"(id)
);

-- Crear la Tabla de Cursos Académicos
CREATE TABLE IF NOT EXISTS academic_courses (
id UUID PRIMARY KEY,
course_id UUID NOT NULL,
start_date DATE,
end_date DATE,
price DECIMAL(10, 2),
    type VARCHAR(50)
    );

-- Crear la Tabla de Grupos
CREATE TABLE IF NOT EXISTS groups (
    id UUID PRIMARY KEY,
    academic_course_id UUID NOT NULL,
    name VARCHAR(100) NOT NULL,
    level VARCHAR(20), -- Para guardar BEGINNER, LOW, MEDIUM, HIGH
    start_time VARCHAR(10),
    end_time VARCHAR(10),
    CONSTRAINT fk_academic_course FOREIGN KEY (academic_course_id) REFERENCES academic_courses(id)
    );

-- Crear la Tabla de Room_per_group asociativa entre Aulas y Grupos
CREATE TABLE IF NOT EXISTS room_per_group (
    group_id UUID NOT NULL,
    room_id UUID NOT NULL,
    schedule_init VARCHAR(10) NOT NULL,
    schedule_end VARCHAR(10) NOT NULL,
    -- Definimos la clave primaria compuesta
    PRIMARY KEY (group_id, room_id),
    -- Claves foráneas para mantener la integridad
    CONSTRAINT fk_rpg_group FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE,
    CONSTRAINT fk_rpg_room FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE
    );
