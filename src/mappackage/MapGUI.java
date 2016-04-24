package mappackage;

import java.awt.BorderLayout;

import javax.swing.*;

public class MapGUI extends JFrame
{
	private JMenuBar menuBar = new JMenuBar();
	private JMenu archiveMenu = new JMenu("Archive");
	private JMenuItem newMap = new JMenuItem("New Map");
	private JMenuItem loadPlaces = new JMenuItem("Load Places");
	private JMenuItem save = new JMenuItem("Save");
	private JMenuItem exit = new JMenuItem("Exit");
	
	public MapGUI()
	{
		super("Inlup2");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		archiveMenu.add(newMap);
		archiveMenu.add(loadPlaces);
		archiveMenu.add(save);
		archiveMenu.add(exit);
		
		menuBar.add(archiveMenu);
		setJMenuBar(menuBar);
		
		mainPanel.add(new PlacePanel(), BorderLayout.NORTH);
		mainPanel.add(new CategoryPanel(), BorderLayout.EAST);
		mainPanel.add(new MapPanel(), BorderLayout.SOUTH);
		
		add(mainPanel);
		
		setSize(600, 600);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JFileChooser jfc = new JFileChooser();
		
		jfc.showOpenDialog(mainPanel);
	}
	
	public class PlacePanel extends JPanel
	{
		private JComboBox newPlaceBox;
		private JTextField searchField;
		private JButton searchButton;
		private JButton hideButton;
		private JButton removeButton;
		private JButton whatIsHereButton;
		
		public PlacePanel()
		{
			JLabel newPlaceLabel = new JLabel("New:");
			add(newPlaceLabel);
			
			String[] placeTypes = {"Named", "Described"};
			newPlaceBox = new JComboBox(placeTypes);
			add(newPlaceBox);
			
			searchField = new JTextField("Search", 10);
			add(searchField);
			
			searchButton = new JButton("Search");
			add(searchButton);
			
			hideButton = new JButton("Hide");
			add(hideButton);
			
			removeButton = new JButton("Remove");
			add(removeButton);
			
			whatIsHereButton = new JButton("What is here?");
			add(whatIsHereButton);
		}
	}
	
	public class CategoryPanel extends JPanel
	{
		private JLabel categoriesLabel = new JLabel("Categories");
		private JList categoryList;
		private JButton hideCategoryButton = new JButton("Hide category");
		
		public CategoryPanel()
		{
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			String[] cats = {"Buss", "Tunnelbana", "Tåg	"};
			categoryList = new JList(cats);
			categoryList.setFixedCellWidth(100);
			
			categoryList.setAlignmentX(LEFT_ALIGNMENT);
			hideCategoryButton.setAlignmentX(LEFT_ALIGNMENT);
			
			add(categoriesLabel);
			add(categoryList);
			add(hideCategoryButton);
		}
	}
	
	public class MapPanel extends JPanel
	{
		private JScrollPane mapScrollbar = new JScrollPane();
		private ImageIcon mapImage;
		
		public MapPanel()
		{
			setLayout(null);
			add(mapScrollbar);
		}
	}
}