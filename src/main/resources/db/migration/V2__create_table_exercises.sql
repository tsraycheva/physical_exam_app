CREATE TABLE exercises (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    requirement INT         NOT NULL,
    gender      VARCHAR(10) NOT NULL
);