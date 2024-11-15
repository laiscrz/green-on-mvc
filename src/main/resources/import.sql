INSERT INTO role (role_name) VALUES ('USER');
INSERT INTO role (role_name) VALUES ('ADMIN');

INSERT INTO user_enterprise (email, img_perfil, nome, password, username, cnpj) VALUES ('admin@fiap.com', 'https://is3-ssl.mzstatic.com/image/thumb/Purple123/v4/78/b7/75/78b7757a-01a4-721d-2f13-71f3064a68f7/source/512x512bb.jpg', 'Admin FIAP', '$2a$12$.BdU8Soj10fRyyYm5hEN8uVrtE0VDHFd5KdcWxMtAjInL2FqSOC1G', 'admin_fiap', '12.345.678/0001-90');

INSERT INTO user_role (id_user, id_role) VALUES ((SELECT id FROM user_enterprise WHERE username = 'admin_fiap'), (SELECT id FROM role WHERE role_name = 'ADMIN'));

INSERT INTO device (potencia_nominal, tempo_uso, nome, imagemurl, fator_emissao, departamento) VALUES (1000, 5, 'Ar-condicionado', 'https://abrir.link/Ppffr', 0.5, 'ENGENHARIA');

INSERT INTO device (potencia_nominal, tempo_uso, nome, imagemurl, fator_emissao, departamento) VALUES (150, 2, 'Lâmpada LED', 'https://abrir.link/SWmem', 0.2, 'ADMINISTRATIVO');

INSERT INTO branch_office (nome, endereco, telefone, segmento, consumo_energia, emissoes_carbono) VALUES ('Filial Paulista', 'Avenida Paulista , 1100, São Paulo, SP', '(11) 1234-5678', 'INDUSTRIA', 5300.0, 3710.0);


INSERT INTO branch_office_device (branch_office_id, device_id) VALUES (1, 1);

INSERT INTO branch_office_device (branch_office_id, device_id) VALUES (1, 2);