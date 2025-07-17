INSERT INTO tb_roles (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_roles (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_users (id, name, email, phone, password, birth_date) VALUES
(1, 'Maria Santos', 'maria@example.com', '(11) 99999-9999', '123456', '1990-01-01'),
(2, 'Joao Silva', 'joao@example.com', '(22) 98888-8888', '123456', '1985-05-05');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);

