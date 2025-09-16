SET search_path TO core_audit;

create sequence "core_audit"."revision_audited_seq" start with 1 increment by 1;

create table "core_audit"."modified_entity_names"
(
    "revision_id" bigint not null,
    "entity_name" varchar(255)
);

create table "core_audit"."revision_audited"
(
    "id"                    bigint       not null,
    "action_done_by"        bigint,
    "action_done_by_ip"     varchar(255),
    "method_names_tracking" varchar(255) not null,
    "name_action_done_by"   varchar(255) not null,
    "revision_data"         timestamp(6) not null,
    primary key ("id")
);

alter table if exists "core_audit"."modified_entity_names"
    add constraint "FKfxs2soh5qq6e38dggp7sdy996" foreign key ("revision_id") references "core_audit"."revision_audited";

SET search_path TO core_system;