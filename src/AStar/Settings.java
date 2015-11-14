/*
 * Thomas Lourenco
 */


package AStar;

public class Settings {
	
	//Flags for ways the AStar algorithm should plan its route.
	public boolean disabilityFlag; //Does the user need to avoid stairs?
	public boolean weatherFlag; //Is the weather bad?
	public boolean footTrafficFlag; //Should the program avoid foot traffic?
	
	//Constructor for settings.
	public Settings(boolean disability, boolean weather, boolean footTraffic){
		this.disabilityFlag = disability;
		this.weatherFlag = weather;
		this.footTrafficFlag = footTraffic;
	}
	
}
