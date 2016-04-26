package places;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;



public class ProgramTest extends JFrame{

	private boolean nyInfo = true; // bestämmer stäng eller ej.
	private String longName; 
	private File valdFil;
	public MouseLyss mouseLyss;
	private ImageIcon image;
	private ImageArea imageArea;
	
	private Registry register = new Registry();

		
	private JMenuBar menu = new JMenuBar();
	private JMenu archive = new JMenu("Archive"); // new Map, Load Places, Save, Exit
	JMenuItem newMap = new JMenuItem("New Map");
	
	private JFileChooser jfc = new JFileChooser("."); // startar filsökning från aktuell mapp
	JScrollPane mapScrollbar;
	
	private JPanel northPanel = new JPanel();
	private JPanel eastPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	
	
	
	private JLabel newPlaceLabel = new JLabel("New:");
	
	String[] JComboAlternativ = {"NamedPlace", "DescribedPlace"};
	private JComboBox<String> choosePlaceType = new JComboBox<String>(JComboAlternativ);
	
	private JTextField searchField = new JTextField("Search", 10);
	
	private JButton searchButton = new JButton("Search");
	private JButton hideButton = new JButton("Hide");
	private JButton removeButton = new JButton("Remove");
	private JButton whatIsHereButton = new JButton("What is here?");
	
	
	
	private JLabel categoriesLabel = new JLabel("Categories");
	private String[] categories = {"Bus", "Train", "Subway"} ;
	private JList<String> categoryList = new JList<String>(categories);
	private JButton hideCategoryButton = new JButton("Hide category");

	
	/*------------------------------------------------------------------ CONSTRUCTOR ----------------------------------------------------------------------*/
	
	
	public ProgramTest(){
		//JFrame program = new JFrame("Inlupp 2");
		super("Inlupp 2");
		Exit exitWindow = new Exit();
		this.addWindowListener(exitWindow);
		setJMenuBar(menu);
		menu.add(archive);
		
		
		newMap.addActionListener(new NewMapLyss());
		archive.add(newMap);
		
		JMenuItem loadPlaces = new JMenuItem("Load Places");
		archive.add(loadPlaces);
		JMenuItem save = new JMenuItem("Save");
		archive.add(save);
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ExitLyss());
		archive.add(exit);
		
		setLayout(new BorderLayout());
		northPanel.add(newPlaceLabel);
		choosePlaceType.addActionListener(new ChoosePlaceTypeLyss());
		northPanel.add(choosePlaceType);
		
		
		
