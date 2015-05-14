
public class DataExchange {
	private int status;
	private int colorIndex;
	private boolean redBallIsGrasped;
	private double derivative;
	
	public DataExchange(int s, int c, boolean f){
		status = s;
		colorIndex = c;
		redBallIsGrasped = f;
		derivative = 0;
	}
	
	public void setStatus(int s){
		status = s;
	}
	
	public int getStatus(){
		return status;
	}
	
	public void setColorIndex(int index){
		colorIndex = index;
	}
	
	public int getColorIndex(){
		return colorIndex;
	}
	
	public void setRedGrasped(boolean value){
		redBallIsGrasped = value;
	}
	
	public boolean redGrasped(){
		return redBallIsGrasped;
	}
	
	public void setDirivative(double d){
		derivative = d;
	}
	
	public double getDirivative(){
		return derivative;
	}
}
