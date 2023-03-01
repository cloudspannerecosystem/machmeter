### Shopping Cart Template

Implements a shopping cart database with containing three tables - `Item`, `ShoppingCart` and `ShoppingCartItem`.
The `Item` table models a list of available items to buy. The `ShoppingCart` table models a shopping cart created
for the user.
The `ShoppingCartItem` table stores the mapping of `ItemIds` corresponding to a `ShoppingCartID`. Since a shopping cart
can contain many items in it, the `ShoppingCartItem` table is interleaved in `ShoppingCart`.

This template demonstrates loading data into interleaved and non-interleaved tables via JMeter. We create the input
CSV for performance testing at the time of the performance run. 

For performance testing, it has example of choosing random query from a pool of queries for performance testing.


