![Forks](https://img.shields.io/badge/forks-44-blue)
![Stars](https://img.shields.io/badge/stars-13-yellow)
# Stats-In-Grass
##Creators
Husnu Arkca
Yusufbek Karamatov
Yunus Emre
Mustafa Akseli

## Description
Welcome to our CS-102 group project! This is Stats-In-Grass desktop app made on Java, offers a comprehensive suite of tools designed to assist sports leagues and organizers in effectively managing sports matches, teams, and player statistics. Our system enables users to seamlessly create matches, add players to teams, and keep track of detailed statistics during games, such as player performance and team outcomes.

With intuitive interfaces for match setup, player additions, and real-time game interactions, this application simplifies the complexities of match management. Whether you're tracking a local tournament or managing a sports league, our system is equipped to provide robust support for all your management needs. 
We integrated JavaFX and MySql in it. Our DB system is dao(DAO, or Data Access Object) 


## Contributing
We welcome any and all contributions! Here are some ways you can get started:
1. Report bugs: If you encounter any bugs, please let us know. Open up an issue and let us know the problem.
2. Contribute code: If you are a developer and want to contribute, follow the instructions below to get started!
3. Suggestions: If you don't want to code but have some awesome ideas, open up an issue explaining some updates or imporvements you would like to see!
4. Documentation: If you see the need for some additional documentation, feel free to add some!

## Instructions
1. Fork this repository
2. Clone the forked repository
3. Add your contributions (code or documentation)
4. Commit and push
5. Wait for eternity your pull request to be merged, because we will never return to this project :)


# Database related
## Design of tables

1. **Match**: Attributes include match ID, date, location, team information, and match statistics
2. **Team**: Attributes include team ID, name, and possibly team logo.
3. **Player**: Attributes include player ID, name, position, and team ID.
4. **PlayerStats**: Attributes include stats ID, player ID, match ID, goals, assists, minutes played, and cards received.

## Detailed Design of tables

**Tables:**

1. **Matches**
    - MatchID (Primary Key)
    - Date
    - Team1ID (Foreign Key)
    - Team2ID (Foreign Key)
    - Scores
    - Venue
2. **Teams**
    - TeamID (Primary Key)
    - Name
    - LogoPath
3. **Players**
    - PlayerID (Primary Key)
    - TeamID (Foreign Key)
    - Name
    - Position
4. **PlayerStats**
    - StatsID (Primary Key)
    - PlayerID (Foreign Key)
    - MatchID (Foreign Key)
    - Goals
    - Assists
    - MinutesPlayed
    - YellowCards
    - RedCards
