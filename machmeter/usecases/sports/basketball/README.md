### Financial Ledger Template

Implements a financial ledger database with containing two tables - `user_bal` and `user_txn`. 
The transactions table stores the list of transactions performed by the user (debit/credit) and the balance table
keeps track of the total user balance in the account.

The `user_txn` table is interleaved inside the `user_bal` table. This template demonstrates loading data into 
interleaved tables via JMeter. The database is sampled and a CSV data set is created for the performance test
template to use in its run.

For performance testing, we showcase a variety of different access patterns such as write mutations, read queries,
readWriteTransactions etc.

