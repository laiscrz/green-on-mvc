/*
Projeto "TaLigado"

Banco de dados relacionado ao aplicativo Java - GreenOn MVC 2024

Integrantes do Grupo:
- Lais Alves Da Silva Cruz - RM552258 - Turma : 2TDSPH
- Bianca Leticia Roman Caldeira - RM552267 - Turma : 2TDSPH
- Charlene Aparecida Estevam Mendes Fialho - RM552252 - Turma : 2TDSPH
- Fabrico Torres Antonio - RM97916 - Turma : 2TDSPH
- Lucca Raphael Pereira dos Santos - RM99675 - Turma : 2TDSPZ 

Este script contem as instrucoes DDL e comandos necessarios para o deploy do banco de dados com pipelines no Azure.
*/

/*
    -- REMOCAO DAS TABELAS EXISTENTES
    Antes de criar as novas tabelas, e necessario remover as existentes para evitar conflitos. 
    O uso de 'CASCADE CONSTRAINTS' remove restricoes e dependencias automaticamente.
*/
DROP TABLE branch_office_device CASCADE CONSTRAINTS;
DROP TABLE branch_office CASCADE CONSTRAINTS;
DROP TABLE device CASCADE CONSTRAINTS;
DROP TABLE user_roles CASCADE CONSTRAINTS;
DROP TABLE roles CASCADE CONSTRAINTS;
DROP TABLE user_enterprise CASCADE CONSTRAINTS;

/*
    -- TABELA branch_office
    Representa as filiais da empresa, armazenando informacoes:
    - Nome, endereco, telefone, segmento (como industria, comercio, etc.), consumo de energia e emissoes de carbono.
*/
CREATE TABLE branch_office (
    id NUMBER(19,0) GENERATED AS IDENTITY,
    nome VARCHAR2(255 CHAR),
    endereco VARCHAR2(255 CHAR),
    telefone VARCHAR2(255 CHAR),
    segmento VARCHAR2(255 CHAR) CHECK (segmento IN ('COMERCIO','INDUSTRIA','SERVICO','AGRONEGOCIO','TECNOLOGIA','SAUDE','EDUCACAO','OUTRO')),
    consumo_energia FLOAT(53),
    emissoes_carbono FLOAT(53),
    PRIMARY KEY (id)
);

/*
    -- TABELA device
    Armazena informacoes sobre dispositivos:
    - Nome, imagem ilustrativa, potencia nominal, tempo de uso, fator de emissao e departamento.
*/
CREATE TABLE device (
    id NUMBER(19,0) GENERATED AS IDENTITY,
    nome VARCHAR2(255 CHAR),
    imagemurl VARCHAR2(255 CHAR),
    potencia_nominal FLOAT(53),
    tempo_uso FLOAT(53),
    fator_emissao FLOAT(53),
    departamento VARCHAR2(255 CHAR) CHECK (departamento IN ('ADMINISTRATIVO','FINANCEIRO','RH','TI','VENDAS','MARKETING','ATENDIMENTO','LOGISTICA','PESQUISA','JURIDICO','OPERACOES','COMPRAS','ENGENHARIA','SEGURANCA','CONFORMIDADE')),
    PRIMARY KEY (id)
);

/*
    -- TABELA roles
    Define os papeis disponiveis no sistema, como:
    - USER e ADMIN.
*/
CREATE TABLE roles (
    id NUMBER(19,0) GENERATED AS IDENTITY,
    role_name VARCHAR2(255 CHAR),
    PRIMARY KEY (id)
);

/*
    -- TABELA user_enterprise
    Representa os usuarios da empresa, armazenando:
    - Nome, username, senha, e-mail, CNPJ e URL para imagem de perfil.
*/
CREATE TABLE user_enterprise (
    id NUMBER(19,0) GENERATED AS IDENTITY,
    nome VARCHAR2(255 CHAR),
    username VARCHAR2(255 CHAR),
    password VARCHAR2(255 CHAR),
    email VARCHAR2(255 CHAR),
    cnpj VARCHAR2(255 CHAR),
    img_perfil VARCHAR2(255 CHAR),
    PRIMARY KEY (id)
);

