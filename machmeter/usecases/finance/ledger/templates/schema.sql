CREATE TABLE user_bal (
  userId STRING(128) NOT NULL,
  cSpent INT64,
  cEarn INT64,
  cEarnToday INT64,
  balance FLOAT64 NOT NULL,
  engmtLvl STRING(MAX),
  streakDay STRING(MAX),
  source STRING(MAX),
  cEarnUpdatedAt INT64,
  engmtLvlUpdatedAt INT64,
  streakDayUpdatedAt INT64,
  creationTime TIMESTAMP,
  updationTime TIMESTAMP,
  currStreakFirstEarnAt INT64,
  sourceTransactLog JSON,
) PRIMARY KEY(userId);

CREATE TABLE user_txns (
  userId STRING(128) NOT NULL,
  transactionId STRING(128) NOT NULL,
  org_transId STRING(128),
  amount FLOAT64 NOT NULL,
  balance FLOAT64,
  currency STRING(50),
  streakDay STRING(MAX),
  transactionType STRING(50),
  transactionStatus STRING(50),
  RewardType STRING(MAX),
  refType STRING(50),
  refTypeId STRING(128),
  transactionSource STRING(60),
  creationTime TIMESTAMP,
) PRIMARY KEY(userId, transactionId),
  INTERLEAVE IN PARENT user_bal ON DELETE CASCADE;

CREATE INDEX user_txnsByTransactionId ON user_txns(transactionId);