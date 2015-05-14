import lejos.robotics.subsumption.Behavior;
import lejos.nxt.*;

public class Exit implements Behavior{

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return Button.ESCAPE.isDown();
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		System.exit(0);
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}

}
