CREATE TABLE IF NOT EXISTS usuario
(
  login text NOT NULL,
  email text,
  nome text,
  senha text,
  pontos integer,
  CONSTRAINT usuario_pkey PRIMARY KEY (login)
);

CREATE SEQUENCE IF NOT EXISTS topico_id_topico_seq
  AS BIGINT START WITH 1;

CREATE TABLE IF NOT EXISTS topico
(
  id_topico integer NOT NULL DEFAULT NEXT VALUE FOR topico_id_topico_seq,
  titulo text,
  conteudo text,
  login text,
  CONSTRAINT topico_pkey PRIMARY KEY (id_topico),
  CONSTRAINT topico_login_fkey FOREIGN KEY (login)
      REFERENCES usuario (login)
);

CREATE SEQUENCE IF NOT EXISTS comentario_id_comentario_seq
  AS BIGINT START WITH 1;

CREATE TABLE IF NOT EXISTS comentario
(
  id_comentario integer NOT NULL DEFAULT NEXT VALUE FOR comentario_id_comentario_seq,
  comentario text,
  login text,
  id_topico integer,
  CONSTRAINT comentario_pkey PRIMARY KEY (id_comentario),
  CONSTRAINT comentario_id_topico_fkey FOREIGN KEY (id_topico)
      REFERENCES topico (id_topico),
  CONSTRAINT comentario_login_fkey FOREIGN KEY (login)
      REFERENCES usuario (login)
);
