create table storage (
    id  bigserial not null,
    path_catalog_root int8,
    person_id int8,
    primary key (id)
)

GO

alter table storage add constraint FKap4v3fyosfbrqjp28jhp5jctc foreign key (path_catalog_root) references path_catalog

GO

alter table storage add constraint FKegkcevty12mqmqrnhq5vocne9 foreign key (person_id) references person

GO