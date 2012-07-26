//Program computes total energy of a configuration and the potential of each charge,
//given a configuration of idealized point charges

import java.io.*;
import java.lang.String;

//Point charge of negligible mass
class Charge {
	//Coordinate of the charge
	private float x;
	private float y;
	private float z;
	
	private float chargeValue; //charge in coulombs
	
	private float coulombConstant = 1; //set k = 1 instead of 9E9
	
	//Constructor
	public Charge() {
		chargeValue = 0;
		x = 0;
		y = 0;
		z = 0;
	}
	
	//Get coordinates
	public float[] getCoords() {
		float[] coords = new float[]{x,y,z};
		return coords;
	}
	
	//Get charge
	public float getChargeValue() {
		return chargeValue;
	}
	
	//Set coordinates
	public void setCoords(float[] newCoords) {
		x = newCoords[0];
		y = newCoords[1];
		z = newCoords[2];
	}
	
	//Set charge
	public void setCharge(float newCharge) {
		chargeValue = newCharge;
	}
	
	//Compute distance to a point
	public double distToPoint(Charge otherCharge) {
		float[] coords = getCoords();
		float[] otherCoords = otherCharge.getCoords();
		
		double dist = 0;
		
		for (int i = 0; i < 3; i++) {
			dist += Math.pow((coords[i] - otherCoords[i]), 2);
		}
		
		dist = Math.sqrt(dist);
		
		return dist;
	}
	
	//Computes potential of charge based on one other source
	public double potentialFromSource (Charge sourceCharge) {
		double potential = coulombConstant * sourceCharge.getChargeValue() / distToPoint(sourceCharge);
		return potential;
	}
	
	//Computes energy of charge pair (with one other charge)
	public double energyWithOther(Charge otherCharge) {
		double energy = chargeValue * potentialFromSource(otherCharge);
		return energy;
	}
	
}

public class CoulombicCharges {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		System.out.println("Please enter the number of charges in the system:");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
		
		try {
			String inpNumCharges = in.readLine();
			int numCharges = Integer.parseInt(inpNumCharges);
			
			Charge[] charges = new Charge[numCharges];
			
			for (int i = 0; i < numCharges; i++) {
				
				//Initialize each charge
				charges[i] = new Charge();
				
				//Get all input data
				System.out.println("Input the charge for charge #" + i + ":");
				String inpCharge = in.readLine();
				
				
				System.out.println("Input the x-coord for charge #" + i + ":");
				String inpX = in.readLine();
				float x = Float.parseFloat(inpX);
				
				System.out.println("Input the y-coord for charge #" + i + ":");
				String inpY = in.readLine();
				float y = Float.parseFloat(inpY);
				
				System.out.println("Input the z-coord for charge #" + i + ":");
				String inpZ = in.readLine();
				float z = Float.parseFloat(inpZ);
				
				float charge = Float.parseFloat(inpCharge);
				float[] coords = new float[]{x, y, z};
				
				//Set the input data
				charges[i].setCharge(charge);
				charges[i].setCoords(coords);
			}
			
			//Compute and display potential for each charge
			for (int i = 0; i < numCharges; i++) {
				
				//Potential for currently computing charge
				float totalPotential = 0;
				
				for (int j = 0; j < numCharges; j++) {
					if (i != j) {
						totalPotential += charges[i].potentialFromSource(charges[j]);
					}
				}
				
				float[] coords = charges[i].getCoords();
				
				System.out.println("The potential for charge #" + i + " (" + charges[i].getChargeValue() + "C) located at [" + coords[0] + ", " + coords[1] + ", " + coords[2] + "] is " + totalPotential);
			}
			
			//Compute total energy of the system
			float totalEnergy = 0;
			
			for (int i = 0; i < numCharges; i++) {
				for (int j = i; j < numCharges; j++) {
					if (i != j) {
						totalEnergy += charges[i].energyWithOther(charges[j]);
					}	
				}
			}
			
			System.out.println("The total energy of this system is: " + totalEnergy);
			
		}
		catch(IOException e) {
			System.out.println("Error occurred:" + e.getMessage());
		}
		

	}

}
