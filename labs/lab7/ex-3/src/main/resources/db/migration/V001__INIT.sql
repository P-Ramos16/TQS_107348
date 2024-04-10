CREATE TABLE books (
  id BIGSERIAL PRIMARY KEY,
  title varchar(255) not null,
  author varchar(255) not null,
  publish_date varchar(255) not null
);

INSERT INTO books VALUES (1, 'The wonders of the world', 'Josefino', '8-2-2012'),
                         (124, 'The Great Adventure', 'Emily Johnson', '2023-05-15'),
                         (125, 'Mystery at Midnight', 'Michael Smith', '2020-09-28'),
                         (126, 'The Lost Kingdom', 'Sophia Lee', '2021-12-10'),
                         (127, 'Journey to the Stars', 'David Brown', '2022-07-03');