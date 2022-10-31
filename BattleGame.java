// -------------------------------------------------------
// Assignment 4
// Written by: Fung Sim Leung 40195538
// For COMP 248 Section EC – Winter 2021
// --------------------------------------------------------


/*
This is the driver of the Battle game and will use many methods that are defined in the Creature class.
The user(s) can create 2 to 8 creatures to start the game.
And then each creature can check how many creatures are still alive, change their name,
check the status of themselves or all creatures,
increase the number of food units (and eventually fire power units) through working, or attack other creatures to kill them.
When only one creature is alive and the rest are all dead, the game is over.
The game over message and the status of all creatures will be displayed.
*/

import java.util.Scanner;
import java.lang.Math;
public class BattleGame {
	public static void main (String[] args) {
		
		Scanner keyboard = new Scanner(System.in);		// Define a Scanner object as we will ask the user to input some values.
		int nbCreatures = 0;    // Total number of creatures (regardless of whether they are alive or dead)
		
		// Display welcome messages
		
		System.out.println("[------------------------------------------------]");
		System.out.println();
		System.out.println("[ Welcome to the Battle Game ]");
		System.out.println();
		System.out.println("[------------------------------------------------]");
		
		// Prompt the user for how many creatures they want to have
		// I use a do-while loop to validate the input (number of creatures).
		// The number must be between 2 to 8 inclusive.
		
		do {
			System.out.println();
			System.out.print("How many creatures would you like to have (minimum 2, maximum 8)? ");
			nbCreatures = keyboard.nextInt();
			
			if ( nbCreatures < 2 || nbCreatures > 8 )	// when the input is not between 2 to 8 inclusive
			{
				System.out.println();
				System.out.println("*** Illegal number of creatures requested ***");
			}
		} while ( nbCreatures < 2 || nbCreatures > 8 );
		

		
		// Ask the user to name each creature. Names can consist of more than one word and space is included,
		// so I need to use nextLine() method instead of next().
			
		
		Creature[] player = new Creature[nbCreatures];
		
		keyboard.nextLine(); // To skip the previous new line.	
		
		// I use a for loop to ensure the program creates the exact number of creatures as decided by the user.		
		for (int i = 0; i < nbCreatures ; i++ ) {		
			System.out.println();
			System.out.print("What is the name of creature " + (i+1) + "? ");
			player[i] = new Creature(keyboard.nextLine());
			
		}
		
		
		// Start the game
		
		// As the players will play in a circle, we need to define the starting point (first) and ending point (last) of the loop.
		
		int first = 1;
		int last = nbCreatures;
		int position = (int) ((Math.random()*nbCreatures) + 1); // A variable to indicate who is the current player
																// I use Math.random() to randomly decide which creature should start first.
	

		
		int choice = 0;  // This variable records which option the current play wants to choose from the menu.
		
		
		
		while (Creature.getNumStillAlive() > 1) {  // This loop will end if only one creature is alive

			// To ensure the user chooses a valid option from the menu.
			do {				
				System.out.println();
				System.out.println("Creature #" + position + ": " + player[position-1].getName() + ", what do you want to do?");
				System.out.println();											// Most of the screenshots have an empty line before showing the first option.
				System.out.println("1.How many creatures are alive?");   		// in the screenshots there is a missing space after "1."
				System.out.println("2. See my status");
				System.out.println("3. See status of all players");
				System.out.println("4. Change my name");
				System.out.println("5. Work for some food");
				System.out.println("6. Attack another creature (Warning! may turn against you)");
				System.out.print("Your Choice please > ");
				choice = keyboard.nextInt();
			} while (choice < 1 || choice > 6);
			 	
			// I use a nested-if for all the 6 options
			// because the options 5 and 6 consist of multiple statements,
			// which is difficult to read and more prone to errors if I use a switch statement.
			
			if (choice == 1) {			// Option 1: Display the number of creatures that are alive
				System.out.println("Number of creatures alive " + Creature.getNumStillAlive() );
				continue;				 // Allow the current player to select another option from the menu again immediately
										//	without the compiler executes/goes through the entire loop
			}
			else if (choice == 2) {		// Option 2: Display the status (full description) of the current player
				System.out.println(player[position-1]);
				continue;
			}
			else if (choice == 3) {		// Option 3: Display the status (full description) of the all players
				for (int i = 0 ; i < nbCreatures ; i++) {
					System.out.println(player[i]);					
				}	
				continue;
			}
			else if (choice == 4) {			// Option 4: Display the current name of the current player's creature
											// and allow them to change the name
				
				keyboard.nextLine();  		// To discard the new line from the previous input
				
				System.out.println("Your name is currently \"" + player[position-1].getName() + "\"");
				System.out.print("What is the new name: ");
				player[position-1].setName(keyboard.nextLine());
				continue;	
			}
			else if (choice == 5) {			// Option 5: Let the current player to earn food
				System.out.println();
				System.out.println("Your status before working for food: " + player[position-1].showStatus() +
									"... You earned " + player[position-1].earnFood() + " food units.");
				System.out.println();
				System.out.println("Your status after working for food: " + player[position-1].showStatus());
			}
			else if (choice == 6) {			// Option 6: Let the current player to choose an opponent to attack
				
				// Use a do while loop to ensure the user chooses a valid creature to attack.
				
				boolean validOpponent = true;  		// A condition for the do-while loop.
				int opponent = 0;
				
				//	I use a do-while loop here because we have to ask the user to input first, before evaluating whether the input is valid.
				do {
					
					validOpponent = true;
					
					System.out.print("Who do you want to attack? (enter a number from " + first +" to "
									+ last + " other than yourself(" + position + "): ");
					opponent = keyboard.nextInt();
				

					// Show error messages for invalid input (3 situations)
				
					if ( opponent < first || opponent > last ) {		// when the input is out of range (not within 1 to nbCreatures)
						System.out.println("That creature does not exist. Try again ...");
						validOpponent = false;
					}
					else if (player[opponent-1].isAlive() == false) {			// when the selected creature is dead 
						System.out.println("That creature is no longer alive. Try again ...");
						validOpponent = false;
					}						
					else if ( opponent == position ) {				// when the current player selects themselves
						System.out.println("Can't attack yourself silly! Try again ...");
						validOpponent = false;
					}

				} while (!validOpponent);		// Keep prompting the user to choose a creature until the input is valid.
				
				// The section below determines whether the current player attacks the opponent,
				// is attacked by the opponent or nothing happens.
				// There are 4 different scenarios.
				// Scenario 1: if the current player has fewer than 2 fire power units, but the opponent has at least two fire power units,
				// the opponent will attack back the current player. Before the attack,
				// the program hows a warning message about insufficient fire power from the current player.
				// Scenario 2: if the current player has fewer than 2 fire power units, AND the opponent also has
				// fewer than 2 fire power units, none of the party is able to attack the other one.				
				// Scenario 3: if the current player has fewer than 2 fire power units OR the random number generator
				// decides that the current play cannot attack the opponent (which has 33.33% chance), but the opponent has
				// fewer than 2 fire power units, none of the party is able to attack the other one.
				// Scenario 4: if the current player has at least 2 fire power units, AND the random number generator decides that
				// the current player can attack, the current player will attack the opponent.
				// Scenario 5: if the current player has at least 2 fire power units, BUT the random number generator decides that
				// the current player CANNOT attack, the opponent will attack back the current player.
				// Scenario 1 displays a warning message.
				// Scenario 2 displays both a warning message and a lucky message.
				// Scenario 3 displays a lucky message
				
				
				// The random number generator chance decides that the current player CANNOT attack if chance == 0;
				// the current player can attack if chance == 1 or 2.		
				// Because 0.0 <= Math.random()*3 < 3.0 , when we cast the double value into an integer, the outcome will be either 0, 1, or 2.
				int chance = (int) (Math.random()*3);   
				
				if (player[position-1].getFirePowerUnits() < 2 && player[opponent-1].getFirePowerUnits() >= 2) {  // Scenario 1
					
					// Display a warning message.
					System.out.println("That was not a good idea ... you only had "
								+ player[position-1].getFirePowerUnits() + " Fire Power units!!!");

					System.out.println("....... Oh No!!! You are being attacked by " + player[opponent-1].getName() + "!");
					System.out.println("Your status before being attacked: " + player[position-1].showStatus());
					player[opponent-1].attacking(player[position-1]);
					System.out.println("Your status after being attacked: " + player[position-1].showStatus());
					
					if (player[position-1].isAlive() == false) 				// Check if the current player is dead
					{
						System.out.println();
						System.out.println("You are dead");
					}
					
				} else if ( (player[position-1].getFirePowerUnits() < 2) && player[opponent-1].getFirePowerUnits() < 2) { // Scenario 2

					// Display a warning message.
					System.out.println("That was not a good idea ... you only had "
								+ player[position-1].getFirePowerUnits() + " Fire Power units!!!");
	
					// Display a message saying the current player is lucky. No one attacks or is attacked.
					System.out.println("Lucky you, the odds were that the other player attacks you, but "
								+ player[opponent-1].getName() + " doesn't have enough fire power to "
								+ "attack you! So is status quo!!");
				
				} else if ( (chance == 0) && player[opponent-1].getFirePowerUnits() < 2) { // Scenario 3

					// Display a message saying the current player is lucky. No one attacks or is attacked.
					System.out.println("Lucky you, the odds were that the other player attacks you, but "
								+ player[opponent-1].getName() + " doesn't have enough fire power to "
								+ "attack you! So is status quo!!");
			
				} else if (player[position-1].getFirePowerUnits() >= 2 && chance != 0) { // Scenario 4

					System.out.println();
					System.out.println("..... You are attacking " + player[opponent-1].getName() + "!");
					System.out.println("Your status before attacking: " + player[position-1].showStatus());
					player[position-1].attacking(player[opponent-1]);
					System.out.println("Your status after attacking: " + player[position-1].showStatus());
								
					if (player[opponent-1].isAlive() == false) 				// Check if the opponent is dead
					{
						System.out.println();
						System.out.println(player[opponent-1].getName() + " is dead");
					}


				} else if (player[position-1].getFirePowerUnits() >= 2 && player[opponent-1].getFirePowerUnits() >= 2 && chance == 0 ) { // Scenario 5
					System.out.println("....... Oh No!!! You are being attacked by " + player[opponent-1].getName() + "!");
					System.out.println("Your status before being attacked: " + player[position-1].showStatus());
					player[opponent-1].attacking(player[position-1]);
					System.out.println("Your status after being attacked: " + player[position-1].showStatus());
					
					if (player[position-1].isAlive() == false)				// Check if the current player is dead
					{
						System.out.println();
						System.out.println("You are dead");
					}
				} 	
			} 	// End of the outermost nested-if statement (the execution of 6 options). 
		
	
			
			// To determine if the game is over (i.e. no other creature is alive.)
			// If I remove this if statement the outermost while-do loop can still end when there is only one creature alive
			// because (Creature.getNumStillAlive() > 1) is false.
			// However I personally think the break; here can ensure there is no infinite loop and is easy for testing and debugging.
			// So I leave it here.
			
			if (Creature.getNumStillAlive() == 1)
			{
				break;
			}
			
			
			// Move on to the next creature that is still alive
			
			do {
				// To ensure the position is always between 1 to nbCreatures (inclusively).
				
				if (position == last) {     // The position will go back to 1 if it is originally the last position to prevent the index of the array goes beyond the range.
					position = first;		
				}
				else
				{
					position++;				// The position will be increased by 1 if it is not the last position.
				}
				
				// To check if the updated creature is alive. If it is dead, the loop will repeat
				// until reaching the next player that is alive.

			} while (player[position-1].isAlive() == false);
		
		} // End of the outermost do-while loop. Display the menu again for the current player
			// or the next player (if the previous action is working or attacking) or end the game (if there is only one creature tht is alive and the rest is dead).
		
			
		// GAME OVER messages		
		System.out.println();			
		System.out.println("GAME OVER!!!!!");		
		System.out.println();
		
		// Show the status (full description) of all creatures
		for (int i = 0 ; i < nbCreatures ; i++) {
			System.out.println(player[i]);
		}	
		
		keyboard.close();	// Close the Scanner object
	}	// End of the main method
}	// End of the driver
