package mainCity.interfaces;
import java.util.Map;
import mainCity.market.*;

public interface MainCook 
{
	public abstract void setMarketGreeter(MarketGreeterRole g);
	public abstract void msgHereIsYourOrder(Map<String, Integer> inventory);
	
}
