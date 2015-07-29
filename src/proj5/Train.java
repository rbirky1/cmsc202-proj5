package proj5;
import java.util.ArrayList;
import java.util.List;

/**
 * @file
 * @author Rachael Birky <rbirky1@gl.umbc.edu>
 * @version 11.27.12
 * @project CMSC 202 - Fall '12 - Project 5
 * @section Section 02
 */

/**
 * This class represents a train of boxcars.
 * Each boxcar holds on type of item -- people or cargo
 * The train has a maximum, minimum, and current speed
 * 	a maximum number of cars and a current number of cars
 * 	a city of origin and a city of destination 
 */
public class Train {

	//Replace Sys.out.println with returns to pass back up to Project5.java to print to output file
	private String origCity;
	private String endCity;
	private int maxSpeed;
	private int minSpeed;
	private int currentSpeed;
	private int maxCars;
	private int numCars;
	private boolean arrived;
	private boolean departed;
	private boolean traveling;
	private List<Boxcar> boxcars = new ArrayList<Boxcar>();
	
	/**
	 * @name Train
	 * @description Constructor
	 * @param origCity - a String representing 
	 * @param minSpeed - an integer representing the train's minimum speed
	 * @param maxSpeed - an integer representing the train's maximum speed
	 * @param maxCars - an integer representing the total number of boxcars it can carry
	 */
	public Train(String origCity, int minSpeed, int maxSpeed, int maxCars){
		this.origCity = origCity;
		this.minSpeed = minSpeed;
		this.maxSpeed = maxSpeed;
		this.maxCars = maxCars;
		numCars=0;
		departed=false;
		arrived=false;
		currentSpeed=0;
	}
	
	/**
	 * 
	* @name speedUp
	* @description Increases the train's current speed by the given amount
	* Precondition: The amount added to the current speed must not exceed the train's maximum speed
	* 		The train must be traveling
	* Postcondition: The train's speed is increased by the given amount
	* @param mph - an integer representing the speed by which to increase the train
	* @return none
	 */
	public String speedUp(int mph){
			try {
				if (!traveling)
					throw new RuntimeException("\n  ERROR: The train has not departed yet.\n");
				else if ((currentSpeed+mph) > maxSpeed){
					throw new IllegalSpeedException("\n  ERROR: Speed cannot be increased; it would exceed the current train's maximum speed.\n");}
				else {currentSpeed+=mph; return "\n";}
			} catch (RuntimeException e) {
				return (e.getMessage());
			}
		
	}
	
	/**
	 * 
	* @name slowDown
	* @description Decreases the train's current speed by the given amount
	* Precondition: The amount subtracted from the current speed must not go below the train's minimum speed
	* 		The train must be traveling
	* Postcondition: The train's speed is decreased by the given amount
	* @param mph - an integer representing the speed by which to decrease the train
	* @return none
	 */
	public String slowDown(int mph){
			try {
				if (!traveling)
					throw new RuntimeException("\n  ERROR: The train has not departed yet.\n");
				else if ((currentSpeed-mph) < minSpeed)
					throw new IllegalSpeedException("\n  ERROR: Speed cannot be decreased; it would fail to meet the current train's minimum speed.\n");
				else {currentSpeed-=mph; return "\n";}
			} catch (RuntimeException e) {
				return (e.getMessage());
			}
		
	}
	
	public String addCar(String type, int capacity){
			try {
				if (traveling)
					throw new IllegalOperationException("\n  ERROR: Cannot add car while traveling.\n");
				else if ((numCars+1) > maxCars)
					throw new IllegalOperationException("\n  ERROR: Adding another car would exceed the maximum capacity.\n");
				else {
					//add a boxcar of appropriate type
					if (type.equalsIgnoreCase("PERSON"))
						{boxcars.add(new Boxcar<Person>(type, capacity)); numCars++; return type+" "+capacity;}
					else if (type.equalsIgnoreCase("CARGO"))
						{boxcars.add(new Boxcar<Cargo>(type, capacity)); numCars++; return type+" "+capacity;}
					else throw new IllegalArgumentException("\n  ERROR: Incorrect type given.");
				}
			} catch (IllegalOperationException e) {
				return (e.getMessage());
			} catch (IllegalArgumentException e){
				return (e.getMessage());
			}
		
	}
	
	public String removeCar(int boxcarIndex){
		try{
			if (boxcarIndex >= boxcars.size())
				//cannot remove if non existent. boxcar nums dynamically change according to size & to start at 0
				throw new IndexOutOfBoundsException("\n  ERROR: Boxcar with given position does not exist.\n");
			else if (traveling)
				throw new IllegalOperationException("\n  ERROR: The train has not arrived in "+endCity+" yet.\n");
			else if (!boxcars.get(boxcarIndex).isEmpty())
				throw new IllegalOperationException("\n  ERROR: Specified boxcar is not empty.\n");
			else {boxcars.remove(boxcarIndex); numCars--; return "\n";}
		} catch (RuntimeException e){
			return (e.getMessage());
		}
	}
	
	/**
	* @name arrive 
	* @description The train arrives at it's destination 
	* Precondition: none
	* Postcondition: The train's destination city is now it's original city/location 
	* @return none
	 */
	public void arrive(){
		arrived = true;
		departed = false;
		traveling = departed && (!arrived);
		origCity = endCity;
		currentSpeed = 0;
	}
	
