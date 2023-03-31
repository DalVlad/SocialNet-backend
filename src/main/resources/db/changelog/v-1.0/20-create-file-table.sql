create table file (
    id  bigserial not null,
    extension varchar(255) not null,
    file_byte bytea, name varchar(255),
    path_catalog_id int8,
    primary key (id)
)

GO

alter table file add constraint FK3b30nfqtdmpbcjw5ttwfpujhm foreign key (path_catalog_id) references path_catalog

GO
