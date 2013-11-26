package mainCity.bank.interfaces;

import mainCity.PersonAgent;
import mainCity.bank.BankAccounts;
import mainCity.bank.gui.BankerGui;

public interface Banker {


	//Messages
	public abstract void msgGoOffDuty(double d);

	public abstract void msgGoToWork();

	public abstract void msgIWantALoan(BankCustomer b, double accnum,
			double amnt);

	public abstract void msgIWantNewAccount(PersonAgent p, BankCustomer b,
			String name, double amnt);
}