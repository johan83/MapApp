package places;

public class Position {
	private int x;
	private int y;
	
	private Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	public static Position createPosition(int x, int y){
		return new Position(x,y);		
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public String toString(){
		return "("+x +","+ y+")";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = result * prime + x;
		result = result * prime + y;
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
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public String toDb() {		
		return x +","+ y;
	}
}
