package mainCity.test;

import mainCity.PersonAgent;
import mainCity.PersonAgent.CityLocation;
import mainCity.interfaces.PersonGuiInterface;
import mainCity.test.Mock;

public class MockPersonGui extends Mock implements PersonGuiInterface{
	public MockPersonGui(String name) {
		super(name);
	}


	public void DoGoToLocation(PersonAgent.CityLocation destination) {
		
	}
	
	public void DoGoToStop() {
			
	}
	
	public void DoGoToLocationOnBus(PersonAgent.CityLocation destination) { 
			
	}
	
	public void DoGoInside() {
		System.out.println("Gui told to go inside by Person");
		log.add(new LoggedEvent("Gui told to go inside by Person"));
	}
	
	public void DoGoOutside() {
		System.out.println("Leaving building and going outside");
		log.add(new LoggedEvent("Gui told to go outside by Person"));
	}

	public CityLocation findNearestStop() {
		return null;
	}
}