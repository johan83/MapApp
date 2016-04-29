package places;
import javax.swing.*; 
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class Place extends JComponent {

	private String name;
	private Position position;
	private TravelCategory color;
	private boolean showInfo = false;
	private boolean marked = false;
	private int sizeX = 30;
	private int sizeY = 25;
	Registry register;

/*------------------------------------------------CONSTRUCTOR---------------------------------------------------------------*/
	public Place(String name, Position position, Registry register){
		this.name = name;
		this.position = position;
		this.register = register;
		this.addMouseListener(new MarkLyss());
		
		this.setBounds(position.getX()-15,position.getY()-25,sizeX,sizeY);
		this.setPreferredSize (new Dimension(100,100));
		this.setMaximumSize (new Dimension(100,100));
		this.setMinimumSize (new Dimension(100,100));
		setVisible(true);
	}
	
/*-------------------------------------------------METHODS-------------------------------------------------------------------*/	
	public String getName(){
		return name;
	} 
	
	public Position getPosition(){
		return position;
	}
	public int getPositionX(){
		return position.getX();
	}
	public int getPositionY(){
		return position.getY();
	}
	public int getSizeX(){
		return sizeX;
	}
	public int getSizeY(){
		return sizeY;
	}
	
	void setMarked(){
		marked = true;
		repaint();
	}
	
	void setNotMarked(){
		marked = false;
		repaint();
	}
	
	void setDontShowInfo(){
		showInfo = false;
		repaint();
	}
	
	public TravelCategory getColor(){
		return color;
	}
	
	public void setCategory(TravelCategory color){
		try{
		this.color = color;
		}catch(NullPointerException e){
			// Inget problem, då Place kan va kategorilös!
		}
	}
	
	
	public boolean getShowInfo(){
		return showInfo;
	}
	public boolean getMarked(){
		return marked;
	}
	
	public abstract String toString();				// kolla child
					// först + PLATSTYP +  sist + BESKRIVNING
	
	public void drawIfMarked(Graphics g){
		if(marked){
			g.setColor(Color.RED);
			g.drawRect(0, 0,getBounds().width-1, getBounds().height -1);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g){
		if(showInfo){
			paintPlaceInfo(g);
		}else{
			paintPlace(g);			
			// denna if-sats skulle kunna ligga utanför paintComponent 
			//och i så fall kunde describedPlace istället representeras av en pålagd panel med en textArea.
		}
	}
	
	private void paintPlace(Graphics g) {
		super.paintComponent(g);
		
		this.setBounds(getPositionX()-15,getPositionY()-25, sizeX ,sizeY);
		
		g.setColor(Color.BLACK);
		int[] xLed = {0,15,30};
		int[] yLed = {0,25,0};
		
		if(color != null){
			switch(color.toString()){
			
			case "BUS":
				g.setColor(Color.RED);
				break;
			
			case "TRAIN":
				g.setColor(Color.GREEN);
				break;
				
			case "SUBWAY":
				g.setColor(Color.BLUE);
				break;
			default:
				}
		}
		
		g.fillPolygon(xLed, yLed, 3);	
		
		drawIfMarked(g);

	}
	protected abstract void paintPlaceInfo(Graphics g);			// Utseende och Info definieras i varje given Place-child 
	
/*----------------------------------------------------------CLASSES---------------------------------------------------------*/	
	class MarkLyss implements MouseListener{
		
		
		public void mouseClicked(MouseEvent mev){
			switch(mev.getButton()){
			
				case MouseEvent.BUTTON1:
					System.out.println("VÄNSTER");
					if(marked){
						marked = false;
						register.getMarkedPlace().remove(Place.this);
					//TA BORT UR MARKED LIST
					}
					else{
						marked = true;
						register.getMarkedPlace().add(Place.this);
					// LÄGG TILL I MARKEDLIST
					}
					repaint();
					break;
					
				case MouseEvent.BUTTON3:
					System.out.println("HÖGER");
					if(showInfo)
						showInfo = false;
					else
						showInfo = true;
					repaint();
					break;
					
				default:
					return;	
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
}


