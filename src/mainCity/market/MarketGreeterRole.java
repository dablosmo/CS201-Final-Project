package mainCity.market;

import agent.Agent;
import mainCity.market.MarketEmployeeRole;
//import restaurant.gui.HostGui;

//import restaurant.interfaces.*;


import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Restaurant Host Agent
 */
//change to GreeterAgent
public class MarketGreeterRole extends Agent {
	private String name;
	
	private List<MyWaitingCustomer> waitingCustomers 
	= Collections.synchronizedList(new ArrayList<MyWaitingCustomer>());
	private List<MyWaitingBusiness> waitingBusinesses
	= Collections.synchronizedList(new ArrayList<MyWaitingBusiness>());
	private List<MyEmployee> myEmployees
	= Collections.synchronizedList(new ArrayList<MyEmployee>());

	
	int nextEmployee = 0;

	
	public MarketGreeterRole(String name) {
		super();

		this.name = name;
		
	}

	public String getMaitreDName() {
		return name;
	}

	public String getName() {
		return name;
	}


	public List getWaitingCustomers(){
		return waitingCustomers;
	}

	
	// Messages
	
	public void msgINeedInventory(MarketCustomerRole c){
		print("Received msgINeedInventory");
		waitingCustomers.add(new MyWaitingCustomer(c));
		stateChanged();
	}
	
	/*
	public void msgINeedInventory(Restaurant r, String delivery, Map<String, Integer> inventoryNeeded){
		print("Received msgINeedInventory");
		waitingBusinesses.add(new MyWaitingBusiness(r, delivery, inventoryNeeded));
		stateChanged();
	}
	*/
	

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	protected boolean pickAndExecuteAnAction() {
		

		/* Think of this rule as:
        Does there exist a table and customer and waiter,
        so that table is unoccupied, customer is waiting, and waiter has
        the least # of customers.
        If so assign the customer to that table and waiter.
	 */


		
		if (!waitingCustomers.isEmpty()){
			if (!myEmployees.isEmpty()){
				nextEmployee++;
				if (nextEmployee > myEmployees.size() - 1)
					nextEmployee = 0;
				
				assignCustomerToEmployee(waitingCustomers.get(0), myEmployees.get(nextEmployee));
				
				return true;
			}
		}
		
		
		if (!waitingBusinesses.isEmpty()){
			if (!myEmployees.isEmpty()){
				nextEmployee++;
				if (nextEmployee > myEmployees.size() - 1)
					nextEmployee = 0;
				
				assignBusinessToEmployee(waitingBusinesses.get(0), myEmployees.get(nextEmployee));
				
				return true;
			}
		}


		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions

	private void assignCustomerToEmployee(MyWaitingCustomer cust, MyEmployee me) {
		print("Assigning " + cust.c.getName() + " to " + me.e.getName());
		
		me.e.msgAssignedToCustomer(cust.c, cust.waitingPosX, cust.waitingPosY);
		cust.c.msgAssignedToEmployee(me.e, me.homeX, me.homeY);
		waitingCustomers.remove(cust);
	}
	private void assignBusinessToEmployee(MyWaitingBusiness busin, MyEmployee e){
		/*print("Assigning " + busin.r.getName() + " to " + e.getName());*/
		
		//e.msgAssignedToBusiness(busin.r, busin.deliveryMethod, busin.inventory);
		waitingBusinesses.remove(busin);
	}


	
	//utilities
/*
	public void setGui(HostGui gui) {
		hostGui = gui;
	}

	public HostGui getGui() {
		return hostGui;
	}
*/
	
	public void addEmployee(MarketEmployeeRole e, int x, int y){
		myEmployees.add(new MyEmployee(e, x, y));
		stateChanged();
	}
	
	private class MyEmployee {
		MarketEmployeeRole e;
		int homeX, homeY;
		
		MyEmployee(MarketEmployeeRole e, int homeX, int homeY){
			this.e = e;
			this.homeX = homeX;
			this.homeY = homeY;
		}
	}
	
	
	private class MyWaitingBusiness {
		//Restaurant r;
		String deliveryMethod;
		Map<String, Integer> inventory;
		
		MyWaitingBusiness(/*Restaurant r,*/ String delivery, Map<String, Integer> inventoryNeeded){
			//this.r = r;
			deliveryMethod = delivery;
			inventory = inventoryNeeded;
		}
	}
	
	private class MyWaitingCustomer {
		MarketCustomerRole c;
		//boolean confirmedToWait;
		
		int waitingPosX;
		int waitingPosY;

		MyWaitingCustomer (MarketCustomerRole cust, int waitingX, int waitingY) {
			this.c = cust;
			this.waitingPosX = waitingX;
			this.waitingPosY = waitingY;
		}
		
		MyWaitingCustomer(MarketCustomerRole cust){
			c = cust;
		}
	}
}

