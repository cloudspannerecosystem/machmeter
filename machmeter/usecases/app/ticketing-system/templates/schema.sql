CREATE TABLE issues (
	  issue_id INT64,
	  component_id INT64,
	  reporter STRING(64),
	  create_time TIMESTAMP,
) PRIMARY KEY (issue_id);

CREATE TABLE comments (
	  issue_id INT64,
	  comment_id INT64,
	  comment_description STRING(64),
	  commenter_id INT64,
	  comment_timestamp TIMESTAMP,
	CONSTRAINT FK_Comment FOREIGN KEY(issue_id) REFERENCES issues(issue_id)
) PRIMARY KEY (issue_id, comment_id),
INTERLEAVE IN PARENT issues ON DELETE CASCADE;

CREATE TABLE history (
	  issue_id INT64,
	  change_id INT64,
	  changed_by INT64,
	  title STRING(64),
	  issue_description STRING(64),
	  assignee STRING(64),
	  issue_status STRING(64),
	  history_timestamp TIMESTAMP,
	CONSTRAINT FK_History FOREIGN KEY(issue_id) REFERENCES issues(issue_id)
) PRIMARY KEY (issue_id, change_id),
INTERLEAVE IN PARENT issues ON DELETE CASCADE;
