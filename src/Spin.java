import lejos.robotics.subsumption.Behavior;
import lejos.nxt.*;


public class Spin implements Behavior {
	private DataExchange dE;
	private RobotControl rC;
	
	public Spin(DataExchange DE, RobotControl RC){
		dE = DE;
		rC = RC;
	}
	
	public boolean takeControl() {
		if (dE.getStatus() > 0){
			rC.enableCamera();
			return true;
		}else
			return false;
	}

	public void suppress() {
		rC.stop();
	}

	public void action() {
		LCD.clear();
		LCD.drawString("spin", 5, 5);
		LCD.drawInt(dE.getColorIndex(), 7, 7);
		if(dE.getDirivative() >= 0){
			rC.turnRight(200);
		}else{
			rC.turnLeft(200);
		}
		
	}
}