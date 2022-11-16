CREATE TABLE employees (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    identification_number INT NOT NULL UNIQUE,
    image_url VARCHAR NOT NULL,
    gender VARCHAR(10) NOT NULL,
    position VARCHAR(22) NOT NULL
);