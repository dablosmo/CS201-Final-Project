package role.jeffersonRestaurant;

import mainCity.PersonAgent;

public class JeffersonNormalWaiterRole extends JeffersonWaiterRole {

	public JeffersonNormalWaiterRole(PersonAgent p, String name) {
		super(p, name);
		
	}

	protected void tellCook(int table, String choice) {

		log("sending order to cook");
		
		
		waiterGui.DoGoToCook();
		
		try {
			atCook.acquire();
		} 
		catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
		cook.msghereIsAnOrder(table, choice,this);
		
		waiterGui.DoLeaveCustomer();
		try {
			atHome.acquire();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}

	}

}
