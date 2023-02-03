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

CREATE TABLE Item (
  ItemID        STRING(36)  NOT NULL,
  Description   STRING(100) NOT NULL,
  Price         FLOAT64     NOT NULL,
  Quantity      INT64       NOT NULL,
  OnHold        INT64       NOT NULL,
  LastUpdated   TIMESTAMP   NOT NULL OPTIONS (
    allow_commit_timestamp = true
  ),
) PRIMARY KEY(ItemID);

CREATE TABLE ShoppingCart (
  ShoppingCartID STRING(36)  NOT NULL,
  Status         STRING(50)  NOT NULL,
  UserName       STRING(100) NOT NULL,
  LastUpdated    TIMESTAMP   NOT NULL OPTIONS (
    allow_commit_timestamp = true
  ),
) PRIMARY KEY(ShoppingCartID);

CREATE TABLE ShoppingCartItem (
  ShoppingCartID STRING(36) NOT NULL,
  ItemID         STRING(36) NOT NULL,
  LastUpdated    TIMESTAMP  NOT NULL OPTIONS (
    allow_commit_timestamp = true
  ),
  CONSTRAINT FK_ItemID FOREIGN KEY (ItemID) REFERENCES Item (ItemID),
) PRIMARY KEY(ShoppingCartID, ItemID),
  INTERLEAVE IN PARENT ShoppingCart ON DELETE CASCADE;
--  https://levelup.gitconnected.com/google-cloud-spanner-many-to-many-relationship-pattern-3f97d4e578e