/*
    -- TABELA branch_office_device
    Define o relacionamento muitos-para-muitos entre branch_office e device:
    - Relaciona filiais aos dispositivos utilizados por elas.
*/
CREATE TABLE branch_office_device (
    branch_office_id NUMBER(19,0) NOT NULL,
    device_id NUMBER(19,0) NOT NULL,
    PRIMARY KEY (branch_office_id, device_id),
    FOREIGN KEY (branch_office_id) REFERENCES branch_office (id),
    FOREIGN KEY (device_id) REFERENCES device (id)
);

/*
    -- TABELA user_roles
    Relaciona usuarios (user_enterprise) aos papeis (roles) que possuem no sistema:
    - Permite que cada usuario tenha um ou mais papeis associados.
*/
CREATE TABLE user_roles (
    id_user NUMBER(19,0) NOT NULL,
    id_role NUMBER(19,0) NOT NULL,
    PRIMARY KEY (id_user, id_role),
    FOREIGN KEY (id_user) REFERENCES user_enterprise (id),
    FOREIGN KEY (id_role) REFERENCES roles (id)
);

/*
    -- INSERCOES INICIAIS
    Populando as tabelas com dados iniciais:
    - Adiciona os papeis 'USER' e 'ADMIN'.
    - Cria um administrador padrao com username 'taligado_admin'.
    - Associa o administrador ao papel 'ADMIN'.
*/
INSERT INTO roles (role_name) VALUES ('USER');
INSERT INTO roles (role_name) VALUES ('ADMIN');

INSERT INTO user_enterprise (email, img_perfil, nome, password, username, cnpj) 
VALUES ('admin.taligado@fiap.com', 'https://abrir.link/eiEEK', 'Admin TaLigadoFIAP', '$2a$12$.BdU8Soj10fRyyYm5hEN8uVrtE0VDHFd5KdcWxMtAjInL2FqSOC1G', 'taligado_admin', '12.345.678/0001-90');

INSERT INTO user_roles (id_user, id_role) 
VALUES ((SELECT id FROM user_enterprise WHERE username = 'taligado_admin'), (SELECT id FROM roles WHERE role_name = 'ADMIN'));

/*
    -- INSERCOES DE DISPOSITIVOS E FILIAIS
    Adiciona dispositivos como ar-condicionado e lampada LED, com suas caracteristicas.
    Cria uma filial chamada 'Filial Paulista' e relaciona os dispositivos a ela.
*/
INSERT INTO device (potencia_nominal, tempo_uso, nome, imagemurl, fator_emissao, departamento) 
VALUES (1000, 5, 'Ar-condicionado', 'https://abrir.link/Ppffr', 0.5, 'ENGENHARIA');

INSERT INTO device (potencia_nominal, tempo_uso, nome, imagemurl, fator_emissao, departamento) 
VALUES (150, 2, 'Lampada LED', 'https://abrir.link/SWmem', 0.2, 'ADMINISTRATIVO');

INSERT INTO branch_office (nome, endereco, telefone, segmento, consumo_energia, emissoes_carbono) 
VALUES ('Filial Paulista', 'Avenida Paulista , 1100, Sao Paulo, SP', '(11) 1234-5678', 'INDUSTRIA', 5300.0, 3710.0);

INSERT INTO branch_office_device (branch_office_id, device_id) VALUES (1, 1);
INSERT INTO branch_office_device (branch_office_id, device_id) VALUES (1, 2);

/*
    -- CONFIRMANDO ALTERACOES
    Garante que todas as insercoes e alteracoes sejam salvas no banco de dados.
*/
COMMIT;

/*
    -- CONSULTAS DE DADOS
    Exibe todos os dados inseridos nas tabelas para validacao.
*/
SELECT * FROM branch_office;
SELECT * FROM device;
SELECT * FROM roles;
SELECT * FROM user_enterprise;
SELECT * FROM user_roles;
SELECT * FROM branch_office_device;
