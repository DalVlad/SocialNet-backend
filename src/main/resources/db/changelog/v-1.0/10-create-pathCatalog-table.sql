create table path_catalog (
    id  bigserial not null,
    path varchar(255) not null,
    parent_id int8,
    primary key (id)
)

GO

alter table path_catalog add constraint FK1b3m7sob2i4ap8qg1cgp05e0o foreign key (parent_id) references path_catalog

GO