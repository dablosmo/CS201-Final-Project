package housing.Interfaces;

import housing.personHome;
import housing.gui.OccupantGui;

import java.util.List;

public interface Occupant {


	public abstract void msgAtDestination();

	public abstract void gotHungry();

	public abstract void msgFixed(String appName);

	public abstract void msgNeedFood(List<String> buyFood);

	public abstract void msgCookFood(String foodCh);

	public abstract void msgLeaveHome();

	public abstract Object getHome();

}