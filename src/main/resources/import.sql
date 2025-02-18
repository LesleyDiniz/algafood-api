insert into cozinha (nome) values ('Tailandesa');
insert into cozinha (nome) values ('Indiana');

insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Gourmet', 10, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Delivery', 9.50, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Tuk Tuk Comida Indiana', 15, 2);

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

