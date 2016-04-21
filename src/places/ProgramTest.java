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
	
	private JScrollPane mapScrollbar = new JScrollPane();
	
	private JLabel categoriesLabel = new JLabel("Categories");
	private String[] categories = {"Bus", "Train", "Subway"} ;
	private JList<String> categoryList = new JList<String>(categories);
	private JButton hideCategoryButton = new JButton("Hide category");

	
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
		eastPanel.add(categoryList);
		categoryList.setFixedCellWidth(150);
		hideCategoryButton.setAlignmentX(LEFT_ALIGNMENT); //Alignment
		eastPanel.add(hideCategoryButton); 
		
		centerPanel.setLayout(new FlowLayout());			//GRID LR BOXLAYOUT FIXAR STORLEKEN PÅ BILDEN MEN VAD MED SCROLLPANE?
		
		add(centerPanel, BorderLayout.CENTER);	//SE BILD    --------------------> om centerPanel byts till imgaeLabel syns kartan... vad fel? om centerPanel.setLayout != null syns bilden alltid.
		//SE BILD --------------------------> lägg till objekt av ImageLabel classen
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(650, 650);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
		
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
	
	
	class NewMapLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			int ok = jfc.APPROVE_OPTION;
			int jfcSvar = jfc.showOpenDialog(newMap);	// för att kunna ladda filer
			if (jfcSvar == ok){
				valdFil = jfc.getSelectedFile();
				longName = valdFil.getAbsolutePath();
				System.out.println(longName);
				centerPanel.add(new ImageLabel(longName));
				centerPanel.validate();
				centerPanel.repaint();
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
	
	
	class ImageLabel extends JPanel{
		
		private ImageIcon bakgrundsbild;
		
		public ImageLabel(String longName){
			setLayout(null);
			ProgramTest.this.longName = longName;
			bakgrundsbild = new ImageIcon(longName);				// absolut sökväg!? kan skriva "jarvafaltet.png" varför funkar inte longName---> pekar på null
			System.out.println("" + longName);
			centerPanel.add(mapScrollbar);
			centerPanel.validate();
			centerPanel.repaint();
			
		}												// skulle kunna göra en ny konstruktor som bugger upp fönstret igen fast med bild denna gång....
		
		
		
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(bakgrundsbild.getImage(), 0, 0,  this);			// ta bort getwidth + getHeight för bildensnaturliga storlek
		}
	}
	

	
	public static void main(String []args){
		ProgramTest program = new ProgramTest();

		
	}
}

