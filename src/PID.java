import java.awt.Rectangle;

import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.NXTCam;
import lejos.robotics.Color;
import lejos.robotics.subsumption.Behavior;

public class PID implements Behavior {
	
	private int kP = 100;
	private int kI = 1;
	private double kD = 1000;
	private long startTime;
	private DataExchange dE;
	private int regularSpeed = 400;
	private int targetColorIndex;
	private int targetRecID;
	private RobotControl rC;
	public PID(DataExchange DE, RobotControl RC){
		dE = DE;
		rC = RC;
		targetColorIndex = 0;
		targetRecID = 0;
		startTime = System.currentTimeMillis();
	}
	
	public boolean takeControl() {
		if(dE.getStatus() == 0)
			return false;
		int id = rC.getBallID(dE.getColorIndex());
		if(id != -1){
			targetColorIndex = dE.getColorIndex();
			startTime = System.currentTimeMillis();
			return true;
		}
		return false;
	}

	public void suppress() {
		rC.stop();
		rC.disableCamera();
	}

	public void action() {
		LCD.clear();
		LCD.drawString("pid", 5, 5);
		int id = rC.getBallID(targetColorIndex);
		LCD.drawInt(id, 5, 5);
		if(id != -1){
			double integral = 0;
			double lastError = 0;
			double derivative = 0;
			
			long elapsed = System.currentTimeMillis() - startTime; 
			startTime = System.currentTimeMillis();
			double dT = (double) elapsed;
			Rectangle r = rC.getBallRec(id);
			double error = rC.getError(r);
			integral =  (2.0/3.0) * integral + error * dT;
			derivative = (error - lastError)/dT;
			dE.setDirivative(derivative);
			lastError = error;
			int turn = (int)(kP * error + kI * integral + kD * derivative);		
			if(error == 0){
				if(dE.getColorIndex() == 1){
					rC.stop();
					rC.robotWait(1000);
					dE.setStatus(5);
					return;
				}
				rC.forward(-1, 1, regularSpeed);
			}else{
				rC.pidMove(regularSpeed, turn);
			}
		}else{
			return;
		}	
	}
}