CREATE TABLE game_items (
  item_uuid STRING(36) NOT NULL,
  item_name STRING(MAX) NOT NULL,
  item_value FLOAT64 NOT NULL,
  available_time TIMESTAMP NOT NULL,
  duration INT64,
) PRIMARY KEY(item_uuid);

CREATE TABLE games (
  game_uuid STRING(36) NOT NULL,
  players ARRAY<STRING(36)> NOT NULL,
  winner STRING(36),
  created TIMESTAMP,
  finished TIMESTAMP,
) PRIMARY KEY(game_uuid);

CREATE TABLE players (
  player_uuid STRING(36) NOT NULL,
  player_name STRING(64) NOT NULL,
  email STRING(MAX) NOT NULL,
  password_hash STRING(60),
  created TIMESTAMP,
  updated TIMESTAMP,
  stats JSON,
  account_balance FLOAT64 NOT NULL DEFAULT (0.00),
  is_logged_in BOOL,
  last_login TIMESTAMP,
  current_game STRING(36),
) PRIMARY KEY(player_uuid);

CREATE TABLE player_items (
  player_item_uuid STRING(36) NOT NULL,
  player_uuid STRING(36) NOT NULL,
  item_uuid STRING(36) NOT NULL,
  price FLOAT64 NOT NULL,
  game_session STRING(36) NOT NULL,
  acquire_time TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP()),
  expires_time TIMESTAMP,
  FOREIGN KEY(item_uuid) REFERENCES game_items(item_uuid),
  FOREIGN KEY(game_session) REFERENCES games(game_uuid),
  FOREIGN KEY(player_uuid) REFERENCES players(player_uuid),
) PRIMARY KEY(player_uuid, player_item_uuid),
  INTERLEAVE IN PARENT players ON DELETE CASCADE;

CREATE TABLE scores (
  player_uuid STRING(36) NOT NULL,
  score INT64 NOT NULL,
  timestamp TIMESTAMP NOT NULL,
) PRIMARY KEY(player_uuid, timestamp),
  INTERLEAVE IN PARENT players ON DELETE NO ACTION;

ALTER TABLE players ADD FOREIGN KEY(current_game) REFERENCES games(game_uuid);

ALTER TABLE games ADD FOREIGN KEY(winner) REFERENCES players(player_uuid);

CREATE UNIQUE INDEX player_auth ON players(email) STORING (password_hash);

CREATE INDEX player_game ON players(current_game);
