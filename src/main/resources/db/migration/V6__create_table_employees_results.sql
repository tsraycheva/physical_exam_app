CREATE TABLE employees_results (
    employee_id BIGSERIAL,
    result_id BIGSERIAL,
    CONSTRAINT pk_employees_results_employees
    PRIMARY KEY (employee_id, result_id),
    CONSTRAINT fk_employees_results_employees
    FOREIGN KEY(employee_id) REFERENCES employees(id),
    CONSTRAINT k_employees_results_results
    FOREIGN KEY(result_id) REFERENCES results(id)
);