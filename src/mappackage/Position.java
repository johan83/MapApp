package mappackage;

public class Position 
{
	private int xPosition, yPosition;
	
	public Position(int xPosition, int yPosition)
	{
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}
	
	public int getXPosition()
	{
		return xPosition;
	}
	
	public int getYPosition()
	{
		return yPosition;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Position)
		{
			Position compare = (Position)o;
			
			return xPosition == compare.getXPosition() && yPosition == compare.getYPosition();
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public int hashCode()
	{
		return (xPosition * 10000) + yPosition;
	}
}
