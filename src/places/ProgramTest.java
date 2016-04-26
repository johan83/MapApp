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
	
	class MouseLyss extends MouseAdapter{
		private Position nyPlatsPosition;
		
		
		public void mouseClicked(MouseEvent mev){
		nyPlatsPosition = new Position(mev.getX(), mev.getY());
		
		// STÄLLA TILLBAKA MUSPEKAREN!?
		}	
		public Position getNyPlatsPosition(){
			return nyPlatsPosition;
		}
		
		private boolean mouse = false;
		int hoverX;
		int hoverY;
		
		
		public void mouseEntered(MouseEvent mev){
			hoverX = mev.getX();
			hoverY = mev.getY();
			Cursor c = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
			
			if(mapScrollbar.contains(hoverX,hoverY))
			mapScrollbar.setCursor(c);
		}
	}
	
	
	class ChoosePlaceTypeLyss implements ActionListener{
		
		TravelCategory colorToUse;
		Place nyPlats;
		MouseLyss mouseLyss = new MouseLyss();
		
		public void actionPerformed(ActionEvent ave){
			//ÄNDRA TILL CROSSHAIR Vid click på kartan, get position och använd i skapandet av en plats.
			
			
			mapScrollbar.addMouseListener(mouseLyss);			//SKA DEN ADDAS TILL MAPSCROLLBAR ELLER TILL BILDEN?		--> Ger Nullpointer om man inte laddat en bild först. fixa?!
			mapScrollbar.addMouseMotionListener(mouseLyss);
			Position nyPlatsPosition = mouseLyss.getNyPlatsPosition();	// Position ska ändras till där användaren markerar på kartan.
			
			
			/* ----------------------------------------NEDAN FUNKAR SO FAR--------------------------------------*/
			
			JPanel namedPlacePanel= new JPanel();
			JPanel describedPlacePanel = new JPanel();
	
			JPanel rad1= new JPanel();
			JPanel rad2 = new JPanel();
			
			JTextField textField = new JTextField(10);
			JTextField describeField = new JTextField(10);
			
			rad1.add(new JLabel("Platsens namn:"));
			rad1.add(textField);
			
			rad2.add(new JLabel("Description:"));
			rad2.add(describeField);
			
			String name = textField.getText();
			String description = describeField.getText();
			
			
			if(choosePlaceType.getSelectedItem().equals(("NamedPlace"))){	
				namedPlacePanel.setLayout(new BoxLayout(namedPlacePanel, BoxLayout.Y_AXIS));
				namedPlacePanel.add(rad1);
				
				String ifyllt = JOptionPane.showInputDialog(null, namedPlacePanel,  "New Named Place", JOptionPane.QUESTION_MESSAGE);			// den här ska bli frågeformuläret
				
				if(ifyllt != null){
				nyPlats = new NamedPlace(name , nyPlatsPosition);			// Denna måste sparas i en samling.	
				System.out.println((String)choosePlaceType.getSelectedItem());			//För att visa att det går!
				}else{
					return;
				}
					

				
				
			}else if(choosePlaceType.getSelectedItem().equals("DescribedPlace")){
				
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
		
		
		ImageIcon imageLabel;
		
		public void actionPerformed(ActionEvent ave){
			int ok = JFileChooser.APPROVE_OPTION;
			int jfcSvar = jfc.showOpenDialog(newMap);	// för att kunna ladda filer
			if (jfcSvar == ok){
				
				if(centerPanel.getComponents() != null){
					centerPanel.removeAll();
				}
				
			valdFil = jfc.getSelectedFile();
			longName = valdFil.getAbsolutePath();
			
			imageLabel = new ImageIcon(longName);
			mapScrollbar = new JScrollPane(new JLabel(imageLabel));		// OK, nu funkar det
			centerPanel.add(mapScrollbar);								//Men vad har jag gjort? :/		----> ImageLabel, klassen behövs inte alls nu!
			centerPanel.validate();
			mapScrollbar.validate();
			
			
//			getContentPane().add(mapScrollbar);
//			getContentPane().validate();		---> FUNKAR LIKA DÅLIGT.. 
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
	
	
//	class ImageLabel extends JPanel{			// påverkar ingenting om den extendar JPanel, JLabel lr JComponent
//		
//		private ImageIcon bakgrundsbild;
//		
//		public ImageLabel(String longName){
//			
//			setLayout(null);
//			ProgramTest.this.longName = longName;
//			bakgrundsbild = new ImageIcon(longName);				
//			System.out.println(longName);
//			//super.setBounds(getX(), getY(), bakgrundsbild.getIconWidth(),bakgrundsbild.getIconHeight());			// funkar inte utan vidare implementering..
//		}												
//		
//		
//		
//		protected void paintComponent(Graphics g){
//			super.paintComponent(g);
//			g.drawImage(bakgrundsbild.getImage(), 0, 0, this);			// ta bort getwidth + getHeight för bildensnaturliga storlek
//		}
//	}
	

	
	public static void main(String []args){
		ProgramTest program = new ProgramTest();
		
	}
}

