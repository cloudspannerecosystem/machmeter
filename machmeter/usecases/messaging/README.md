### Messaging template
This template implements the backend for a chat like messaging application.

#### Schema
The following diagram shows the entity relationship diagram defined for this template.

[![ER diagram](https://raw.githubusercontent.com/cloudspannerecosystem/machmeter/master/machmeter/usecases/messaging/images/Schema.png)](https://raw.githubusercontent.com/cloudspannerecosystem/machmeter/master/machmeter/usecases/messaging/images/Schema.png)

All of these tables are interlinked via interleaved/foreign key relationships.

#### Perf transactions defined
1. Update the delivery status of all messages delivered to a user.


#### Queries defined
1. Find all spaces a user is subscribed to.
2. Find the number of unread messages for each space that a user is subscribed to.
3. Query all messages for a given space.