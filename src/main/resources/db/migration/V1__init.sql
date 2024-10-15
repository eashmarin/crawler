CREATE TABLE urls
(
    id                 BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    url                VARCHAR UNIQUE NOT NULL
);

INSERT INTO urls (url) VALUES
('https://www.roscosmos.ru/40895/'),
('https://minobrnauki.gov.ru/press-center/news/nauka-i-obrazovanie/88650/');

CREATE TABLE crawled_urls
(
    id                 BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    url                VARCHAR UNIQUE NOT NULL
);


CREATE TABLE links_between_url
(
    id                 BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    from_url_ref       BIGINT REFERENCES urls NOT NULL,
    to_url_ref         BIGINT REFERENCES urls NOT NULL
);

CREATE TABLE words
(
    id                 BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    word               VARCHAR UNIQUE NOT NULL,
    is_filtered        BOOL NOT NULL
);

CREATE TABLE word_locations
(
    id                 BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    word_ref           BIGINT REFERENCES words NOT NULL,
    url_ref            BIGINT REFERENCES urls NOT NULL,
    "location"         INT NOT NULL CHECK ("location" >= 0),

    UNIQUE (url_ref, "location")
);

CREATE TABLE link_words
(
    id                 BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    word_ref           BIGINT REFERENCES words NOT NULL,
    link_ref           BIGINT REFERENCES links_between_url NOT NULL
);