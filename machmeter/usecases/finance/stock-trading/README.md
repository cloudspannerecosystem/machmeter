### Stock Trading Template

Implements a stock trading database with containing two main tables - `StockPrice`, and `OrderHistory`,
which will be updated and queried frequently. We also have two metadata tables - `OrderType` and `Stocks`.

The description of the four tables are as follows:

`Stocks`: Stores the Ticker and Name of the stocks in the trading exchange.

`OrderType`: Stores the type of orders - BUY/SELL along with their unique IDs, which will be used as foreign key in `OrderHistory` table.

`StockPrice`: Stores the closing price of stocks for each trading day.

`OrderHistory`: Stores all the executed orders, with their quantity, ticker and day.
