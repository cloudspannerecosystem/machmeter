CREATE TABLE games (
  game_uuid STRING(36) NOT NULL,
  player1 STRING(36) NOT NULL,
  player2 STRING(36) NOT NULL,
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

CREATE TABLE scores (
  player_uuid STRING(36) NOT NULL,
  score INT64 NOT NULL,
  timestamp TIMESTAMP NOT NULL,
) PRIMARY KEY(player_uuid, timestamp),
  INTERLEAVE IN PARENT players ON DELETE NO ACTION;

ALTER TABLE players ADD FOREIGN KEY(current_game) REFERENCES games(game_uuid);

ALTER TABLE games ADD FOREIGN KEY(winner) REFERENCES players(player_uuid);

ALTER TABLE games ADD FOREIGN KEY(player1) REFERENCES players(player_uuid);

ALTER TABLE games ADD FOREIGN KEY(player2) REFERENCES players(player_uuid);

CREATE UNIQUE INDEX player_auth ON players(email) STORING (password_hash);

CREATE INDEX player_game ON players(current_game);
