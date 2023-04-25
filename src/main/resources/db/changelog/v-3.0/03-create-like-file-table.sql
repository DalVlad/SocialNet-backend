create table like_file (
    id  bigserial not null,
    file_id int8 not null,
    person_id int8 not null,
    FOREIGN KEY (file_id) REFERENCES file (id),
    FOREIGN KEY (person_id) REFERENCES person (id),
    primary key (id)
)

GO