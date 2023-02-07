/*
 * Copyright 2022 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

CREATE TABLE actors (
  id INT64 NOT NULL,
  first_name STRING(100),
  last_name STRING(100),
  gender STRING(1),
) PRIMARY KEY(id);

CREATE INDEX actors_first_name ON actors(first_name);

CREATE INDEX actors_last_name ON actors(last_name);

CREATE TABLE roles (
  id INT64 NOT NULL,
  movie_id INT64 NOT NULL,
  role STRING(100) NOT NULL,
) PRIMARY KEY(id, movie_id, role),
  INTERLEAVE IN PARENT actors ON DELETE CASCADE;

CREATE INDEX actor_id ON roles(id);

CREATE INDEX movie_id ON roles(movie_id);

CREATE TABLE directors (
  id INT64 NOT NULL,
  first_name STRING(100),
  last_name STRING(100),
) PRIMARY KEY(id);

CREATE INDEX directors_first_name ON directors(first_name);

CREATE INDEX directors_last_name ON directors(last_name);

CREATE TABLE directors_genres (
  id INT64 NOT NULL,
  genre STRING(100) NOT NULL,
  prob FLOAT64,
) PRIMARY KEY(id, genre),
  INTERLEAVE IN PARENT directors ON DELETE CASCADE;

CREATE INDEX directors_genres_director_id ON directors_genres(id);

CREATE TABLE movies_directors (
  id INT64 NOT NULL,
  movie_id INT64 NOT NULL,
) PRIMARY KEY(id, movie_id),
  INTERLEAVE IN PARENT directors ON DELETE CASCADE;

CREATE INDEX movies_directors_director_id ON movies_directors(id);

CREATE INDEX movies_directors_movie_id ON movies_directors(movie_id);

CREATE TABLE movies (
  id INT64 NOT NULL,
  name STRING(100),
  year INT64,
  rank FLOAT64,
) PRIMARY KEY(id);

ALTER TABLE movies_directors ADD CONSTRAINT movies_directors_ibfk_2 FOREIGN KEY(movie_id) REFERENCES movies(id);

ALTER TABLE roles ADD CONSTRAINT roles_ibfk_2 FOREIGN KEY(movie_id) REFERENCES movies(id);

CREATE INDEX movies_name ON movies(name);

CREATE TABLE movies_genres (
  id INT64 NOT NULL,
  genre STRING(100) NOT NULL,
) PRIMARY KEY(id, genre),
  INTERLEAVE IN PARENT movies ON DELETE CASCADE;

CREATE INDEX movies_genres_movie_id ON movies_genres(id);