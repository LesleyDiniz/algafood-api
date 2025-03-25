create table estado (
  id bigint not null,
  nome varchar(80) not null,
  
  primary key (id)
) engine=InnoDB default charset=utf8;

SET @rownum := 0;
insert into estado (id, nome)
SELECT @rownum:=(@rownum + 1), nome_estado FROM (SELECT DISTINCT nome_estado FROM cidade) AS T;

ALTER TABLE cidade
ADD COLUMN estado_id BIGINT NOT NULL;

UPDATE cidade c SET c.estado_id = (SELECT e.id FROM estado e WHERE e.nome = c.nome_estado);

ALTER TABLE cidade
ADD CONSTRAINT fk_cidade_estado
FOREIGN KEY (estado_id) REFERENCES estado(id);

ALTER TABLE cidade
DROP COLUMN nome_estado;

ALTER TABLE cidade
CHANGE nome_cidade nome varchar(80) not null;
