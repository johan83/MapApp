package mappackage;

import javax.swing.*;

public class MapGUI extends JFrame
{
	private JLabel newPlaceLabel = new JLabel("New:");
	
	private JTextField searchField = new JTextField("Search");
	
	private JButton searchButton = new JButton("Search");
	private JButton hideButton = new JButton("Hide");
	private JButton removeButton = new JButton("Remove");
	private JButton whatIsHereButton = new JButton("What is here?");
	
	private JScrollPane mapScrollbar = new JScrollPane();
	
	private JLabel categoriesLabel = new JLabel("Categories");
	
	private JList categoryList = new JList();
	
	private JButton hideCategoryButton = new JButton("Hide category");

}
