CREATE TABLE Stocks (
                        Ticker INT64 NOT NULL,
                        Name STRING(MAX),
) PRIMARY KEY (Ticker);

CREATE TABLE StockPrice (
                            Ticker INT64 NOT NULL, Day DATE NOT NULL,
                            ClosingPrice FLOAT64 NOT NULL,
) PRIMARY KEY (Ticker, Day),
INTERLEAVE IN PARENT Stocks ON DELETE CASCADE;

CREATE TABLE OrderType (
                           OrderTypeId INT64 NOT NULL,
                           OrderTypeName STRING(MAX),
) PRIMARY KEY (OrderTypeId);

CREATE TABLE OrderHistory (
                              OrderId STRING(MAX),
                              Ticker INT64 NOT NULL,
                              Day DATE NOT NULL,
                              OrderType INT64 NOT NULL,
                              Units INT64 NOT NULL,
                              CONSTRAINT FK_OrderType FOREIGN KEY (OrderType) REFERENCES OrderType (OrderTypeId)
) PRIMARY KEY (Ticker, Day, OrderId),
INTERLEAVE IN PARENT StockPrice ON DELETE CASCADE;

CREATE INDEX StockPriceByTickerDay ON StockPrice(Ticker, Day DESC);
CREATE INDEX OrderHistoryByTickerOrderTypeDay ON OrderHistory(Ticker, OrderType, Day DESC);
CREATE INDEX OrderSummaryByDayOrderType ON OrderHistory(Day, OrderType);
