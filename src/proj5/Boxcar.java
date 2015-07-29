package proj5;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @file
 * @author Rachael Birky <rbirky1@gl.umbc.edu>
 * @version 11.27.12
 * @project CMSC 202 - Fall '12 - Project 5
 * @section Section 02
 */

/**
 * This is a container/generic class that hold either Cargo or Person classes
 */
public class Boxcar<T extends BoxcarType & Comparable<T>> {

	private List<T> things;
	private int capacity;
	private int numThings;
	private String type;
	
	/**
	 * @name Boxcar
	 * @description A container/generic class that holds objects of BoxcarType in an ordered array
	 * @param type - the type the current boxcar holds
	 * @param capacity - the number of items the boxcar can hold
	 */
	public Boxcar(String type, int capacity){
		this.things = new ArrayList<T>(capacity);
		this.capacity = capacity;
		this.numThings = 0;
		this.type = type;
	}
	
	/**
	 * 
	* @name load 
	* @description Adds the parameter object to the boxcar 
	* Precondition: The item must have a unique ID
	* 		adding the item must not exceed the capacity of the current boxcar
	* Postcondition: The object passed is added to the boxcar
	* @param newThing - an object of BoxcarType
	* @return none
	 */
	public String load (T newThing){
			try {
				if (duplicateItem(newThing)){ //more specific case
					throw new DuplicateItemException("  ERROR: Invalid item, item with id "+newThing.getId()+" already exists.\n");}
				else if ((numThings+1) > capacity){
					throw new RuntimeException("  ERROR: Adding another item would exceed the maximum capacity.\n");}
				else {things.add(newThing); numThings++; return "";}
			} catch (RuntimeException e) {
				return e.getMessage();
			}
	}

	/**
	 * 
	* @name getStatus 
	* @description returns the items in the current boxcar, ordered by ID, in a formatted String
	* Precondition: none
	* Postcondition: none
	* @return sortedBoxcar -  a formatted String that lists the boxcar's contents in order by ID
	 */
	public String getStatus(){
		if(numThings==0)
			return "\n    This boxcar is empty";
		else
		{
			//Sort using <Type> compareTo()
			Collections.sort(things);
			
			//Compile into a string
			String sortedBoxcar = "";
			for(BoxcarType thing : things){
				sortedBoxcar += "\n    "+thing.toString();
			}
			//return formatted string
			return sortedBoxcar;
		}
	}
	
	/**
	 * 
	* @name unload 
	* @description Removes the specified item from the boxcar
	* Precondition: The item must exist in the boxcar
	* Postcondition: The item is removed from the boxcar
	* @param id - the unique ID of the item
	* @return an Error string, or a newline charater for formatting purposes
	 */
	public String unload(String id){
			boolean found = false;
			for (int i=0; i<things.size(); i++){
				if (things.get(i).getId().equals(id)){
					found = true;
					things.remove(i);
					numThings--;
					}
			}
			if (found) return "\n";
			else return ("\n  ERROR: Item does not exist.\n");
	}
	
	/**
	 * 
	* @name getType 
	* @description Returns a String representing the type of object the current boxcar holds
	* Precondition: none
	* Postcondition: the type is returned as a String
	* @return type - a String representing the type of object the current boxcar holds
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * 
	* @name isEmpty 
	* @description Determines if the boxcar is empty
	* Precondition: none
	* Postcondition: Whether or not the boxcaar is empty is returned
	* @return true - the boxcar is empty
	* 			false - the boxcar contains items
	 */
	public boolean isEmpty(){
		return (things.size()==0);
	}
	
	/**
	 * 
	* @name duplicateItem 
	* @description a helper method that compares a new item with
	*  the items currently in the boxcar to make sure it is not a duplicate
	* Precondition: newThing has an ID
	* Postcondition: Whether or not the item is a duplicate is returned
	* @return true - the item is a duplicate
	* 		false - the item is unique
	 */
	private boolean duplicateItem(T newThing){
		boolean duplicate=false;
		for (T thing : things){
			if (thing.compareTo(newThing)==0) duplicate = true;
		}
		return duplicate;
	}
	
	//TESTING
	public static void main(String[] args) {
		//Testing a boxcar of cargo
		Boxcar<Cargo> testBoxcar = new Boxcar<Cargo>("cargo", 2);
		//Testing adding cargo 
		//testBoxcar.addThing(new Cargo("2", 1300, 12, 12, 12));
		//testBoxcar.addThing(new Cargo("2", 1300, 12, 12, 12));
		//testBoxcar.addThing(new Cargo("1", 1200, 12, 12, 12));
		//Testing sorting and printing cargo
		testBoxcar.getStatus();
		
		//Testing a boxcar of people
		Boxcar<Person> testBoxcar2 = new Boxcar<Person>("person", 3);
		//Testing adding people
		testBoxcar2.load(new Person("123-12-4567", "Spongebob", 7));
		testBoxcar2.load(new Person("123-12-1234", "John Doe", 34));
		//testBoxcar2.addThing(new Person("123-12-4567", "Spongebob", 7));
		//testBoxcar2.addThing(new Cargo("1", 1200, 12, 12, 12));
		//Testing sorting and printing people
		testBoxcar2.getStatus();
		//testBoxcar2.unload(0);

	}

}
