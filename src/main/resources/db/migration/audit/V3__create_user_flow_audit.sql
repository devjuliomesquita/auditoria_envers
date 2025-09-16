SET search_path TO core_audit;

create table "core_audit"."profile_permissions_aud"
(
"rev"          bigint       not null,
"profile_id"   bigint       not null,
"permission"   varchar(255),
"revtype"      smallint,
"revend"       bigint,
"revend_tstmp" timestamp(6),
primary key ("profile_id", "rev", "permission")
);
create table "core_audit"."profiles_aud"
(
"id"              bigint not null,
"rev"             bigint not null,
"revtype"         smallint,
"revend"          bigint,
"revend_tstmp"    timestamp(6),
"description"     varchar(255),
"description_mod" boolean,
"name"            varchar(255),
"name_mod"        boolean,
"permissions_mod" boolean,
primary key ("rev", "id")
);

create table "core_audit"."tokens_aud"
(
"id"             bigint not null,
"rev"            bigint not null,
"revtype"        smallint,
"revend"         bigint,
"revend_tstmp"   timestamp(6),
"token_type"     varchar(255),
"token_type_mod" boolean,
"token_value"    varchar(2000),
"value_mod"      boolean,
"user_id"        bigint,
"user_mod"       boolean,
primary key ("rev", "id")
);
create table "core_audit"."users_aud"
(
"id"           bigint not null,
"rev"          bigint not null,
"revtype"      smallint,
"revend"       bigint,
"revend_tstmp" timestamp(6),
"email"        varchar(255),
"email_mod"    boolean,
"name"         varchar(255),
"name_mod"     boolean,
"profile_id"   bigint,
"profile_mod"  boolean,
primary key ("rev", "id")
);

alter table if exists "core_audit"."profile_permissions_aud"
    add constraint "FK55nrcyds8w34esbvklj54caao" foreign key ("rev") references "core_audit"."revision_audited";
alter table if exists "core_audit"."profile_permissions_aud"
    add constraint "FKgis5edbpeo049pmus57n7hjo7" foreign key ("revend") references "core_audit"."revision_audited";
alter table if exists "core_audit"."profiles_aud"
    add constraint "FKbt7ju26kwmff0qlu0r87yp4c4" foreign key ("rev") references "core_audit"."revision_audited";
alter table if exists "core_audit"."profiles_aud"
    add constraint "FKki4t8by6m5ntvhmktk8v1mb25" foreign key ("revend") references "core_audit"."revision_audited";
alter table if exists "core_audit"."tokens_aud"
    add constraint "FK1hcv4wq59u7g02d8li9ci2q9w" foreign key ("rev") references "core_audit"."revision_audited";
alter table if exists "core_audit"."tokens_aud"
    add constraint "FKstp04q2u1p8x27y6y4hx28y04" foreign key ("revend") references "core_audit"."revision_audited";
alter table if exists "core_audit"."users_aud"
    add constraint "FKml147q9xfb2ii866e4gfmbrvx" foreign key ("rev") references "core_audit"."revision_audited";
alter table if exists "core_audit"."users_aud"
    add constraint "FKdpatp0n07jv7aekwtah1u4orr" foreign key ("revend") references "core_audit"."revision_audited";

SET search_path TO core_system;
