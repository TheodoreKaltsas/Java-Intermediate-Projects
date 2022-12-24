import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class MedievalGame {

  /* Instance Variables */
  private Player player;

  /* Main Method */
  public static void main(String[] args) {
    Scanner console = new Scanner(System.in);
    MedievalGame game = new MedievalGame();
    //game.player = new Player("Test");

    game.player = game.start(console);

    game.addDelay(500);
    System.out.println(
      "\nLet's take a quick look at you to make sure you're ready to head out the door."
    );
    System.out.println(game.player);

    game.addDelay(1000);
    System.out.println(
      "\nWell, you're off to a good start, let's get your game saved so we don't lose it."
    );
    game.save();

    game.addDelay(2000);
    System.out.println("We just saved your game...");
    System.out.println(
      "Now we are going to try to load your character to make sure the save worked..."
    );

    game.addDelay(1000);
    System.out.println("Deleting character...");
    String charName = game.player.getName();
    game.player = null;

    game.addDelay(1500);
    game.player = game.load(charName, console);
    System.out.println("Loading character...");

    game.addDelay(2000);
    System.out.println(
      "Now let's print out your character again to make sure everything loaded:"
    );

    game.addDelay(500);
    System.out.println(game.player);
  } // End of main

  /* Instance Methods */
  private Player start(Scanner console) {
    // Add start functionality here
    Player player;
    Art.homeScreen();

    System.out.print(
      "Welcome adventurer to the game! Would you like to start a new game or load a saved game? Please enter y for yes or n for no "
    );
    //String answer = console.nextLine().toLowerCase();
    char answer = console.next().charAt(0);

    if (answer == 'y') {
      System.out.print("Please enter the previous character name: ");
      String name = console.nextLine();

      player = load(name, console);
      return player;
    } else if (answer == 'n') {
      System.out.print("Please enter a new name for the character: ");
      String name = console.nextLine();

      player = new Player(name);
      return player;
    } else {
      System.out.println("Please enter either y or n");
    }

    return new Player("John");
  } // End of start

  private void save() {
    // Add save functionality here
    String fileName = player.getName() + ".svr";
    try {
      FileOutputStream userSaveFile = new FileOutputStream(fileName);
      ObjectOutputStream playerSaver = new ObjectOutputStream(userSaveFile);
      playerSaver.writeObject(player);
    } catch (IOException e) {
      e.printStackTrace();
    }
  } // End of save

  private Player load(String playerName, Scanner console) {
    // Add load functionality here
    Player loadedPlayer = new Player(playerName);

    try {
      FileInputStream findPlayer = new FileInputStream(playerName + ".svr");
      ObjectInputStream playerFinder = new ObjectInputStream(findPlayer);
      loadedPlayer = (Player) playerFinder.readObject();
    } catch (IOException e) {
      addDelay(1500);
      System.out.println(
        "\nThere is a problem loading your save file, system has created a new file with the name you have entered."
      );
      System.out.println(
        "If the save file name is correct, the save file is no longer available, please reload the save file if you like to try again."
      );
      System.out.println(
        "As of now a new character is created with the name: " + playerName
      );
      addDelay(2000);
      loadedPlayer = new Player(playerName);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    return loadedPlayer;
  } // End of load

  // Adds a delay to the console so it seems like the computer is "thinking"
  // or "responding" like a human, not instantly like a computer.
  private void addDelay(int time) {
    try {
      Thread.sleep(time);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
