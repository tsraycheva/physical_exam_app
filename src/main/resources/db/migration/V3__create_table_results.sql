CREATE TABLE results (
    id                      BIGSERIAL PRIMARY KEY,
    year_of_performance     INT        NOT NULL,
    running_time_in_seconds INT,
    crunches_count          INT,
    push_ups_count          INT,
    jump_in_centimeters     INT,
    conclusion              VARCHAR(6) NOT NULL
);