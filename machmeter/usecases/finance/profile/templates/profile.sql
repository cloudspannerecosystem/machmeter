CREATE TABLE tbl_device_profile (
  device_id STRING(41) NOT NULL,
  user_ref_id STRING(256) NOT NULL,
  biometric_uuid STRING(256),
  is_active BOOL NOT NULL,
  fcm_token STRING(255),
) PRIMARY KEY(device_id);

CREATE INDEX idx_device_profile_user ON tbl_device_profile(user_ref_id);

CREATE TABLE tbl_migrated_user (
  old_user_id STRING(256) NOT NULL,
  user_ref_id STRING(256) NOT NULL,
  is_initialized BOOL NOT NULL,
) PRIMARY KEY(old_user_id);

CREATE TABLE tbl_mobile_detect (
  device_id STRING(41) NOT NULL,
  detected_mobile_no STRING(12) NOT NULL,
  timestamp TIMESTAMP NOT NULL,
) PRIMARY KEY(device_id);

CREATE TABLE tbl_profile_transaction_limit (
  user_ref_id STRING(41) NOT NULL,
  transaction_group STRING(16) NOT NULL,
  limit_amount NUMERIC NOT NULL,
) PRIMARY KEY(user_ref_id, transaction_group);

CREATE TABLE tbl_quick_link_favorite (
  favorite_ref_id STRING(41) NOT NULL,
  user_ref_id STRING(41) NOT NULL,
  favorite_nickname STRING(20) NOT NULL,
  transaction_type STRING(16) NOT NULL,
  transfer_type STRING(16),
  to_bank_code STRING(3),
  sort_order INT64 NOT NULL,
  to_proxy_type STRING(16),
  to_icon_url STRING(255),
  is_category_icon BOOL,
) PRIMARY KEY(user_ref_id, favorite_ref_id);

CREATE TABLE tbl_segment (
  segment_id INT64 NOT NULL,
  segment STRING(40) NOT NULL,
  segment_display_name_th STRING(40) NOT NULL,
  segment_display_name_en STRING(40) NOT NULL,
) PRIMARY KEY(segment_id);

CREATE TABLE tbl_transaction_limit (
  segment STRING(16) NOT NULL,
  transaction_group STRING(16) NOT NULL,
  daily_limit NUMERIC NOT NULL,
  default_daily_limit NUMERIC NOT NULL,
) PRIMARY KEY(segment, transaction_group);

CREATE TABLE tbl_user_default_quick_action (
  user_ref_id STRING(41) NOT NULL,
  multiple_quick_action_code STRING(500) NOT NULL,
  version STRING(10) NOT NULL,
  created_date_time TIMESTAMP NOT NULL,
  created_by STRING(50) NOT NULL,
  updated_date_time TIMESTAMP NOT NULL,
  updated_by STRING(50) NOT NULL,
) PRIMARY KEY(user_ref_id);

CREATE TABLE tbl_user_ekyc (
  user_ref_id STRING(41) NOT NULL,
  device_id STRING(41) NOT NULL,
  dopa_verify_flag BOOL,
  dopa_verify_date_time TIMESTAMP,
  biometric_verify_flag BOOL,
  biometric_verify_date_time TIMESTAMP,
  dipchip_verify_flag BOOL,
  id_card_expiry_date DATE,
  is_cid_lifetime BOOL NOT NULL,
  dopa_verify_channel STRING(30),
  dipchip_verify_date_time TIMESTAMP,
  dipchip_verify_channel STRING(30),
  ep_last_updated_date_time TIMESTAMP,
  id_card_expiration_date_channel STRING(30),
  biometric_verify_channel STRING(30),
) PRIMARY KEY(user_ref_id);

