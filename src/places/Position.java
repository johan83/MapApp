package places;

public class Position {
	private int positionX;
	private int positionY;
	
	public Position(int x, int y){
		x = positionX;
		y = positionY;
	}
	
	public int getX(){
		return positionX;
	}
	public int getY(){
		return positionY;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + positionX;
		result = prime * result + positionY;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (positionX != other.positionX)
			return false;
		if (positionY != other.positionY)
			return false;
		return true;
	}
}
