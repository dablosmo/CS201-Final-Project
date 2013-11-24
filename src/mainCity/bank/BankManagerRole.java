package mainCity.bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import agent.Agent;

public class BankManagerRole extends Agent {

	String name;
	public List <myTeller> tellers= new ArrayList<myTeller>();
	public List <myBanker> bankers = new ArrayList<myBanker>();
	public List <myBankCustomer>  teller_bankCustomers = Collections.synchronizedList(new ArrayList<myBankCustomer>());
	public List <myBankCustomer>  banker_bankCustomers = Collections.synchronizedList(new ArrayList<myBankCustomer>());

	public static class myTeller{
	    BankTellerRole t;
	    int tellernumber;
	    BankCustomerRole bc;
	    boolean Occupied;
	    
	    public int gettellernumber(){
	    	return tellernumber;
	    }
	    
	    public void settellernumber(int tn){
	    	tellernumber=tn;
	    }
	    
	    public myTeller(BankTellerRole bt, int tn){
	    	t=bt;
	    	Occupied=false;
	    	tellernumber=tn;
	    }
	}

	public static class myBanker{
	    BankerRole b;
	    int bankernumber;
	    BankCustomerRole bc;
	    boolean Occupied;
	    public myBanker(BankerRole ba){
	    	b=ba;
	    	Occupied=false;
	    }
	}
	
	public static class myBankCustomer{
		BankCustomerRole bc;
		public myBankCustomer(BankCustomerRole bc){
			this.bc=bc;
		}
	}
	
	public BankManagerRole(String name){
		super();
		this.name=name;
		Do("Bank Manager instantiated");
		
	}
	
	//Messages
	public void msgTellerAdded(BankTellerRole bt){
		tellers.add(new myTeller(bt,tellers.size()));
	}
	
	public void msgDirectDeposit(double accountNumber, int amount){
		//TODO what parameter should be used to identify a person? is accountNumber too private?
		
	}
	
	public void msgIWantToDeposit( BankCustomerRole bc){
		Do("recieved message IWantToDeposit");
	    teller_bankCustomers.add(new myBankCustomer(bc));
	    stateChanged();
	}

	public void msgIWantToWithdraw( BankCustomerRole bc){
		Do("recieved message IWantToWithdraw");
		teller_bankCustomers.add(new myBankCustomer(bc));
	    stateChanged();
	}
	
	public void msgIWantNewAccount(BankCustomerRole bc) {
		Do("recieved message want new account");
		banker_bankCustomers.add(new myBankCustomer(bc));
		stateChanged();
		
	}
	
	

	public void msgIWantALoan(BankCustomerRole bc){
		Do("recieved message IWantALoan");
		banker_bankCustomers.add(new myBankCustomer(bc));
	    stateChanged();
	}

	public void msgImLeaving(BankTellerRole bt){
		for (myTeller mt: tellers){
			if(mt.t==bt){
				tellers.remove(mt);
			}
		}
		
	}
	
	public void msgImLeaving(BankerRole b){
		for (myBanker mb: bankers){
			if(mb.b==b){
				bankers.remove(b);
			}
		}
	}
	
	public void msgImLeaving(BankCustomerRole bc){
		Do("recieved message ImLeaving");
	    for (myTeller mt: tellers){
	        if( mt.bc==bc){                                                  
	            mt.bc=null;
	            mt.Occupied=false;
	            stateChanged();
	        }   
	    }   



	    for (myBanker mb: bankers){
	        if( mb.bc==bc){
	            mb.bc=null;
	            mb.Occupied=false;
	            stateChanged();
	        }   
	    }
	    
	}
	
	
	
	
	
//TODO handle scenarios where not enough employees	
	
	protected boolean pickAndExecuteAnAction() {
		if(tellers.isEmpty() || bankers.isEmpty()){
			sayClosed();
			return false;
		}
		
		for(myTeller mt:tellers){
			if(!mt.Occupied && !teller_bankCustomers.isEmpty()){
				
					assignTeller(mt);
					return true;	
				
				
			}
		}
		
		for(myBanker mb: bankers){
			if(!mb.Occupied && !banker_bankCustomers.isEmpty()){
				assignBanker(mb);
				return true;
			}
			
		}
			
		
		return false;
	}

	
//Actions
	
	private void sayClosed(){
		synchronized(banker_bankCustomers){
			for(myBankCustomer mb : banker_bankCustomers){
				mb.bc.msgBankClosed();
				//banker_bankCustomers.remove(mb);
				
			}
		}	
		synchronized(teller_bankCustomers){
			for (myBankCustomer mb: teller_bankCustomers){
				mb.bc.msgBankClosed();
				//teller_bankCustomers.remove(mb);
			}
		}
	}
	
	private void assignTeller(myTeller mt){
	Do("assigning teller");
	    teller_bankCustomers.get(0).bc.msgGoToTeller(mt.t, mt.tellernumber);
	    mt.bc=teller_bankCustomers.get(0).bc;
	    mt.Occupied=true;
	    teller_bankCustomers.remove(0);
	}
	private void assignBanker(myBanker mb){
		Do("Assigning banker");
	    banker_bankCustomers.get(0).bc.msgGoToBanker(mb.b, mb.bankernumber);
	    mb.bc=banker_bankCustomers.get(0).bc;
	    mb.Occupied=true;
	    banker_bankCustomers.remove(0);
	}


	
	
	
}