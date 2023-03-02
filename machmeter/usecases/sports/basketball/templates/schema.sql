CREATE TABLE awards_coaches (
  id INT64 NOT NULL,
  year INT64,
  coachID STRING(255),
  award STRING(255),
  lgID STRING(255),
  note STRING(255),
) PRIMARY KEY(id);

CREATE INDEX coachID_2 ON awards_coaches(coachID, year);

CREATE INDEX coachID_29 ON awards_coaches(coachID);

CREATE INDEX coachID_3 ON awards_coaches(coachID, year);

CREATE INDEX year_2_33 ON awards_coaches(year, coachID);

CREATE INDEX year_32 ON awards_coaches(year, coachID);

CREATE INDEX year_3_34 ON awards_coaches(year, coachID, lgID);

CREATE TABLE awards_players (
  playerID STRING(255) NOT NULL,
  award STRING(255) NOT NULL,
  year INT64 NOT NULL,
  lgID STRING(255),
  note STRING(255),
  pos STRING(255),
) PRIMARY KEY(playerID, year, award);

CREATE INDEX playerID_37 ON awards_players(playerID);

CREATE TABLE coaches (
  coachID STRING(255) NOT NULL,
  year INT64 NOT NULL,
  tmID STRING(255) NOT NULL,
  lgID STRING(255),
  stint INT64 NOT NULL,
  won INT64,
  lost INT64,
  post_wins INT64,
  post_losses INT64,
) PRIMARY KEY(coachID, year, tmID, stint);

CREATE INDEX coachID ON coaches(coachID);

CREATE INDEX tmID ON coaches(tmID, year);

CREATE INDEX tmID_2 ON coaches(tmID, year);

CREATE INDEX year ON coaches(year, coachID);

CREATE INDEX year_2 ON coaches(year, coachID, lgID);

CREATE INDEX year_3 ON coaches(year, tmID);

CREATE INDEX year_4 ON coaches(year, tmID);

CREATE TABLE draft (
  id INT64 NOT NULL,
  draftYear INT64,
  draftRound INT64,
  draftSelection INT64,
  draftOverall INT64,
  tmID STRING(255),
  firstName STRING(255),
  lastName STRING(255),
  suffixName STRING(255),
  playerID STRING(255),
  draftFrom STRING(255),
  lgID STRING(255),
) PRIMARY KEY(id);

CREATE INDEX tmID_12 ON draft(tmID, draftYear);

CREATE TABLE player_allstar (
  playerID STRING(255) NOT NULL,
  last_name STRING(255),
  first_name STRING(255),
  season_id INT64 NOT NULL,
  conference STRING(255),
  league_id STRING(255),
  games_played INT64,
  minutes INT64,
  points INT64,
  o_rebounds INT64,
  d_rebounds INT64,
  rebounds INT64,
  assists INT64,
  steals INT64,
  blocks INT64,
  turnovers INT64,
  personal_fouls INT64,
  fg_attempted INT64,
  fg_made INT64,
  ft_attempted INT64,
  ft_made INT64,
  three_attempted INT64,
  three_made INT64,
) PRIMARY KEY(playerID, season_id);

CREATE INDEX player_id ON player_allstar(playerID);

CREATE TABLE players (
  playerID STRING(255) NOT NULL,
  useFirst STRING(255),
  firstName STRING(255),
  middleName STRING(255),
  lastName STRING(255),
  nameGiven STRING(255),
  fullGivenName STRING(255),
  nameSuffix STRING(255),
  nameNick STRING(255),
  pos STRING(255),
  firstseason INT64,
  lastseason INT64,
  height FLOAT64,
  weight INT64,
  college STRING(255),
  collegeOther STRING(255),
  birthDate DATE,
  birthCity STRING(255),
  birthState STRING(255),
  birthCountry STRING(255),
  highSchool STRING(255),
  hsCity STRING(255),
  hsState STRING(255),
  hsCountry STRING(255),
  deathDate DATE,
  race STRING(255),
) PRIMARY KEY(playerID);

