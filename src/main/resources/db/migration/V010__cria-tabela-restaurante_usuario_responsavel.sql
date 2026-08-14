create table restaurante_usuario_responsavel (
	usuario_id bigint not null,
	restaurante_id bigint not null
) engine = InnoDB;


alter table restaurante_usuario_responsavel add constraint FK_restaurante_usuario_responsavel_usuario_id foreign key (usuario_id) references usuario (id);

alter table restaurante_usuario_responsavel add constraint FK_restaurante_usuario_responsavel_restaurante_id foreign key (restaurante_id) references restaurante (id);