
import java.util.HashMap;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.TouchSensor;
import lejos.nxt.addon.ColorHTSensor;
import lejos.robotics.Color;
import lejos.robotics.subsumption.Behavior;

public class Grasp implements Behavior{
	
	private int targetColor;
	private DataExchange dE;
	private RobotControl rC;
	private HashMap colorMap = new HashMap();  
	
	public Grasp(DataExchange DE, int colorIndex, RobotControl RC){
		dE = DE;
		rC = RC;
		colorMap.clear();
		colorMap.put(0, Color.RED);
		colorMap.put(1, Color.GREEN);
		targetColor = (Integer) colorMap.get(colorIndex);
	}
	
	public boolean takeControl() {
		if(dE.getStatus() == 0 )
			return false;
		int c = rC.readCHTSensor();
		if(!dE.redGrasped()&&(c == targetColor))
			return true;
		return false;
	}

	public void suppress() {
		rC.stop();
	}

   	public void action() {
   		rC.stop();
		rC.grasp();
//		robotWait(1000);
//		loose();
//		System.exit(0);
		//forward(500, -1, 300);
		dE.setRedGrasped(true);
		
		dE.setColorIndex(1); 		// start chasing blue ball
		dE.setStatus(4);
		suppress();
   	}
   	
   	
   	
	
   
	
}