		northPanel.add(searchField);
		northPanel.add(searchButton);
		northPanel.add(hideButton);
		northPanel.add(removeButton);
		northPanel.add(whatIsHereButton);
		add(northPanel, BorderLayout.NORTH);
		add(eastPanel, BorderLayout.EAST);
		eastPanel.setLayout(new BoxLayout(eastPanel,BoxLayout.Y_AXIS));
		eastPanel.add(categoriesLabel);
		categoryList.setAlignmentX(LEFT_ALIGNMENT); //Alignement (verkar bugga -> vänster blir höger) 
		categoryList.setFixedCellWidth(150);
		categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);			// Om jag vill kunna deselecta en kategori måste jag till föra en klass där jag implementerat detta.
		eastPanel.add(categoryList);
		
		hideCategoryButton.setAlignmentX(LEFT_ALIGNMENT); //Alignment
		eastPanel.add(hideCategoryButton); 
		
		centerPanel.setLayout(new BorderLayout());			//GRID LR BOXLAYOUT FIXAR STORLEKEN PÅ BILDEN MEN VAD MED SCROLLPANE?   -----BorderLayout lägger i mitten och alla över varandra.
		add(centerPanel, BorderLayout.CENTER);	
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(650, 650);
		//setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
		
	}
	
	/*------------------------------------------------------------- METHODS -----------------------------------------------------------------------------------*/
	
	private void avsluta(){
		int ok = JOptionPane.OK_OPTION;
		if(!nyInfo){
			System.exit(0);
		}else{
			int svar = JOptionPane.showConfirmDialog(null,"Vill du avsluta ändå?","Osparade ändringar",
					JOptionPane.OK_CANCEL_OPTION);
			
			if(svar == ok){
				System.exit(0);
			}
		}
	}
	
	
	/*--------------------------------------------------------------- CLASSES ----------------------------------------------------------------------------------*/
	
	class ImageArea extends JPanel{
		
		
		
		ImageArea(){
			image = new ImageIcon(longName); 
			setLayout(null);
		}
		
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(image.getImage(), 0, 0, this);
			
		}
	}
	
	
	class MouseClicked extends MouseAdapter{
		private Position nyPlatsPosition;
		private int X;
		private int Y;
		
		public void mouseClicked(MouseEvent mev){
		nyPlatsPosition = new Position(mev.getX(), mev.getY());
		X= mev.getX();
		Y= mev.getY();
		// STÄLLA TILLBAKA MUSPEKAREN!?
		}	
		
		public Position getNyPlatsPosition(){
			return nyPlatsPosition;
		}
		public int getX(){
			return X;
		}
		public int getY(){
			return Y;
		}
	}
	
	class MouseLyss extends MouseAdapter{
	
		
		int hoverX;
		int hoverY;
		
		
		public void mouseEntered(MouseEvent mev){
			hoverX = mev.getX();
			hoverY = mev.getY();
			Cursor c = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
			
			if(mapScrollbar.contains(hoverX,hoverY)){
			mapScrollbar.setCursor(c);
			
			}
		}
	}
	
	
	class ChoosePlaceTypeLyss implements ActionListener{
		
		Place nyPlats;
		
		public void actionPerformed(ActionEvent ave){
			TravelCategory colorToUse;
			mouseLyss = new MouseLyss();
			MouseClicked posLyss = new MouseClicked();
			mapScrollbar.addMouseListener(mouseLyss);			//SKA DEN ADDAS TILL MAPSCROLLBAR ELLER TILL BILDEN?		--> Ger Nullpointer om man inte laddat en bild först. fixa?!
			//Position nyPlatsPosition = mouseLyss.getNyPlatsPosition();	// Position ska ändras till där användaren markerar på kartan.
			
			
			
			/* ----------------------------------------NEDAN FUNKAR SO FAR--------------------------------------*/
			
			JPanel namedPlacePanel= new JPanel();
			JPanel describedPlacePanel = new JPanel();
	
			JPanel rad1= new JPanel();
			JPanel rad2 = new JPanel();
			
			JTextField textField = new JTextField(10);
			JTextField describeField = new JTextField(10);
			
			rad1.add(new JLabel("Platsens namn:"));
			
			rad2.add(new JLabel("Description:"));
			rad2.add(describeField);
			
			String name = textField.getText();
			String description = describeField.getText();
			
			
			if(choosePlaceType.getSelectedItem().equals(("NamedPlace"))){	
				namedPlacePanel.setLayout(new BoxLayout(namedPlacePanel, BoxLayout.Y_AXIS));
				namedPlacePanel.add(rad1);
				
				String ifyllt = JOptionPane.showInputDialog(null, namedPlacePanel,  "New Named Place", JOptionPane.QUESTION_MESSAGE);			// den här ska bli frågeformuläret
				
				if(ifyllt != null && ifyllt.length()>0){
					System.out.println(ifyllt);	//TEST ---> funkar
					mapScrollbar.addMouseListener(posLyss);		// spelar ingen roll om den sätts på mapScrollbar eller imageArea.. får inte till det.
					
					Position nyPlatsPosition = new Position(posLyss.getX(), posLyss.getY());		// börjar bli förvirrad, men borde läsa av att jag klickar innan plats skapas!
					
					System.out.println(nyPlatsPosition.getX());		// ---> NULL
					System.out.println(posLyss.getNyPlatsPosition());		// ---> NULL
					
					if(nyPlatsPosition != null){
						nyPlats = new NamedPlace(name , nyPlatsPosition);			// Denna måste sparas i en samling.	
						register.addPlace(nyPlats);				// VÄNTA NU, DEN ADDERAR PLATSEN INNAN JAG HAR KLICKAT!
						
						
						System.out.println((String)choosePlaceType.getSelectedItem());			//För att visa att det går!
					}
				}else{
					System.out.println("Tom sträng"); // TEST ---> Funkar
					return;
				}
					

				
				
			}else if(choosePlaceType.getSelectedItem().equals("DescribedPlace")){
				Position nyPlatsPosition = posLyss.getNyPlatsPosition();
				describedPlacePanel.setLayout(new BoxLayout(describedPlacePanel, BoxLayout.Y_AXIS));
				describedPlacePanel.add(rad1);
				describedPlacePanel.add(rad2);
				
				JOptionPane.showMessageDialog(null, describedPlacePanel , "New Described Place", JOptionPane.QUESTION_MESSAGE);			//Frågeformuläret
				
				
				
				nyPlats = new DescribedPlace(name, nyPlatsPosition, description);
				
				System.out.println((String)choosePlaceType.getSelectedItem());			////För att visa att det går!
			}
			
			if(!(categoryList.isSelectionEmpty())){
				String color = (String)categoryList.getSelectedValue();
				
				for(TravelCategory c : TravelCategory.values()){
					
					if(color.equalsIgnoreCase(c.name())){				
						colorToUse = c;
						nyPlats.setCategory(colorToUse);
						System.out.println("DEN NYA PLATSEN HAR NU FÅTT EN KATEGORI!!!");
					}
				}
			}
		}
	}
	
	

	class NewMapLyss implements ActionListener{
		
		public void actionPerformed(ActionEvent ave){
			int ok = JFileChooser.APPROVE_OPTION;
			int jfcSvar = jfc.showOpenDialog(newMap);	// för att kunna ladda filer
			if (jfcSvar == ok){
				
				if(centerPanel.getComponents() != null){
					centerPanel.removeAll();
				}
				
			valdFil = jfc.getSelectedFile();
			longName = valdFil.getAbsolutePath();
			imageArea = new ImageArea();
			mapScrollbar = new JScrollPane(new JLabel(image));//------------------------------- Har lyft ut ImageArea till instansvariabel för att kunna lägga PlaceGUI på den.	
			// nu syns inte/ funkar inte scrollbaren... det gör den på JLabel varianten av en bild.
			
			centerPanel.add(mapScrollbar);								
			centerPanel.validate();			// Nödvändig?
			mapScrollbar.validate();		// Nödvändig?
			}			
		}
	}
	

	class LoadPlacesLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			// load
		}
	}
	
	class SaveLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			// save
		}
	}
	
	class ExitLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			avsluta();
		}
	}
	
	class Exit extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent wev){
			avsluta();
		}
	}
	
	public static void main(String []args){
		ProgramTest program = new ProgramTest();
		
	}
}

