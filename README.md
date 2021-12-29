# LED-Design-Exercise

With answer = ABA
| Key Press | LED 1 | LED 2 | LED 3 |
|---|---|---|---|
| B | off | off | orange |
| B | off | green | orange |
| C | orange | green | red |
| A | orange | red | green |
| B | red | orange | orange |

### THE GAME

The on-screen game elements consist of the following:

 - LED 1 - which can be {red, green, orange, off (dark grey)}
 - LED 2 - which can be {red, green, orange, off (dark grey)}
 - LED 3 - which can be {red, green, orange, off (dark grey)}
 - Button A
 - Button B
 - Button C
 - A TextView showing the current answer (for ease of debugging and testing, more detail below)
 - A TextView showing the current guess (more detail below)


### REQUIREMENTS

- The system implements a game in which the user has to guess a predetermined random sequence of three letters by pressing the buttons.

- The sequence can contain any combination, e.g. BAC, CCB, AAA.
- The LEDs should always represent the result of the last 3 button presses.
  - LED 3 will always represent the most recent button press
  - LED 2 the one before that
  - LED 1 the one before that

- LED color definition
  - Red indicates that the button pressed was wrong for this position, and does not appear in a different position.
  - Orange indicates that the button pressed was wrong for this position, but it DOES appear in a different position.
  - Green indicates that the button pressed was correct for this position.
  - Dark grey indicates that there is no button pressed for this position yet.

- After the right answer is found, show a dialog and then generate a new answer to start a new game.
- Include unit tests and Android tests.
