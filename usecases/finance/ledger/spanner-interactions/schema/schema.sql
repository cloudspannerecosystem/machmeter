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

CREATE TABLE user_bal (
 userId STRING(128) NOT NULL,
 cSpent INT64,
 cEarn INT64,
 cEarnToday INT64,
 balance FLOAT64 NOT NULL,
 engmtLvl STRING(30),
 streakDay STRING(30),
 source STRING(60),
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
 streakDay STRING(30),
 transactionType STRING(50),
 transactionStatus STRING(50),
 RewardType STRING(50),
 refType STRING(50),
 refTypeId STRING(128),
 transactionSource STRING(60),
 creationTime TIMESTAMP,
) PRIMARY KEY(userId, transactionId),
 INTERLEAVE IN PARENT user_bal ON DELETE CASCADE;
 
CREATE INDEX user_txnsByTransactionId ON user_txns(transactionId);