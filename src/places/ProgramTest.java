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
	private ImageArea imageArea;
	private ImageIcon image;
	private String name;
	private String description;
	
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
	private TravelCategory choosePlaceCategory(){
		
		if(!(categoryList.isSelectionEmpty())){
			String color = (String)categoryList.getSelectedValue();
			
			for(TravelCategory c : TravelCategory.values()){
				if(color.equalsIgnoreCase(c.name())){				
				return c;	
				}
			}
		}
		return null;
	}
	
	private String createNamedPlace(){
		JPanel namedPlacePanel= new JPanel();
		
		namedPlacePanel.setLayout(new BoxLayout(namedPlacePanel, BoxLayout.Y_AXIS));
		namedPlacePanel.add(new JLabel("Platsens namn:"));
		
		
		String ifyllt = JOptionPane.showInputDialog(null, namedPlacePanel,  "New Named Place", JOptionPane.QUESTION_MESSAGE);
		if(ifyllt.length()> 0){
			return ifyllt;
		}
		return null;
	}
	
	private String[] createDescribedPlace(){
		JPanel describedPlacePanel = new JPanel();
		
		JPanel rad1= new JPanel();
		JPanel rad2 = new JPanel();
		
		JTextField nameField = new JTextField(10);
		JTextField describeField = new JTextField(10);
		
		rad1.add(new JLabel("Platsens namn:"));
		rad1.add(nameField);
		
		rad2.add(new JLabel("Description:"));
		rad2.add(describeField);
		
		describedPlacePanel.setLayout(new BoxLayout(describedPlacePanel, BoxLayout.Y_AXIS));
		describedPlacePanel.add(rad1);
		describedPlacePanel.add(rad2);
		
		if(JOptionPane.showConfirmDialog(null, describedPlacePanel,  "New Described Place", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION){
			
			
			String name = nameField.getText();
			String description = describeField.getText();
			
			if(name.length()>0 && description.length()>0){
				String[] nameAndDescription = {name, description};
				return nameAndDescription;
			}
		}
		return null;
	}
	
	
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
	
	class MouseLyss extends MouseAdapter{
		int clickX;
		int clickY;
		Position pos;
		Cursor c;
		
		public void mouseEntered(MouseEvent mev){
			c = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
			mapScrollbar.setCursor(c);
			
		}
		
		public void mouseClicked(MouseEvent mev){
			Place nyPlats;
			
			if(name != null){
				Position nyPlatsPosition = new Position(mev.getX(),  mev.getY());	
				System.out.println(mev.getX());
				
				if(nyPlatsPosition != null){
					System.out.println(nyPlatsPosition.getX());
//-----------------------------------Bestäm huruvida du ska göra NamedPlace lr DescribedPlace-----------------------------------------------------------------
					
					if(choosePlaceType.getSelectedItem().equals(("NamedPlace"))){	
						nyPlats = new NamedPlace(name , nyPlatsPosition);	
						nyPlats.setCategory(choosePlaceCategory());	
						
						register.addPlace(nyPlats);				
						imageArea.add(nyPlats);		
						
						nyPlats.repaint();

						System.out.println(nyPlats);
						System.out.println((String)choosePlaceType.getSelectedItem());			//För att visa att det går!
						
					}
					else if(choosePlaceType.getSelectedItem().equals("DescribedPlace")){
						nyPlats = new DescribedPlace(name , nyPlatsPosition, description);	
						nyPlats.setCategory(choosePlaceCategory());
						
						
						register.addPlace(nyPlats);				
						imageArea.add(nyPlats);		
						
						nyPlats.repaint();

						System.out.println(nyPlats);
						System.out.println((String)choosePlaceType.getSelectedItem());			//För att visa att det går!
					}
				}
			}
			else{
				System.out.println("Tom sträng"); // TEST ---> Funkar
				return;
			}
		}
		
		public void mouseExited(MouseEvent mev){
			c = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
			mapScrollbar.setCursor(c);
		}
		
		public Position getClick(){
			pos = new Position (clickX, clickY);
			return pos;
		}
	}
	
	
	class ChoosePlaceTypeLyss implements ActionListener{
		
		public void actionPerformed(ActionEvent ave){
			mouseLyss = new MouseLyss();
			imageArea.addMouseListener(mouseLyss);			
			
			
			if(choosePlaceType.getSelectedItem().equals(("NamedPlace"))){	
				name = createNamedPlace();				
			}
			else if(choosePlaceType.getSelectedItem().equals("DescribedPlace")){
				String[] nameAndDescription = createDescribedPlace();
				if(nameAndDescription != null && nameAndDescription.length>0){
					name = nameAndDescription[0];
					description = nameAndDescription[1];
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
			imageArea.setSize(image.getIconWidth(),image.getIconHeight());
			imageArea.setPreferredSize(new Dimension(image.getIconWidth(),image.getIconHeight())); //kan skapa platser utanför bilden.. :( 
			mapScrollbar = new JScrollPane(imageArea);
			centerPanel.add(mapScrollbar);								
			centerPanel.validate();			
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

