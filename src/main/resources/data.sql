INSERT INTO usuario (email, password, tipo_usuario, quantidade_acessos) VALUES
('teste@gmail.com', '$2a$12$eanl.MjGcHiNebmhCsrJouTXkBBG6knigQn235dMQhy/fTH9k1b6u', 'VETERINARIO', 0),
('teste2@gmail.com', '$2a$12$eanl.MjGcHiNebmhCsrJouTXkBBG6knigQn235dMQhy/fTH9k1b6u', 'ATENDENTE', 1),
('teste3@gmail.com', '$2a$12$eanl.MjGcHiNebmhCsrJouTXkBBG6knigQn235dMQhy/fTH9k1b6u', 'CLIENTE', 2);

INSERT INTO cliente (cpf, nome, apelido, telefone, usuario) VALUES
('41047581833', 'Antenor Manoel', 'Antenor', '34998096296', 1),
('74125441584', 'Gabriela Souza', 'Gabriela', '34996587458', 2),
('85474154784', 'Ricardo Carlos', 'Ricardo', '34985478741', 3);

INSERT INTO animal (nome, foto, raca, pelagem, peso, tipo, data_nascimento, data_cadastro, estado, cliente) VALUES
('pitoco', 'pitoco.png', 'doberman', 'preta', 15.2, 1, '2015-06-15 10:22:45', '2022-06-11 22:21:10', 2, 1),
('kekeu', 'kekeu.png', 'siames', 'cinza', 5.7, 2, '2016-07-16 11:23:46', '2022-06-11 15:15:03', 2, 1),
('matue', 'matue.png', 'bulldog', 'marrom', 10.5, 1, '2017-08-17 12:24:47', '2022-06-11 11:07:48', 2, 2),
('rex', 'rex.png', 'pintcher', 'preta', 2.1, 1, '2018-09-18 13:25:48', '2022-06-11 09:18:34', 2, 3);