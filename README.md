# Charlene's Coffee Corner

This is a solution to Swiss Re's work assignment.

## Commands
 - `./gradlew build`: Builds the project
 - `./gradlew test`: Runs the tests
 - `./gradlew -q --console plain run`: Runs the application. Alternatively run the `run.sh` script.
 
## Requirements
 - Java 17+

## Assumptions
 - Each extra can be added at most once
 - There is no need for multi-currency support as all prices are CHF
 - There is no need for multi-language support as all product names are in English
 - When ordering a beverage and a snack, the extra that is free is the most expensive one
 - When ordering multiple snacks and multiple beverages, the number of extras that are free is at most the number of snacks purchased.
 - When ordering multiple snacks and multiple beverages, at most one extra per beverage is free. For example, buying two snacks and a beverage with two extras, only one of the extras will be free.
 - Each item can only have at most one bonus applied. For example, if a beverage is free because another 4 have been purchased then no extras can be removed from it because of buying snacks.
 - Bonuses are prioritised so that the highest value ones are applied first.
 - In the future, the free extra might be on a snack, not necessarily a beverage. The bonus always applies on the most expensive extra on any item that hasn't had a bonus applied to it yet.
 - When ordering five or more beverages, the beverage that is free is always the fifth one. When ordering ten, the ones that are free are the fifth and the tenth. And so on.
