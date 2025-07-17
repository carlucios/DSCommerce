CREATE TABLE tb_roles (
    id SERIAL PRIMARY KEY,
    authority VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE tb_users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    birth_date DATE NOT NULL
);

CREATE TABLE tb_user_role (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES tb_users(id),
    FOREIGN KEY (role_id) REFERENCES tb_roles(id)
);