CREATE TABLE tbl_user_preference (
  user_ref_id STRING(41) NOT NULL,
  scan_and_go_limit NUMERIC NOT NULL,
  is_login_email_notification_enabled BOOL NOT NULL,
  is_transaction_email_notification_enabled BOOL NOT NULL,
  is_application_setting_email_notification_enabled BOOL NOT NULL,
  is_masking_enabled BOOL NOT NULL,
  is_smart_transactions_enabled BOOL NOT NULL,
  language STRING(5) NOT NULL,
) PRIMARY KEY(user_ref_id);

CREATE TABLE tbl_user_preference_quick_action (
  user_ref_id STRING(41) NOT NULL,
  quick_actions STRING(1000) NOT NULL,
  created_date_time TIMESTAMP NOT NULL,
  created_by STRING(50) NOT NULL,
  updated_date_time TIMESTAMP NOT NULL,
  updated_by STRING(50) NOT NULL,
) PRIMARY KEY(user_ref_id);

CREATE TABLE tbl_user_profile (
  user_ref_id STRING(41) NOT NULL,
  hashed_user_ref_id STRING(128) NOT NULL,
  email STRING(320),
  segment STRING(256) NOT NULL,
  cif_no STRING(12) NOT NULL,
  cid STRING(13),
  passport_no STRING(20),
  passport_country STRING(2),
  date_of_birth STRING(8),
  full_name_th STRING(40),
  full_name_en STRING(40),
  first_name_th STRING(17),
  first_name_en STRING(17),
  last_name_th STRING(20),
  last_name_en STRING(25),
  middle_name_th STRING(20),
  middle_name_en STRING(20),
  nickname STRING(256),
  profile_picture_file_name STRING(41),
  otp_mobile_no STRING(12),
  accepted_tnc_version STRING(6),
  is_masked_account_no BOOL,
  is_block_by_staff BOOL NOT NULL,
  block_by_staff_reason STRING(100),
  block_by_staff_branch_name STRING(100),
  block_by_staff_employee_id STRING(20),
  is_block_by_fraud BOOL NOT NULL,
  block_by_fraud_reason STRING(100),
  block_by_fraud_employee_id STRING(20),
  is_scan_and_go_allowed BOOL NOT NULL,
  is_push_notification_enabled BOOL NOT NULL,
  travel_card_visa_accepted_tnc_version STRING(6),
  travel_card_upi_accepted_tnc_version STRING(6),
  global_wallet_accepted_tnc_version STRING(6),
  bypass_type STRING(16) NOT NULL,
  ndid_accepted_tnc_version STRING(6),
  idp_accepted_tnc_version STRING(6),
  is_ndid_enrolled BOOL NOT NULL,
  accepted_ekyc_consent_version STRING(6),
  created_date_time TIMESTAMP NOT NULL,
  created_by STRING(50) NOT NULL,
  updated_date_time TIMESTAMP NOT NULL,
  updated_by STRING(50) NOT NULL,
  is_pin_active BOOL NOT NULL,
  mutual_fund_accepted_tnc_version STRING(6),
  hvd_accepted_tnc_version STRING(6),
  inw_accepted_tnc_version STRING(6),
  remittance_accepted_tnc_version STRING(6),
  virtual_debit_card_accepted_tnc_version STRING(6),
  promptpay_accepted_tnc_version STRING(6),
) PRIMARY KEY(user_ref_id);

CREATE TABLE tbl_user_quick_actions (
  user_ref_id STRING(41) NOT NULL,
  sort_order INT64 NOT NULL,
  quick_action_code STRING(50) NOT NULL,
) PRIMARY KEY(user_ref_id, sort_order, quick_action_code);

CREATE TABLE tbl_user_quick_actions_history (
  user_ref_id STRING(41) NOT NULL,
  quick_actions STRING(2000) NOT NULL,
  created_date_time TIMESTAMP NOT NULL,
  created_by STRING(50) NOT NULL,
  updated_date_time TIMESTAMP NOT NULL,
  updated_by STRING(50) NOT NULL,
  version STRING(6),
) PRIMARY KEY(user_ref_id);