CREATE TABLE players_teams (
  id INT64 NOT NULL,
  playerID STRING(255) NOT NULL,
  year INT64,
  stint INT64,
  tmID STRING(255),
  lgID STRING(255),
  GP INT64,
  GS INT64,
  minutes INT64,
  points INT64,
  oRebounds INT64,
  dRebounds INT64,
  rebounds INT64,
  assists INT64,
  steals INT64,
  blocks INT64,
  turnovers INT64,
  PF INT64,
  fgAttempted INT64,
  fgMade INT64,
  ftAttempted INT64,
  ftMade INT64,
  threeAttempted INT64,
  threeMade INT64,
  PostGP INT64,
  PostGS INT64,
  PostMinutes INT64,
  PostPoints INT64,
  PostoRebounds INT64,
  PostdRebounds INT64,
  PostRebounds INT64,
  PostAssists INT64,
  PostSteals INT64,
  PostBlocks INT64,
  PostTurnovers INT64,
  PostPF INT64,
  PostfgAttempted INT64,
  PostfgMade INT64,
  PostftAttempted INT64,
  PostftMade INT64,
  PostthreeAttempted INT64,
  PostthreeMade INT64,
  note STRING(255),
) PRIMARY KEY(playerID, id),
  INTERLEAVE IN PARENT players ON DELETE NO ACTION;

CREATE INDEX fgAttempted ON players_teams(fgAttempted);

CREATE INDEX fgAttempted_2 ON players_teams(fgAttempted);

CREATE INDEX playerID ON players_teams(playerID);

CREATE INDEX tmID_23 ON players_teams(tmID, year);

CREATE INDEX tmID_2_24 ON players_teams(tmID, year);

CREATE INDEX tmID_3 ON players_teams(tmID, year);

CREATE INDEX year_26 ON players_teams(year, tmID);

CREATE TABLE series_post (
  id INT64 NOT NULL,
  year INT64,
  round STRING(255),
  series STRING(255),
  tmIDWinner STRING(255),
  lgIDWinner STRING(255),
  tmIDLoser STRING(255),
  lgIDLoser STRING(255),
  W INT64,
  L INT64,
) PRIMARY KEY(id);

CREATE INDEX tmIDLoser ON series_post(tmIDLoser, year);

CREATE INDEX tmIDWinner ON series_post(tmIDWinner, year);

CREATE TABLE teams (
  year INT64 NOT NULL,
  lgID STRING(255),
  tmID STRING(255) NOT NULL,
  franchID STRING(255),
  confID STRING(255),
  divID STRING(255),
  rank INT64,
  confRank INT64,
  playoff STRING(255),
  name STRING(255),
  o_fgm INT64,
  o_fga INT64,
  o_ftm INT64,
  o_fta INT64,
  o_3pm INT64,
  o_3pa INT64,
  o_oreb INT64,
  o_dreb INT64,
  o_reb INT64,
  o_asts INT64,
  o_pf INT64,
  o_stl INT64,
  o_to INT64,
  o_blk INT64,
  o_pts INT64,
  d_fgm INT64,
  d_fga INT64,
  d_ftm INT64,
  d_fta INT64,
  d_3pm INT64,
  d_3pa INT64,
  d_oreb INT64,
  d_dreb INT64,
  d_reb INT64,
  d_asts INT64,
  d_pf INT64,
  d_stl INT64,
  d_to INT64,
  d_blk INT64,
  d_pts INT64,
  o_tmRebound INT64,
  d_tmRebound INT64,
  homeWon INT64,
  homeLost INT64,
  awayWon INT64,
  awayLost INT64,
  neutWon INT64,
  neutLoss INT64,
  confWon INT64,
  confLoss INT64,
  divWon INT64,
  divLoss INT64,
  pace INT64,
  won INT64,
  lost INT64,
  games INT64,
  min INT64,
  arena STRING(255),
  attendance INT64,
  bbtmID STRING(255),
) PRIMARY KEY(year, tmID);

CREATE INDEX tmID_2_44 ON teams(tmID, year);

CREATE INDEX tmID_43 ON teams(tmID);