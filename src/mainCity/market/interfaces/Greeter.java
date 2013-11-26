package mainCity.market.interfaces;

import java.util.Map;

import role.market.MarketDeliveryManRole;
import mainCity.interfaces.MainCashier;
import mainCity.interfaces.MainCook;
import mainCity.market.MarketCustomerRole;


public interface Greeter {
	public abstract void setCashier(Cashier c);
	public abstract void setDeliveryMan(DeliveryMan d);
	public abstract void addEmployee(Employee e);
	
	public abstract void msgINeedInventory(MarketCustomerRole c, int x, int y);
	public abstract void msgINeedInventory(String restaurantName, MainCook cook, MainCashier cashier, Map<String, Integer> inventoryNeeded);
	
}