INSERT INTO tb_roles (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_roles (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_users (id, name, email, phone, password, birth_date) VALUES
(1, 'Maria Santos', 'maria@example.com', '(11) 99999-9999', '$2a$10$JWI.Jnl3Djgq1NEgnZtgXOdZzwpH3/Z51A/2ks6IT8ExuhYumj4iC', '1990-01-01'),
(2, 'Alex Silva', 'alex@example.com', '(22) 98888-8888', '$2a$10$JWI.Jnl3Djgq1NEgnZtgXOdZzwpH3/Z51A/2ks6IT8ExuhYumj4iC', '1985-05-05');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);

