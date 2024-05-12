## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).


## Database related
# Project Stats-In_Grass

## Database - we need to store which data?

1. Details about each match including date, teams, and outcome
2. Player statistics per match
3. Team information including players
4. Historical data access for match reviews

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