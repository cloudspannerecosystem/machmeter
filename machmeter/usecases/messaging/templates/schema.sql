CREATE TABLE Users (
 userId INT64 NOT NULL,
 userName STRING(100),
 dpUrl STRING(1024),
 status STRING(1024),
 firstName STRING(100),
 lastName STRING(100),
 creationDate TIMESTAMP,
) PRIMARY KEY(userId);

CREATE TABLE Spaces (
 spaceId INT64 NOT NULL,
 spaceName STRING(100),
 spaceDescription STRING(1024),
 createdAt TIMESTAMP,
 creatorUserId INT64,
  FOREIGN KEY (creatorUserId) REFERENCES Users (userId) 
) PRIMARY KEY(spaceId);

CREATE TABLE SpaceMembership (
 spaceId INT64 NOT NULL,
 userId INT64,
 isAdmin BOOL,
 FOREIGN KEY (userId) REFERENCES Users (userId)
) PRIMARY KEY(spaceId, userId),
 INTERLEAVE IN PARENT Spaces ON DELETE CASCADE;

CREATE TABLE Messages (
 messageId INT64 NOT NULL,
 senderId INT64 NOT NULL,
 spaceId INT64 NOT NULL,
 repliedToMessageId INT64,
 text STRING(1024),
 createdAt TIMESTAMP,
 updatedAt TIMESTAMP,
 isDeleted BOOL,
 attachmentUrl STRING(1024),
 FOREIGN KEY (senderId) REFERENCES Users (userId)
) PRIMARY KEY(spaceId, messageId),
INTERLEAVE IN PARENT Spaces ON DELETE CASCADE;

CREATE TABLE MessageStatus (
 spaceId INT64 NOT NULL,
 messageId INT64 NOT NULL,
 receiverId INT64 NOT NULL,
 isDelivered BOOL,
 isRead BOOL,
 FOREIGN KEY (receiverId) REFERENCES Users (userId) 
) PRIMARY KEY(spaceId, messageId, receiverId),
 INTERLEAVE IN PARENT Messages ON DELETE CASCADE;

CREATE INDEX messages_spaceId ON Messages(spaceId);
CREATE INDEX messages_updatedAt ON Messages(updatedAt);