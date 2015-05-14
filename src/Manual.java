import java.io.DataInputStream;
import java.io.IOException;

import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import lejos.robotics.subsumption.Behavior;


public class Manual implements Behavior{
	private boolean isrunning = true;
	private boolean exit = false;
	private boolean suppressed = false;
	private int regularSpeed = 300;
	private int turnSpeed = 200;
	private int runSpeed = 500;
	private DataExchange dE;
	private NXTConnection btc;
	private DataInputStream dis;
	private RobotControl rC;
	
	public Manual(DataExchange DE, RobotControl RC){
		dE = DE;
		rC = RC;
	}
	
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return (dE.getStatus() == 0);
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		//if(!suppressed){
			LCD.drawString("waiting",0,0);
		    LCD.refresh();
		    //Listen for incoming connection
		    btc = Bluetooth.waitForConnection();
		    btc.setIOMode(NXTConnection.RAW);
		    LCD.clear();
		    LCD.drawString("connected",0,0);
		    LCD.refresh();  
		    if(dE.getStatus() == 0)
				exit = false;

		    //The InputStream for read data 

		    dis=btc.openDataInputStream();
		    while(!exit){
		        Byte n=-1;
		        try {
					n=dis.readByte();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        LCD.clear();
		        LCD.drawInt(n, 4, 4);
		        switch(n){
		        case 0:{
		        	rC.stop();
		        	break;
		        }
		        case 1:{
		        	rC.goStraight(regularSpeed);
		        	break;
		        }
		        case 2:{
		        	rC.goBack(regularSpeed);
		        	break;
		        }
		        case 3:{
		        	rC.turnLeft(turnSpeed);
		        	break;
		        }
		        case 4:{
		        	rC.turnRight(turnSpeed);
		        	break;
		        }
		        case 5:{
		        	rC.goStraight(runSpeed);
		        	break;
		        }
		        case 6:{
		        	rC.grasp();
		        	break;
		        }
		        case 7:{
		        	rC.loose();
		        	break;
		        }
		        case 8:{
		        	exit = true;
		        	System.exit(0);
		        	//break;
		        }
		        case 9:{
		        	Sound.beep();
		        	break;
		        }
		        case 10:{
		        	dE.setStatus(1);
		        	exit = true;
		        	break;
		        }
		        }
		    }
		    try {
				dis.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // wait for data to drain
		    LCD.clear();
		    LCD.drawString("closing",0,0);
		    LCD.refresh();
		    btc.close();
		    LCD.clear();
		//}
	}

	@Override
	public void suppress() {
		rC.stop();
//		try {
//			dis.close();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//		e.printStackTrace();
//		} // wait for data to drain
//		LCD.clear();
//		LCD.drawString("closing",0,0);
//		LCD.refresh();
//		btc.close();
//		LCD.clear();
		// TODO Auto-generated method stub
		//suppressed = true;
	}

}
