// -------------------------------------------------------
// Assignment 4
// Written by: Fung Sim Leung 40195538
// For COMP 248 Section EC – Winter 2021
// --------------------------------------------------------

/*
The purpose of this Creature class is to generate and store information about a player of the Battle Game,
as well as create multiple methods to manipulate variables,
so that the user can play the Battle Game through the driver file without direct access to the codes of the methods.
This way, we can ensure the methods and instance variables won't be modified inappropriately or unexpectedly by the player
through the driver, which can lead to errors.
*/

import java.util.Date;
import java.lang.Math;

public class Creature {

	// Declare static and instance variables
	
	private final static int FOOD2HEAT = 6;  // The normalize() method can convert 6 foodUnits into 1 healthUnits.
	private final static int HEALTH2POWER = 4;	// The normalize() method can convert 4 healthUnits into 1 firePowerUnits.
	private static int numStillAlive = 0;	// How many creatures are still alive
	private String name;					// The name of this creature
	private int foodUnits;					// how many food units this creature has
	private int healthUnits;				// how many health units this creature has
	private int firePowerUnits;				// how many fire power units this creature has
	private Date dateCreated;				// The date and time when this creature is created (when it is named by a user)
	private Date dateDied;					// The date and time when this creature is died
											// (when both of its foodUnits and healthUnits are 0 after an attack)
	
	
	// Defining a constructor
	public Creature(String name) {
		
		// Math.random() generates a double value x where 0 <= x < 1.0
		// I use (int) to cast the double into an integer,
		// so that foodUnits, healthUnits and firePowerUnits can store the values without errors.
		
		this.name = name;
		foodUnits = (int) ( (Math.random()*12)+1 ); // It must be a random number between 1 and 12 inclusive.
		healthUnits = (int) ( (Math.random()*7)+1 );  // It must be a random number between 1 and 7 inclusive.
		firePowerUnits = (int) ( (Math.random()*11) );  // It must be a random number between 0 and 10.
		
		dateCreated = new Date();	// Assign the date and time to dateCreated.
		dateDied = null;			// The creature is alive, so there is no date of death yet.
		
		numStillAlive++;			// We add one creature to the total number of creatures that are alive
		
		normalizeUnits();			// Convert every 6 foodUnits to 1 healthUnits and 4 healthUnits to 1 firePowerUnits
		
		System.out.println();
		
		System.out.println(toString()); // Show a descriptive message of the Creature's status
	}
	
	// Declare a public mutator method to set the name of the creature to the value passed.
	
	public void setName (String newName) {
		this.name = newName;
	}
	
	// Declare a public mutator method to set the number of health units.
	
	public void setHealthUnit (int n) {
		this.healthUnits = n;
	}
	
	// Declare a public mutator method to set the number of food units.
	public void setFoodUnit (int n) {
		this.foodUnits = n;
	}
		
		
	// Declare a public method called reduceFirePowerUnits() which will reduce the fire power
	// units by the amount specified by the parameter.
	public void reduceFirePowerUnits (int n) {
		firePowerUnits -= n;
	}
		
	//Public accessor methods for each non-static instance variable.	
	public String getName() {

		return name;
	}	
		
	public int getFoodUnits() {
		return foodUnits;
	}			

	public int getHealthUnits() {
		return healthUnits;
	}			

	public int getFirePowerUnits() {
		return firePowerUnits;
	}		

	public Date getDateCreated() {
		// Create a copy of dateCreated to prevent privacy leak
		// so that the only way a user using the driver can modify the value of dateCreated
		// is to call the setDateCreated() method.
		Date copyDateCreated = (Date) dateCreated.clone();
		return copyDateCreated;
	}		
		
	public Date getDateDied() {
		// Create a deep copy of dateDied to prevent privacy leak
		// so that the only way a user using the driver can modify the value of dateDied
		// is to call the setDateDied() method.
		Date copyDateDied = (Date) dateDied.clone();
		return copyDateDied;
	}			
	
	// Declare a public static accessor method to get the static
	// instance variable numStillAlive.
	
	public static int getNumStillAlive() {
		return numStillAlive;
	}
	
	//A public method isAlive() that returns true if the dateDied is null and false otherwise.
	public boolean isAlive() {
		if (dateDied == null) 
			return true;
		return false;
	}
	
	// Declare a public method earnFood() which adds a number
	// (randomly generated between 0 to 15 inclusive) of units to foodUnits.
	public int earnFood () {
		
		int foodEarned = (int) (Math.random()*16);   // Because 0 <- Math.random()*16 < 16.0 ,
													// and casting using (int) will round down the numbers, 
													// foodEarned will be between 0 to 15 inclusive.
		
		foodUnits += foodEarned;			// Increase the foodUnits of this creature by the number of foodEarned
		
		normalizeUnits();			// Convert every 6 foodUnits to 1 healthUnits and 4 healthUnits to 1 firePowerUnits
		
		return foodEarned;			// We need to display the value of foodEarned in the driver.
		
	}
	
	// A public method attacking(player): This creature is attacking the player (the passed
	// parameter). The attacking creature gets 50% of the other creature’s food units and,
	// 50% of its health units (i.e., divide by 2 and round up).
	
