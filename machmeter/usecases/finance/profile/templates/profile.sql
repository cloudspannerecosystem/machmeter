CREATE TABLE device_profile (
  device_id STRING(41) NOT NULL,
  user_id STRING(256) NOT NULL,
  biometric_uuid STRING(256),
  is_active BOOL NOT NULL,
  fcm_token STRING(255),
) PRIMARY KEY(device_id);

CREATE INDEX idx_device_profile_user ON device_profile(user_id);

CREATE TABLE profile_transaction_limit (
  user_id STRING(41) NOT NULL,
  transaction_group STRING(16) NOT NULL,
  limit_amount NUMERIC NOT NULL,
) PRIMARY KEY(user_id, transaction_group);

CREATE TABLE user_profile (
  user_id STRING(41) NOT NULL,
  hashed_user_id STRING(128) NOT NULL,
  email STRING(320),
  segment STRING(256) NOT NULL,
  cif_no STRING(12) NOT NULL,
  cid STRING(13),
  passport_no STRING(20),
  passport_country STRING(2),
  date_of_birth STRING(8),
  full_name_en STRING(40),
  first_name_en STRING(17),
  last_name_en STRING(25),
  middle_name_en STRING(20),
  nickname STRING(256),
  profile_picture_file_name STRING(41),
  otp_mobile_no STRING(12),
  accepted_tnc_version STRING(6),
  is_masked_account_no BOOL,
  created_date_time TIMESTAMP NOT NULL,
  created_by STRING(50) NOT NULL,
  updated_date_time TIMESTAMP NOT NULL,
  updated_by STRING(50) NOT NULL,
  is_pin_active BOOL NOT NULL,
) PRIMARY KEY(user_id);

CREATE TABLE user_preference (
  user_id STRING(41) NOT NULL,
  scan_and_go_limit NUMERIC NOT NULL,
  is_login_email_notification_enabled BOOL NOT NULL,
  is_transaction_email_notification_enabled BOOL NOT NULL,
  is_application_setting_email_notification_enabled BOOL NOT NULL,
  is_masking_enabled BOOL NOT NULL,
  is_smart_transactions_enabled BOOL NOT NULL,
  language STRING(5) NOT NULL,
) PRIMARY KEY(user_id);

CREATE TABLE user_quick_actions (
  user_id STRING(41) NOT NULL,
  sort_order INT64 NOT NULL,
  quick_action_code STRING(50) NOT NULL,
) PRIMARY KEY(user_id, sort_order, quick_action_code);

CREATE TABLE user_quick_actions_history (
  user_id STRING(41) NOT NULL,
  quick_actions STRING(2000) NOT NULL,
  created_date_time TIMESTAMP NOT NULL,
  created_by STRING(50) NOT NULL,
  updated_date_time TIMESTAMP NOT NULL,
  updated_by STRING(50) NOT NULL,
  version STRING(6),
) PRIMARY KEY(user_id);