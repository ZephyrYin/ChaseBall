
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.*;

public class TestBehavior {
	private static DataExchange dE;
	private static int redBallIndex = 0;
	private static int blueBallIndex = 1;
	private static RobotControl rC;
	public static void main(String [] args) {
		dE = new DataExchange(0, 0, false);		// status colorIndex grasped
		//dE = new DataExchange(1, 1, true);	
		rC = new RobotControl();
		Behavior man = new Manual(dE, rC);
		Behavior spin = new Spin(dE, rC);
	    Behavior pidChase = new PID(dE, rC);
	    Behavior graspRed = new Grasp(dE, 0, rC);
	    Behavior shoot = new Shoot(dE, rC);
	    Behavior exitMission = new Exit();
	    
	    Behavior [] bArray = {man, spin, pidChase, graspRed , shoot, exitMission};
	    //Behavior [] bArray = {manspin, pidChase, graspRed, exitMission};
	    Arbitrator arby = new Arbitrator(bArray);
	    arby.start();
	}
}
