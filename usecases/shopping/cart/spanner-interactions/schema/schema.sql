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