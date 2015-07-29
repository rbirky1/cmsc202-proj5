package proj5;
/**
 * @file
 * @author Rachael Birky <rbirky1@gl.umbc.edu>
 * @version 11.27.12
 * @project CMSC 202 - Fall '12 - Project 5
 * @section Section 02
 */

/**
 * This class represents a person with a unique government-issued String ID,
 *  a name and age
 * Class Invariant: all vars should be non-null Strings of length greater than 0
 *  age should be a positive integer
 */
public class Person implements Comparable<Person>, BoxcarType{

	private String govId;
	private String name;
	private int age;
	
	/**
	 * @name Person
	 * @description Constructor
	 * @param govId - a String representing the person's unique gov't-issued ID
	 * @param name - a String representing the person's name
	 * @param age - an integer representing the person's age
	 */
	public Person(String govId, String name, int age){
		this.govId = govId;
		this.name = name;
		this.age = age;
	}
	
	@Override
	public String getId(){
		return this.govId;
	}
	
	@Override
	public int compareTo(Person otherPerson){
		return this.getId().compareTo(otherPerson.getId());
	}
	
	@Override
	public String toString(){
		return String.format("%s:   Name: %-15s Age: %d", govId, name, age);
	}

}
