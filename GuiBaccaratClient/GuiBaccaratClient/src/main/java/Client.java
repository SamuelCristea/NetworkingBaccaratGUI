import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;



public class Client extends Thread{

	
	Socket socketClient;
	
	ObjectOutputStream out;
	ObjectInputStream in;
	
	String ip;
	int p;
	
	private Consumer<Serializable> callback;
	
	Client(Consumer<Serializable> call){
	
		callback = call;
	}
	
	public void run() {
		System.out.println("We in here after all run's calls-- BEFORE-BEFORE TRY");
		
		try {
		System.out.println("We in here after all run's calls-- BEFORE IN TRY");
		socketClient= new Socket(ip,p);
		System.out.println("What the hell are these --->" + ip + p);
		System.out.println("We in here after all run's calls-- FIRST");
	    out = new ObjectOutputStream(socketClient.getOutputStream());
	    in = new ObjectInputStream(socketClient.getInputStream());
	    socketClient.setTcpNoDelay(true);
	    System.out.println("We in here after all run's calls-- LAST");
		}
		catch(Exception e) {System.out.println("did we ever fluffing get caught in here??");}
		
		while(true) {
			 
			try {
			String message = in.readObject().toString();
			callback.accept(message);
			}
			catch(Exception e) {}
		}
	
    }
	
	public void sendToClient(String PortNum, String IPAddress) {
		this.ip = IPAddress;
		this.p = Integer.parseInt(PortNum);
		//run();
		//System.out.println("did we pass run??");
	}
	
	public void send(String data) {
		
		System.out.println("Are we here in the send function??");
		
		try {
			out.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
