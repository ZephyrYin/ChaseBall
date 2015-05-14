import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.TouchSensor;
import lejos.nxt.addon.ColorHTSensor;
import lejos.robotics.Color;
import lejos.robotics.subsumption.Behavior;

public class Shoot implements Behavior{
	
	private DataExchange dE;
	private RobotControl rC;
	
	public Shoot(DataExchange DE, RobotControl RC){
		dE = DE;
		rC = RC;
	}

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		if(dE.redGrasped()&&dE.getStatus() == 5)
			return true;
		else
			return false;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		//forward(500,1, 300);
		rC.stop();
		rC.loose();
		rC.robotWait(1000);
		rC.forward(500,1, 500);
		rC.stop();
		System.exit(0);
		//suppress();
		
		//System.exit(0);
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		rC.stop();
		dE.setColorIndex(0);
		dE.setRedGrasped(false);
		dE.setDirivative(0);
		dE.setStatus(0);
	}
}
