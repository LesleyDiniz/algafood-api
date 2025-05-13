set foreign_key_checks = 0;

DELETE FROM cidade;
DELETE FROM cozinha;
DELETE FROM estado;
DELETE FROM forma_pagamento;
DELETE FROM grupo;
DELETE FROM grupo_permissao;
DELETE FROM permissao;
DELETE FROM produto;
DELETE FROM restaurante;
DELETE FROM restaurante_forma_pagamento;
DELETE FROM usuario;
DELETE FROM usuario_grupo;

set foreign_key_checks = 1;

ALTER TABLE cidade auto_increment = 1;
ALTER TABLE cozinha auto_increment = 1;
ALTER TABLE forma_pagamento auto_increment = 1;
ALTER TABLE grupo auto_increment = 1;
ALTER TABLE permissao auto_increment = 1;
ALTER TABLE produto auto_increment = 1;
ALTER TABLE restaurante auto_increment = 1;
ALTER TABLE usuario auto_increment = 1;

insert into cozinha (nome) values ('Tailandesa');
insert into cozinha (nome) values ('Indiana');
insert into cozinha (nome) values ('Argentina');
insert into cozinha (nome) values ('Brasileira');

insert into estado (id, nome) values (1, 'Acre');
insert into estado (id, nome) values (2, 'Alagoas');
insert into estado (id, nome) values (3, 'Amapá');
insert into estado (id, nome) values (4, 'Amazonas');
insert into estado (id, nome) values (5, 'Bahia');
insert into estado (id, nome) values (6, 'Ceará');
insert into estado (id, nome) values (7, 'Distrito Federal');
insert into estado (id, nome) values (8, 'Espírito Santo');
insert into estado (id, nome) values (9, 'Goiás');
insert into estado (id, nome) values (10, 'Maranhão');
insert into estado (id, nome) values (11, 'Mato Grosso');
insert into estado (id, nome) values (12, 'Mato Grosso do Sul');
insert into estado (id, nome) values (13, 'Minas Gerais');
insert into estado (id, nome) values (14, 'Pará');
insert into estado (id, nome) values (15, 'Paraíba');
insert into estado (id, nome) values (16, 'Paraná');
insert into estado (id, nome) values (17, 'Pernambuco');
insert into estado (id, nome) values (18, 'Piauí');
insert into estado (id, nome) values (19, 'Rio de Janeiro');
insert into estado (id, nome) values (20, 'Rio Grande do Norte');
insert into estado (id, nome) values (21, 'Rio Grande do Sul');
insert into estado (id, nome) values (22, 'Rondônia');
insert into estado (id, nome) values (23, 'Roraima');
insert into estado (id, nome) values (24, 'Santa Catarina');
insert into estado (id, nome) values (25, 'São Paulo');
insert into estado (id, nome) values (26, 'Sergipe');
insert into estado (id, nome) values (27, 'Tocantins');

insert into cidade (nome, estado_id) values ('Uberlãndia', 13);
insert into cidade (nome, estado_id) values ('Uberaba', 13);
insert into cidade (nome, estado_id) values ('Araguari', 13);

insert into forma_pagamento (descricao) values ('A vista credito');
insert into forma_pagamento (descricao) values ('A vista debito');
insert into forma_pagamento (descricao) values ('Parcelado credito');
insert into forma_pagamento (descricao) values ('Pix');
insert into forma_pagamento (descricao) values ('Dinheiro');

insert into permissao (nome, descricao) values ('READ_RESTAURANTE', 'Consultar restaurantes');
insert into permissao (nome, descricao) values ('WRITE_RESTAURANTE', 'Criar/Alterar restaurantes');
insert into permissao (nome, descricao) values ('REMOVE_RESTAURANTE', 'Remover restaurantes'); 
insert into permissao (nome, descricao) values ('READ_COZINHA', 'Consultar cozinhas');
insert into permissao (nome, descricao) values ('WRITE_COZINHA', 'Criar/Alterar cozinhas');
insert into permissao (nome, descricao) values ('REMOVE_COZINHA', 'Remover cozinhas');

insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_bairro, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero, data_cadastro, data_atualizacao) values ('Thai Gourmet', 10, 1, 1, 'Centro', '38400-000', 'Loja 1', 'Rua 1', '100', now(), now());
insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_bairro, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero, data_cadastro, data_atualizacao) values ('Thai Delivery', 9.50, 1, 1, 'Centro', '38400-000', 'Loja 2', 'Rua 2', '200', now(), now());
insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_bairro, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero, data_cadastro, data_atualizacao) values ('Tuk Tuk Comida Indiana', 15, 2, 2, 'Centro', '38400-000', 'Loja 3', 'Rua 3', '300', now(), now()); 

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (1, 4), (1, 5); 
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (2, 1), (2, 2), (2, 4); 
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (3, 3), (3, 4), (3, 5); 

insert into produto (ativo, preco, restaurante_id, nome, descricao) values (1, 50.00, 1, 'Pad Tha', 'Tradicionalmente, é preparado com noodles de arroz, tofu ou camarão, ovo, amendoim, broto de feijão e temperado com molho de tamarindo, açúcar, molho de peixe e pimenta. Cada garfada oferece uma explosão de sabores, tornando-o uma verdadeira experiência gastronômica!');
insert into produto (ativo, preco, restaurante_id, nome, descricao) values (1, 45.50, 1, 'Tom Yum Goong', 'Tom Yum Goong é muito mais do que uma simples sopa. É uma sinfonia de especiarias que dançam harmoniosamente para criar um perfil de sabor único');
insert into produto (ativo, preco, restaurante_id, nome, descricao) values (1, 42.50, 1, 'Pad Kra Pao', 'É um prato tradicional tailandês que se destaca pelo uso do manjericão sagrado (holy basil), alho e pimenta');