	/**
	* @name departs 
	* @description The train departs from it's current location 
	* Precondition: The destination must be different from the current location
	* Postcondition: The train will begin traveling
	* @return none
	 */
	public String depart(String endCity){
		if (endCity.equalsIgnoreCase(origCity))
			return ("\n  ERROR: Train is already in "+endCity+"\n");
		else if (traveling){
			return ("\n  ERROR: Train has already departed.");
		}
		else {
			this.endCity = endCity;
			arrived = false;
			departed = true;
			traveling = departed && (!arrived);
			currentSpeed=10;
			return endCity+"\n";
		}
	}
	
	/**
	 * 
	* @name getPosition 
	* @description Returns a String describing the train's current location
	* Precondition: none
	* Postcondition: A String with the current location is returned
	* @return a String of "stopped in [original/destination city], traveling from [original city] to [destination]
	 */
	public String getPosition(){
		if (arrived) return "Stopped in "+endCity;
		else if (departed )return "Traveling from "+origCity+" to "+endCity;
		else return "Stopped in "+origCity;
	}
	
	/**
	* @name getStatus 
	* @description Returns a String describing the status of the train
	* Precondition: none
	* Postcondition: A description of the train's properties and contents is returned
	* @return Formatted text describing the curent train's minimum, maximum and current speeds,
	* 		current position, maximum and current number of boxcars, and the contents of each boxcar
	 */
	public String getStatus(){
		String boxCarDesc = "";
		for (int i=0; i<boxcars.size(); i++){
			boxCarDesc+="\n  Boxcar: "+i+"\n  ----------\n  Contents:"+boxcars.get(i).getStatus();
		}
		
		return ("\nTrain status\n----------"
		+"\n  Current Speed: "+currentSpeed
		+"\n  Minimum Speed: "+minSpeed
		+"\n  Maximum Speed: "+maxSpeed
		+"\n  Current Position: "+getPosition()
		+"\n  Current Number of Boxcars: "+numCars
		+"\n  Maximum Number of Boxcars: "+maxCars
		+boxCarDesc+"\n");
		
	}
	
	/**
	* @name unload 
	* @description Unloads people or cargo from the specified boxcar 
	* Precondition: The item must exist
	* 		The train must be stopped
	* 		The boxcar must exist
	* Postcondition: The item is removed from the boxcar
	* @param boxcarIndex - the number of the boxcar from which to remove the item
	* @param id - the unique of of the item to be removed
	* @return a String Error or newline for formatting purposes
	 */
	public String unload(int boxcarIndex, String id){
		try{
			if (traveling)
				throw new IllegalOperationException("\n  ERROR: Cannot unload car while traveling.\n");
			return boxcarIndex+" "+id+boxcars.get(boxcarIndex).unload(id);
		} catch (IndexOutOfBoundsException e){
			return ("\n  ERROR: Boxcar with given position does not exist.\n");
		}
	}
	
	/**
	* @name load 
	* @description Loads people or cargo into the specified boxcar 
	* Precondition: The item must be the same type as the other items in the boxcar
	* 		The train must be stopped
	* 		The boxcar must exist
	* Postcondition: The item is added to the boxcar
	* @param type - the type of the object being added (to be compared to the type of specified in the desired boxcar)
	* @param boxcarIndex - the number of the boxcar from which to remove the item
	* @param itemArgs - an array with all the information needed to create a new object of the specified type and add it to the boxcar 
	* @return a String Error or newline for formatting purposes
	 */
	public String load(String type, int boxcarIndex, ArrayList<String> itemArgs){
		try{
			if (traveling)
				throw new IllegalOperationException("\n  ERROR: Cannot load car while traveling.\n");
			else if (!type.equalsIgnoreCase(boxcars.get(boxcarIndex).getType()))
				throw new IllegalOperationException("\n  ERROR: Cannot put this item in specified boxcar -- invalid type.\n");
			else{
				//add appropriate type of thing
				if (type.equalsIgnoreCase("PERSON"))
				{
					String id = itemArgs.get(0); String name = itemArgs.get(1); int age = Integer.parseInt(itemArgs.get(2));
					return boxcars.get(boxcarIndex).load(new Person(id, name, age));
				}
				else if (type.equalsIgnoreCase("CARGO"))
				{
					String id = itemArgs.get(0);
					int weight = Integer.parseInt(itemArgs.get(1));
					int height = Integer.parseInt(itemArgs.get(2));
					int width = Integer.parseInt(itemArgs.get(3));
					int length = Integer.parseInt(itemArgs.get(4));
					return boxcars.get(boxcarIndex).load(new Cargo(id, weight, height, width, length));}
				else throw new IllegalArgumentException("\n  ERROR: Incorrect type given.\n");
			}
		} catch (IllegalArgumentException e){
			return e.getMessage();
		} catch (IllegalOperationException e){
			return e.getMessage();
		} catch (IndexOutOfBoundsException e){
			return "\n  ERROR: Boxcar with given position does not exist.\n";
		}
	}
	
	//TESTING
	public static void main(String[] args) {
		//city, minspeed, maxspeed, maxcars
		Train testTrain = new Train("Boonsboro", 5, 60, 2);
		//type, capacity
		testTrain.addCar("Cargo",1);
		//testTrain.load("Cargo",0); //how to add with variable number of arguments
								//put into arraylist<string> then know that person needs size = 3 & cargo = 5?
		testTrain.depart("Chicago");
		//testTrain.speedUp(65);
		//testTrain.removeCar(0);
		testTrain.getStatus();
	}

}
