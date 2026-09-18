ALTER TABLE pedido add codigo varchar(36) not null after id;

UPDATE pedido set codigo = UUID();

ALTER TABLE pedido add constraint uk_pedido_codigo UNIQUE (codigo);
