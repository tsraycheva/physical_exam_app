CREATE TABLE exercises (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(10) NOT NULL,
    requirement INT         NOT NULL,
    gender      VARCHAR(10) NOT NULL
);