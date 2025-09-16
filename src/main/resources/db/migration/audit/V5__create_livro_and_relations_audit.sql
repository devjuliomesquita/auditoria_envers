SET search_path TO core_audit;

create table "core_audit"."autores_aud"
(
"id"           bigint not null,
"rev"          bigint not null,
"revtype"      smallint,
"revend"       bigint,
"revend_tstmp" timestamp(6),
"nome_autor"   varchar(250),
"nome_mod"     boolean,
"livros_mod"   boolean,
primary key ("rev", "id")
);
create table "core_audit"."detalhes_aud"
(
"id"             bigint not null,
"rev"            bigint not null,
"revtype"        smallint,
"revend"         bigint,
"revend_tstmp"   timestamp(6),
"isbn_codigo"    varchar(255),
"isbn_mod"       boolean,
"editora_nome"   varchar(255),
"num_paginas"    integer,
"ano_publicacao" integer,
"publicacao_mod" boolean,
"livro_id"       bigint,
"livro_mod"      boolean,
primary key ("rev", "id")
);
create table "core_audit"."exemplares_aud"
(
"id"                bigint not null,
"rev"               bigint not null,
"revtype"           smallint,
"revend"            bigint,
"revend_tstmp"      timestamp(6),
"codigo_barras"     varchar(255),
"codigo_barras_mod" boolean,
"livro_id"          bigint,
"livro_mod"         boolean,
primary key ("rev", "id")
);
create table "core_audit"."livro_autor_assoc_aud"
(
"rev"          bigint not null,
"livro_id"     bigint not null,
"autor_id"     bigint not null,
"revtype"      smallint,
"revend"       bigint,
"revend_tstmp" timestamp(6),
primary key ("livro_id", "rev", "autor_id")
);
create table "core_audit"."livros_aud"
(
"id"             bigint not null,
"rev"            bigint not null,
"revtype"        smallint,
"revend"         bigint,
"revend_tstmp"   timestamp(6),
"titulo"         varchar(255),
"titulo_mod"     boolean,
"autores_mod"    boolean,
"exemplares_mod" boolean,
"detalhe_mod"    boolean,
primary key ("rev", "id")
);

alter table if exists "core_audit"."autores_aud"
    add constraint "FKahe8bgmyarq1u5l4qclgrlvxy" foreign key ("rev") references "core_audit"."revision_audited";
alter table if exists "core_audit"."autores_aud"
    add constraint "FK5vvk1veopr9vn18otj5l1xw5h" foreign key ("revend") references "core_audit"."revision_audited";
alter table if exists "core_audit"."detalhes_aud"
    add constraint "FKnc2i31x5rf0q6bl7xemw8886t" foreign key ("rev") references "core_audit"."revision_audited";
alter table if exists "core_audit"."detalhes_aud"
    add constraint "FKqbps9r1qgymmnfpruynyut46f" foreign key ("revend") references "core_audit"."revision_audited";
alter table if exists "core_audit"."exemplares_aud"
    add constraint "FKnc6xhegxt08bflkyfgowjdjl3" foreign key ("rev") references "core_audit"."revision_audited";
alter table if exists "core_audit"."exemplares_aud"
    add constraint "FKghd7eifdsj398yr94hnc1o9lj" foreign key ("revend") references "core_audit"."revision_audited";
alter table if exists "core_audit"."livro_autor_assoc_aud"
    add constraint "FK2c1pho4c75fih231updyly591" foreign key ("rev") references "core_audit"."revision_audited";
alter table if exists "core_audit"."livro_autor_assoc_aud"
    add constraint "FKepdnvqcv2cmwt263u8jghr6xt" foreign key ("revend") references "core_audit"."revision_audited";
alter table if exists "core_audit"."livros_aud"
    add constraint "FK3cn2fmcggp3vgid2i825maq0o" foreign key ("rev") references "core_audit"."revision_audited";
alter table if exists "core_audit"."livros_aud"
    add constraint "FK55mc4y7t49vkl7fvsjod5p8xt" foreign key ("revend") references "core_audit"."revision_audited";

SET search_path TO core_system;