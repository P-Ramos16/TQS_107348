CREATE TABLE cars (
  id BIGSERIAL PRIMARY KEY,
  maker varchar(255) not null,
  model varchar(255) not null
);

INSERT INTO cars (maker, model) VALUES ('BMW', '240i'),
                       ('Honda', 'Civic'),
                       ('Suzuki', 'Swift');