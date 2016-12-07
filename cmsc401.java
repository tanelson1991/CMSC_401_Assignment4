package assignment4;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;

public class cmsc401
{
	//create a City class to store and use information about each of the various cities
	class City{
		
		int lodging,cityNum, total, currentCost;
		String cityName;
		boolean visited = false;
		// an arrayList that will hold each individual city's highways that are connected to it
		ArrayList<Highway> intrastates = new ArrayList<Highway>();
		
		public City(){
			this.lodging = 0;
			this.cityNum = 0;
			this.cityName = null;
			this.total = 0;
			this.currentCost = 0;
		}

		public int getTotal()
		{
			return total;
		}

		public void setTotal(int total)
		{
			this.total = total;
		}

		public int getCurrentCost()
		{
			return currentCost;
		}

		public void setCurrentCost(int currentCost)
		{
			this.currentCost = currentCost;
		}

		public boolean isVisited()
		{
			return visited;
		}

		public void setVisited(boolean visited)
		{
			this.visited = visited;
		}		
	}
	// create a Highway class to store and use information about each of the highways
	class Highway{
		int cost, highwayNum;
		City beginCity;
		City endCity;
		
		public Highway(){
			this.cost = 0;
		}
		
	}
	
	
	// method that will traverse through the cities using a Dijkstra-like algorithm
	static void travel(City start){
		int currentCost, cheapest=Integer.MAX_VALUE;
		City nextCity=null, destination;
		for(int i=0; i<start.intrastates.size(); i++){
			/////////////////////////////////////////////////////////////////////////////
			// the way the input is set up you have to determine whether the end city matches the current city
			// if it does, then you have to flip the values because it will move to the end city (aka the same current city)
			// if you don't, you will just be in an infinite loop of moving to the same city in certain cases
			//if(start.cityNum == 1)
			//	start.visited = true;
			if(start.intrastates.get(i).endCity == start){
				destination = start.intrastates.get(i).beginCity;				
			}
			else 
				destination = start.intrastates.get(i).endCity;
			/////////////////////////////////////////////////////////////////////////////
			
			// if you've already visited the city you obviously don't want to travel back to it
			if(destination.visited == true){
				continue;
			}
			
			// if you come to a node and the value is set to infinity change it to the cost it takes to get there
			if(destination.getTotal() == Integer.MAX_VALUE){
				destination.setTotal(start.intrastates.get(i).cost + destination.lodging + start.getTotal());
			}
			// else check if the cost it took to get there on this current path is cheaper
			// if it is then set the new total for the destination to the cheaper
			else if(destination.getTotal() > (start.getTotal() + start.intrastates.get(i).cost + destination.lodging)){
				destination.setTotal(start.getTotal() + start.intrastates.get(i).cost + destination.lodging);
			}
						
			// set currentCost = to the highway cost + the cost of the destination lodging
			currentCost = (start.intrastates.get(i).cost + destination.lodging);
			// determine which route will be the cheapest from the current city
			if(currentCost < cheapest){
				cheapest = currentCost;
				nextCity = destination;
			}
			
			// once you evaluate all of the highways connected to the current city you set the fact that you've
			// visited this city to true
			if(i == start.intrastates.size()-1){
				start.setVisited(true);
				}

		}// end of for loop	
		
		if(nextCity == null)
			return;
		travel(nextCity);

	}// end travel method	

	public static void main(String[] args)
	{
		//System.out.println();
		cmsc401 c = new cmsc401();
		Scanner input = new Scanner(System.in);
		
		// take in number of cities
		int numCities = input.nextInt();
		
		// ArrayList to hold the city objects
		ArrayList <City> cities = new ArrayList<City>();
		
		// fill the ArrayList with City objects
		for(int i=0; i<numCities; i++){
			City city = c.new City();
			city.cityName = "City " + (i+1);
			city.cityNum = (i+1);
			city.total = Integer.MAX_VALUE;
			cities.add(city);
		}
		// set the first city total to 0 because it has no associated cost
		cities.get(0).setTotal(0);
		
		// take in the number of highways
		int highways = input.nextInt();
		
		// create a 2d array to store all of the lodging cost information
		int motels[][] = new int[numCities][2];
		
		// fill the 2d array with the lodging values
		for(int i=0; i<(numCities-2); i++){
			for(int j=0; j<2; j++){
				motels[i][j] = input.nextInt();
			}	
		}
		
		// update each city's lodging cost from the 2d array
		for(int i=0; i<(numCities-2); i++){
			for(int j=0; j<2; j++){
				for(int k=0; k<cities.size(); k++){
					cities.get(motels[i][0]-1).lodging = motels[i][1];
				}
			}	
		}
		
		// create a 2d array to store the cost of gas for each highway
		int gas[][] = new int[highways][3];
		
		// fill the 2d array with values for gas along each highway
		for(int i=0; i<highways; i++){
			for(int j=0; j<3; j++){
				gas[i][j] = input.nextInt();
			}
		}
		
		// create ArrayList that holds all the Highway objects
		ArrayList<Highway> interstates = new ArrayList<Highway>();
		
		// update each highway's gas cost
		for(int i=0; i<highways; i++){
			Highway interstate = c.new Highway();
			interstate.highwayNum = (i+1);
			interstates.add(interstate);
		}
		
		// add the highways to each of the cities in the City ArrayList
		for(int i=0; i<highways; i++){
			for(int j=0; j<3; j++){
				
				if(j==0){
					cities.get(gas[i][0]-1).intrastates.add(interstates.get(i));
					cities.get(gas[i][1]-1).intrastates.add(interstates.get(i));
					interstates.get(i).beginCity = cities.get(gas[i][0]-1);
					interstates.get(i).endCity = cities.get(gas[i][1]-1);
					interstates.get(i).cost = gas[i][2];
				}
				else continue;
			}
		}	
		
		// you can use the following commented code to display information about all of the various cities and highways
		
/*		for(int i=0; i<highways; i++){
			System.out.println("Highway " + interstates.get(i).highwayNum + " costs $" + interstates.get(i).cost);
		}*/
		
		
/*		for(int i=0; i<cities.size(); i++){
			System.out.println("Price of lodging in city " + cities.get(i).cityName + " is " + cities.get(i).lodging);
					
		}*/	
		
		
/*		for(int i=0; i<cities.size(); i++){
			City thisCity = cities.get(i), checker;
			for(int j=0; j<cities.get(i).intrastates.size(); j++){
				if(thisCity.intrastates.get(j).endCity == thisCity)
					checker = thisCity.intrastates.get(j).beginCity;
				else checker = thisCity.intrastates.get(j).endCity;
				
				System.out.print(thisCity.cityName + " has " + cities.get(i).intrastates.size() + " highways of costs: " );
				System.out.println(cities.get(i).intrastates.get(j).cost + " to city " + checker.cityNum);
			}
			System.out.println();
		}*/
				
		
		// travel through the cities and highways implementing Dijkstra's(Taylor Nelson's) algorithm
		travel(cities.get(0));
		
		
		System.out.println(cities.get(1).total);
		
		
		//System.out.println(cities.get(j).cityName + " " + cities.get(j).total);

		input.close();
		
	}// end of main method
	

}// end of class cmsc401


