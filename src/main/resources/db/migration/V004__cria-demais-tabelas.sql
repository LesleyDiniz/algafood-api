create table forma_pagamento (
	id bigint not null auto_increment,
	descricao varchar(100) not null,
	primary key (id)
) engine = InnoDB;

create table grupo (
	id bigint not null auto_increment,
	nome varchar(100) not null,
	primary key (id)
) engine = InnoDB;

create table grupo_permissao (
	grupo_id bigint not null,
	permissao_id bigint not null
) engine = InnoDB;

create table permissao (
	id bigint not null auto_increment,
	descricao varchar(255) not null,
	nome varchar(100) not null,
	primary key (id)
) engine = InnoDB;

create table produto (
	ativo bit not null,
	preco decimal(38, 2) not null,
	id bigint not null auto_increment,
	restaurante_id bigint not null,
	descricao TEXT not null,
	nome varchar(150) not null,
	primary key (id)
) engine = InnoDB;

create table restaurante (
	data_atualizacao datetime not null,
	data_cadastro datetime not null,
	taxa_frete decimal(38, 2) not null,
	cozinha_id bigint not null,
	endereco_cidade_id bigint,
	id bigint not null auto_increment,
	endereco_bairro varchar(150),
	endereco_cep varchar(9),
	endereco_complemento varchar(255),
	endereco_logradouro varchar(255),
	endereco_numero varchar(100),
	nome varchar(150) not null,
	primary key (id)
) engine = InnoDB;

create table restaurante_forma_pagamento (
	forma_pagamento_id bigint not null,
	restaurante_id bigint not null
) engine = InnoDB;

create table usuario (
	data_cadastro datetime not null,
	id bigint not null auto_increment,
	email varchar(255) not null,
	nome varchar(150) not null,
	senha varchar(255) not null,
	primary key (id)
) engine = InnoDB;

create table usuario_grupo (
	grupo_id bigint not null,
	usuario_id bigint not null
) engine = InnoDB;

alter table cidade add constraint FKkworrwk40xj58kevvh3evi500 foreign key (estado_id) references estado (id);

alter table grupo_permissao add constraint FKh21kiw0y0hxg6birmdf2ef6vy foreign key (permissao_id) references permissao (id);

alter table grupo_permissao add constraint FKta4si8vh3f4jo3bsslvkscc2m foreign key (grupo_id) references grupo (id);

alter table produto add constraint FKb9jhjyghjcn25guim7q4pt8qx foreign key (restaurante_id) references restaurante (id);

alter table restaurante add constraint FK76grk4roudh659skcgbnanthi foreign key (cozinha_id) references cozinha (id);

alter table restaurante add constraint FKbc0tm7hnvc96d8e7e2ulb05yw foreign key (endereco_cidade_id) references cidade (id);

alter table restaurante_forma_pagamento add constraint FK7aln770m80358y4olr03hyhh2 foreign key (forma_pagamento_id) references forma_pagamento (id);

alter table restaurante_forma_pagamento add constraint FKa30vowfejemkw7whjvr8pryvj foreign key (restaurante_id) references restaurante (id);

alter table usuario_grupo add constraint FKk30suuy31cq5u36m9am4om9ju foreign key (grupo_id) references grupo (id);

alter table usuario_grupo add constraint FKdofo9es0esuiahyw2q467crxw foreign key (usuario_id) references usuario (id);