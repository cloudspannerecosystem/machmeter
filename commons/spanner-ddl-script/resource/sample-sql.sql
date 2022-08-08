CREATE TABLE MusicInfo (
    SingerId   INT64 NOT NULL,
    FirstName  STRING(1024),
    LastName   STRING(1024),
    SingerInfo BYTES(MAX),
    Revenues   NUMERIC,
) PRIMARY KEY (SingerId);
