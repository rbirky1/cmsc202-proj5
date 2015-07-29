package proj5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * @file
 * @author Rachael Birky <rbirky1@gl.umbc.edu>
 * @version 11.27.12
 * @project CMSC 202 - Fall '12 - Project 5
 * @section Section 02
 */
import java.util.ArrayList;

/**
 * This is a driver program that simulates train management
 */
public class Project5 {

	public static void main(String[] args) {
		//Given args[0] city [1]min [2]max [3]numCars [4]cmdfile [5]outputfile
		final int personArgs = 3;
		final int cargoArgs =  5;
		try{
			
			String city="";
			String cmdFile = "";
			String outputFile = "";
			final int speed, min, max, numCars;
			
			//If one word city name
			//if ((int)args[1].charAt(0) > 48 && (int)args[1].charAt(0) < 57){
				city = args[0];
				min = Integer.parseInt(args[1]);
				max = Integer.parseInt(args[2]);
				numCars = Integer.parseInt(args[3]);
				cmdFile = args[4];
				outputFile = args[5];
			/*}
			else{
				city = args[0]+" "+args[1];
				min = Integer.parseInt(args[2]);
				max = Integer.parseInt(args[3]);
				numCars = Integer.parseInt(args[4]);
				cmdFile = args[5];
				outputFile = args[6];
			}*/
			
			//Input
			java.io.FileReader file = new java.io.FileReader(cmdFile);
			java.io.BufferedReader buf = new java.io.BufferedReader(file);
			
			//Output
			PrintWriter out = new PrintWriter(new File(outputFile));
			
			//Object
			Train train = new Train(city, min, max, numCars);
			
			//Code
			String command = buf.readLine();
			System.out.println("Program complete. Check "+outputFile+" for results.");
			
			while(command!=null){
				out.write(command+" ");
				//"MENU"
				if (command.equalsIgnoreCase("QUIT")){out.write("\nQuitting...");}
				else if (command.equalsIgnoreCase("PRINT")){
					out.write(train.getStatus()+"\n");
				}
				else if (command.equalsIgnoreCase("ADDCAR")){
					String type = buf.readLine();
					int capacity = Integer.parseInt(buf.readLine());
					out.write(train.addCar(type, capacity));
					out.write("\n");
				}
				else if (command.equalsIgnoreCase("LOAD")){
					String type = buf.readLine();
					int boxcarIndex = Integer.parseInt(buf.readLine());
					ArrayList<String> itemArgs = new ArrayList<String>();
					//Need 3 arguments for person type
					if (type.equalsIgnoreCase("PERSON")){
						for (int i=0; i<personArgs; i++){
								String param = buf.readLine();
								out.write(" "+param); 
								itemArgs.add(param);
							}
						out.write("\n");
					}
					//Need 5 arguments for a cargo type
					if (type.equalsIgnoreCase("CARGO")){
						for (int i=0; i<cargoArgs; i++){
							String param = buf.readLine();
							itemArgs.add(param);
							out.write(" "+param);
						}
						out.write("\n");
					}
					out.write(train.load(type, boxcarIndex, itemArgs));
				}
				else if (command.equalsIgnoreCase("SPEEDUP")){
					int mph = Integer.parseInt(buf.readLine());
					out.write(" "+mph);
					out.write(train.speedUp(mph));
				}
				else if (command.equalsIgnoreCase("SLOWDOWN")){
					int mph = Integer.parseInt(buf.readLine());
					out.write(" "+mph);
					out.write(train.slowDown(mph));
				}
				else if (command.equalsIgnoreCase("REMOVECAR")){
					int boxcarIndex = Integer.parseInt(buf.readLine());
					out.write(boxcarIndex+train.removeCar(boxcarIndex));
				}
				else if (command.equalsIgnoreCase("DEPART")){
					String endCity = buf.readLine();
					out.write(train.depart(endCity));
				}
				else if (command.equalsIgnoreCase("ARRIVE")){
					train.arrive();
					out.write("\n");
				}
				else if (command.equalsIgnoreCase("UNLOAD")){
					int boxcarIndex = Integer.parseInt(buf.readLine());
					String id = buf.readLine();
					out.write(train.unload(boxcarIndex, id));
				}
				else{
					out.write("\n  ERROR: Invalid command.\n");
				}
				command = buf.readLine();
			}
			
			out.close();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("ERROR: Invalid number of arguments provided.\n");
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		
	}
}
