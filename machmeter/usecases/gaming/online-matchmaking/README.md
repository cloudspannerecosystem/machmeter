### Online Gaming Matchmaking-Leaderboard Template

A gaming application where players are matched to 2-player games. A leaderboard shows the top 10 recent high scores. The matchmaking logic involves picking two players not playing a game. The winner then gets some points and affects the leaderboard.

The schema includes tables `games`, `players`, `scores` and relevant constraints and indexes.

The data load step inserts players that can be picked up by the matchmaking logic in the subsequent load test.

The load test step models three scenarios -
1. Create a new game
2. Finish a running game
3. Show leaderboard