	public void attacking (Creature player) {
		
		// normally, when we use the operator / on an int variable, the outcome is rounded down.
		// If we add 1 to the numerator, the outcome either remains unchanged or is rounded up.
		// e.g. (13+1)/2 = 7, (14+1)/2 = 7
		
		// In the Math class, there are three methods for rounding a number.
		// Because the game requires me to round up all floating numbers, I use Math.ceiling().
		// However, this method returns a number of the type double. I need to cast the double into an integer using (int),
		// allowing variables foodEarned and healthEarned to store the values without errors.
		// Note that playerBeingAttacked.foodUnits is an integer. If I use the / operator, the outcome could be rounded down before Math.ceiling(0) rounded it up.
		// So I must ensure the Math.ceil() is rounding up a value that is a double and has not yet rounded down.
		// To do so, I divide playerBeingAttacked.foodUnits by 2.0 instead of 2,
		// to let the compiler knows that the outcome is a double and it should not round it down.

		
		int foodEarned = (int) Math.ceil(((player.foodUnits)/2.0)); 
		int healthEarned = (int) Math.ceil(((player.healthUnits)/2.0)); 
		
		this.foodUnits += foodEarned;		// Transfer the number of foodEarned from the creature being attacked to the creature that is attacking
		player.foodUnits -= foodEarned;
		this.healthUnits += healthEarned;	// Transfer the number of healthEarned from the creature being attacked to the creature that is attacking
		player.healthUnits -= healthEarned;		
		
		reduceFirePowerUnits(2);	// Each attack costs 2 fire power units from the creature who successfully completes an attack.
		
		normalizeUnits();			// Convert every 6 foodUnits to 1 healthUnits and 4 healthUnits to 1 firePowerUnits
		player.healthFoodUnitsZero();		// Call the method to check if the creature that is attacked has neither foodUnits and healthUnits.
		
	}
	
	// Declare a public method healthFoodUnitsZero() that returns true if the creature has no
	// more health and food units left (i.e. died), otherwise false. If no more health and food unit left,
	// and the dateDied is null, call died().
	
	public boolean healthFoodUnitsZero() {
		if (dateDied == null && foodUnits == 0 && healthUnits == 0)   // If the dateDied is null, this creature is newly dead. 
			{														
				Died();												// We need to call this method to assign the date of death to this creature.
				numStillAlive--;									// This creature is dead. The number of creatures alive is reduced by one.
				return true;										// Return true when the creature is dead.
			}
		else if (foodUnits == 0 && healthUnits == 0)				// If the dateDied is not null, the creature was skilled in one of the previous rounds.
			{														// So we don't need to assign a new date of death
				return true;										// and the total number of creatures alive remains unchanged.
			}														// Return true when the creature is dead.
		
			return false;											// Return false when the creature is still alive.													
	}

	// a private method died(), which sets the dateDied field to the current date.
	private void Died () {
		dateDied = new Date();
	}
	
	
	// Declare a toString method. The output format is defined in the pdf.
	public String toString() {
		
		String summary;
		
		
		summary = "Food units\tHealth units\tFire power units\tName" +
				"\n----------\t------------\t----------------\t-----" + 
				"\n" + foodUnits + "\t" + "\t" + healthUnits + "\t" + "\t" + firePowerUnits + "\t" + "\t" + "\t" + name +
				"\nDate Created: " + dateCreated +
				"\nDate Died: ";
		
		if (isAlive() == true)
			summary += "is still alive";
		else
			summary += dateDied;
		
		return summary;
		
		
	}

	
	// Declare a showStatus method. The output format is defined in the pdf.
	public String showStatus() {
		return (foodUnits + " food units, " + healthUnits + " health units, " + firePowerUnits + " fire power units");
		
	}
	
	// A private void method normalizeUnits()
	// The method normalizeUnits() will convert each 6 units of food to 1 health unit and
	// each 4 health units to 1 fire power unit.
	// According to the instructions, we should avoid having both 0 foodUnits and 0 healthUnits at the same time
	// because in this case the creature is considered dead.
	// However, we can have 1 to 5 foodUnits and 0 healthUnits, or 0 foodUnits and 4 healthUnits after normalizing the variables.
	// I use static constants FOOD2HEAT and HEALTH2POWER instead of writing the number here
	// because if later on we change the principle of conversions,
	// we can just modify the value of the constants during the declaration without going through every statement.
	
	private void normalizeUnits() {
		
		while (foodUnits >= FOOD2HEAT) {
			foodUnits -= 6;
			healthUnits++;
		}
		
		if (foodUnits == 0)    // Because foodUnits becomes 0 after conversion, healthUnits cannot be 0.
		{		
			while (healthUnits > HEALTH2POWER) { 		// The condition of this while loop is (healthUnits > 4),
				healthUnits -= 4;			// so healthUnits must be at least 5 in order to enter the loop body to be reduced by 4.
				firePowerUnits++;			// So the healthUnits will not go from 4 to 0 after conversion.
			}		
		} else {							// Because foodUnits is not 0 after conversion, healthUnits can be 0.
			while (healthUnits >= 4) { 		// The condition of this while loop is (healthUnits >= 4),
				healthUnits -= 4;			// so healthUnits must be at least 4 in order to enter the loop body to then be reduced by 4.
				firePowerUnits++;			// The healthUnits can go from 4 to 0 after conversion.
			}			
		}	
			
	
		
	}	// End of the method normalizeUnits()
	
}	// End of the Creature class

	


