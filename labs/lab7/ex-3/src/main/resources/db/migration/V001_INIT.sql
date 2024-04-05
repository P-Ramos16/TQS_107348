CREATE TABLE books (
  id BIGSERIAL PRIMARY KEY,
  title varchar(255) not null,
  author varchar(255) not null,
  publish_date varchar(255) not null
);