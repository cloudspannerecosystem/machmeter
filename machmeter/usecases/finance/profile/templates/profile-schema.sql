CREATE TABLE device_profile (
  deviceId STRING(41) NOT NULL,
  userId STRING(256) NOT NULL,
  bioUUID STRING(256),
  isActive BOOL NOT NULL,
  tokenId STRING(255),
) PRIMARY KEY(deviceId);

CREATE INDEX idx_device_profile_user ON device_profile(userId);

CREATE TABLE transaction_limit (
  userId STRING(41) NOT NULL,
  txnGroup STRING(16) NOT NULL,
  limitAmt NUMERIC NOT NULL,
) PRIMARY KEY(userId, txnGroup);

CREATE TABLE user_profile (
  userId STRING(41) NOT NULL,
  hashedUserId STRING(128) NOT NULL,
  email STRING(320),
  segment STRING(256) NOT NULL,
  bankAcctNumber STRING(12) NOT NULL,
  cardId STRING(13),
  birthDate STRING(8),
  firstNameEN STRING(17),
  lastNameEN STRING(25),
  midNameEN STRING(20),
  nickName STRING(256),
  mobileNumber STRING(12),
  isMaskedAcct BOOL,
  createdDate TIMESTAMP NOT NULL,
  createdBy STRING(50) NOT NULL,
  updatedDate TIMESTAMP NOT NULL,
  updatedBy STRING(50) NOT NULL,
  isPinActive BOOL NOT NULL,
) PRIMARY KEY(userId);

CREATE TABLE user_preference (
  userId STRING(41) NOT NULL,
  scanLimit NUMERIC NOT NULL,
  isNotificationEnabled BOOL NOT NULL,
  language STRING(5) NOT NULL,
) PRIMARY KEY(userId);

CREATE TABLE user_quick_actions (
  userId STRING(41) NOT NULL,
  sortOrder INT64 NOT NULL,
  codeQuickAction STRING(50) NOT NULL,
) PRIMARY KEY(userId, sortOrder, codeQuickAction);

CREATE TABLE user_quick_actions_history (
  userId STRING(41) NOT NULL,
  quickAction STRING(2000) NOT NULL,
  createdDate TIMESTAMP NOT NULL,
  createdBy STRING(50) NOT NULL,
  updatedDate TIMESTAMP NOT NULL,
  updatedBy STRING(50) NOT NULL,
  version STRING(6),
) PRIMARY KEY(userId);