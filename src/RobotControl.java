import java.awt.Rectangle;


import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorHTSensor;
import lejos.nxt.addon.NXTCam;
import lejos.robotics.Color;
import lejos.robotics.navigation.DifferentialPilot;


public class RobotControl {
	private ColorHTSensor cHTSensor;
	private int clawCnt;
	private NXTCam camera;
	private int errorThreshold;
	private int cameraWidth;
	private double cameraCenterX;
	//private DifferentialPilot pilot;
	
	public RobotControl(){
		camera = new NXTCam(SensorPort.S1);
		camera.sendCommand('A');
		camera.sendCommand('E');
		cHTSensor = new ColorHTSensor(SensorPort.S3);
		clawCnt = 65;
		errorThreshold = 15;
		cameraWidth = 160; //164;
		cameraCenterX = 100.0;
		//pilot = new DifferentialPilot(1.0f, 5.25f, Motor.A, Motor.C);
	}
	
	public void forward(int time, int direction, int speed){
		Motor.A.setSpeed(speed);
		Motor.C.setSpeed(speed);
		if(direction > 0){
			Motor.A.forward();
			Motor.C.forward();
		}else{
			Motor.A.backward();
			Motor.C.backward();
		}
		
		if(time > 0){
			try{
				Thread.sleep(time);
			}catch(Exception ex){
			    System.exit(0);
			} 
			stop();
		}	
	}
	
	
	public void pidMove(int speed , int diff){
		Motor.C.setSpeed(Math.abs(speed + diff));			//left Motor
		Motor.A.setSpeed(Math.abs(speed - diff));			//right Motor
		if(speed + diff > 0)
			Motor.C.forward();
		else
			Motor.C.backward();
		if(speed - diff > 0)
			Motor.A.forward();
		else
			Motor.A.backward();
	}

//	public double getError(Rectangle r){
//		double cameraMid = cameraWidth/2.0;
//		double recMid = r.getCenterX();
//		double diff = recMid - cameraMid;
//		double error = diff*2.0/cameraWidth;
//		if(Math.abs(diff) <= errorThreshold)
//			return 0;
//		else
//			return -error;
//	}
	
	public double getError(Rectangle r){
		double recMid = r.getCenterX();
		double diff = recMid - cameraCenterX;
		double error = diff*2.0/cameraWidth;
		if(Math.abs(diff) <= errorThreshold)
			return 0;
		else
			return -error;
	}
	
	public void enableCamera(){
		camera.sendCommand('E');
	}
	public void disableCamera(){
		camera.sendCommand('E');
	}

	public int getBallID(int colorIndex){
		int num = camera.getNumberOfObjects();
		if(num<=0||num>8){		
			return -1;
		}
		for(int id = 0;id<num;id++){
			if(camera.getObjectColor(id) == colorIndex)
				return id;
		}
		return -1;
	}
	
	public Rectangle getBallRec(int index){
		return camera.getRectangle(index);
	}
	
	public void stop(){
		//if(Motor.A.isMoving())
		Motor.A.stop(true);
		//if(Motor.C.isMoving())
		Motor.C.stop(true);
	}
	public int readCHTSensor(){
		if(cHTSensor.getRGBComponent(Color.RED) > 100)
			return Color.RED;
		else if (cHTSensor.getRGBComponent(Color.GREEN) > 100)
			return Color.GREEN;
		return Color.WHITE;
	}
	
	public void grasp(){
		Motor.B.rotate(clawCnt);
	}
	
	public void loose(){
		Motor.B.rotate(-clawCnt);
	}
	
//	public void hit(){
//		pilot.travel(2,true);
//	}
	
	public void robotWait(int time){
		try{
			Thread.sleep(time);
		}catch(Exception ex){
		    System.exit(0);
		} 
	}
	
	public static void turnLeft(int speed){
		Motor.A.setSpeed(speed);
		Motor.C.setSpeed(speed);
		Motor.A.forward();
		Motor.C.backward();
	}
	
	public static void turnRight(int speed){
		Motor.A.setSpeed(speed);
		Motor.C.setSpeed(speed);
		Motor.A.backward();
		Motor.C.forward();
	}
	
	
	public static void goStraight(int speed){
		Motor.A.setSpeed(speed);
    	Motor.C.setSpeed(speed);
    	
    	Motor.A.forward();
    	Motor.C.forward();
	}
	
	public static void goBack(int speed){
		Motor.A.setSpeed(speed);
    	Motor.C.setSpeed(speed);
    	
		Motor.A.backward();
		Motor.C.backward();
	}
}
