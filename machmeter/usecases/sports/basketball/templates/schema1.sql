CREATE TABLE player (
  id INT64 NOT NULL,
  first_name STRING(100),
  last_name STRING(100),
) PRIMARY KEY(id);

CREATE TABLE country (
  id INT64 NOT NULL,
  country_name STRING(100),
) PRIMARY KEY(id);

CREATE TABLE team (
  id INT64 NOT NULL,
  team_name STRING(100),
  country_id INT64 NOT NULL,
) PRIMARY KEY(id, country_id);

ALTER TABLE team ADD CONSTRAINT team_fk_country FOREIGN KEY(country_id) REFERENCES country(id);

CREATE TABLE team_players (
  id INT64 NOT NULL,
  player_id INT64 NOT NULL,
  team_id INT64 NOT NULL,
) PRIMARY KEY(id, player_id, team_id);

ALTER TABLE team_players ADD CONSTRAINT team_players_fk_playerId FOREIGN KEY(player_id) REFERENCES player(id);

ALTER TABLE team_players ADD CONSTRAINT team_players_fk_teamId FOREIGN KEY(team_id) REFERENCES team(id);


CREATE TABLE game (
  id INT64 NOT NULL,
  team1_id INT64 NOT NULL,
  team2_id INT64 NOT NULL,
  date DATE NOT NULL,
) PRIMARY KEY(id);

ALTER TABLE game ADD CONSTRAINT fame_fk_team1Id FOREIGN KEY(team1_id) REFERENCES team(id);

ALTER TABLE game ADD CONSTRAINT fame_fk_team2Id FOREIGN KEY(team2_id) REFERENCES team(id);

