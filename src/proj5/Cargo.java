package proj5;
/**
 * @file
 * @author Rachael Birky <rbirky1@gl.umbc.edu>
 * @version 11.27.12
 * @project CMSC 202 - Fall '12 - Project 5
 * @section Section 02
 */

/**
 * This class represents cargo with an ID, weight, height, width and length
 * Class Invariant: All vars must be positive integer values
 * 	ID must be a non-null String with a length greater than 0
 */
public class Cargo implements Comparable<Cargo>, BoxcarType{
	
	private String cargoId;
	private int weight;
	private int height;
	private int width;
	private int length;
	
	/**
	 * @name Cargo
	 * @description Constructor
	 * Precondition: all vars are positive integers
	 * 	ID is a non-null String with a length greater than 0
	 * Postcondition: a new Cargo item is created
	 * @param cargoId - the cargo item's unique id number
	 * @param weight - an integer representing the weight of the cargo
	 * @param height - an integer representing the height
	 * @param width - an integer representing the width
	 * @param length - an integer representing the length
	 */
	public Cargo(String cargoId, int weight, int height, int width, int length){
		this.cargoId = cargoId;
		this.weight = weight;
		this.height = height;
		this.width = width;
		this.length = length;
	}
	
	@Override
	public String getId(){
		return this.cargoId;
	}
	
	@Override
	public int compareTo(Cargo otherCargo){
		return this.getId().compareTo(otherCargo.getId());
	}

	/**
	* @name getDimensions
	* @description A helper method that formats the dimensions of the
	* 	current item appropriately
	* Precondition: none
	* Postcondition: A string with formatted dimension information is returned
	* @return the dimensions of the object in the form height x width x length
	 */
	private String getDimensions(){
		return ""+height+"x"+width+"x"+length;
	}
	
	@Override
	public String toString(){
		return String.format("%s:   Weight: %-10s Dimensions: %s", cargoId, weight, getDimensions());
	}

}
