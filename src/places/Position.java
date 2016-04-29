package places;

public class Position {

	private int positionX;
	private int positionY;

/*-----------------------------------------------------CONSTRUCTOR------------------------------------------------------*/
	public Position(int x, int y){
		positionX =  x;
		positionY = y;
	}
	
/*-------------------------------------------------------METHODS--------------------------------------------------------*/	
	public int getX(){
		return positionX;
	}
	
	public int getY(){
		return positionY;
	}
	
	@Override
	public boolean equals(Object o){				// till för jämförelser i hashMap
		
		if(o instanceof Position){
		
			Position comparePos=(Position)o;
			
			if(comparePos.getX() == positionX && comparePos.getY() == positionY){
				return true;
			}
		}
		
		return false;
	}
	
	
	@Override
	public int hashCode(){							//// till för jämförelser i hashMap
		return positionX * 31 + positionY;
	}
}
