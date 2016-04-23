package places;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import places.Place.PlaceType;

/*
 * TODO:
 * make category object so less conversions and interpretations need to be done
 */

@SuppressWarnings("serial")
public class MapApp extends JFrame {
	public static final String title = "MapApp";
	private static final int WHAT_IS_HERE_GRID_SIZE = 21;
	
	PlaceFactoryImp placeFactory;

	private JComboBox<PlaceType> newPlaceChooser;
	private JTextField searchInput;
	private SortedList sortedList;
	private Map map;
	private JScrollPane mapPane;
	private JList<Category> list;
	
	private WhatIsHereListener WISListener;
	private NewPlaceListener comboListener;
	
	private FileNameExtensionFilter pictureFilter = new FileNameExtensionFilter("Pictures", "png", "jpg", "jpeg");
	private FileNameExtensionFilter placeFilter = new FileNameExtensionFilter("Places", "places");

	private JFileChooser fileChooser;
	private Places places;

	private boolean changed;

	public MapApp() {
		super(title);
		placeFactory = PlaceFactory.createFactory();
		this.createCategories();
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowHandler());
		this.setJMenuBar(populateMenuBar(new JMenuBar()));
		this.add(populateMainView(new JPanel()));
		this.pack();
		this.centerFrameOnDefaultMonitor();
	}
	
	private void createCategories(){
		Category.createCategory("Buss", Color.RED);
		Category.createCategory("Tunnelbana", Color.BLUE);
		Category.createCategory("Tåg", Color.GREEN);
		Category.createCategory("None", Color.BLACK);
	}

	private void centerFrameOnDefaultMonitor() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int displayWidth = gd.getDisplayMode().getWidth();
		int displayHeight = gd.getDisplayMode().getHeight();
		int frameLocationX = (int) (displayWidth - getWidth()) /2; // Center the window on the X axis
		int frameLocationY = (int) (displayHeight - getHeight()) /2; // Center the window on the Y axis
		this.setLocation(frameLocationX, frameLocationY);
	}

	private JMenuBar populateMenuBar(JMenuBar menu) {
		JMenu file = new JMenu("File");
		menu.add(file);

		JMenuItem map = new JMenuItem("New map");
		map.addActionListener(new LoadListener());
		file.add(map);

		JMenuItem places = new JMenuItem("Load places");
		places.addActionListener(new LoadListener());
		file.add(places);

		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new SaveListener());
		file.add(save);

		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ExitListener());
		file.add(exit);

		return menu;
	}

	private JPanel populateMainView(JPanel mainView) {
		mainView.setLayout(new BorderLayout());

		mainView.add(populateUpperBar(new JPanel()), BorderLayout.NORTH);

		map = Map.createMap();
		map.addMouseListener(new WhatIsHereMapListener());
		map.addMouseListener(new NewPlaceMapListener());
		
		mapPane = new JScrollPane(map, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainView.add(mapPane, BorderLayout.CENTER);

		mainView.add(populatePlaceCategoryChooser(new JPanel()), BorderLayout.EAST);

		return mainView;
	}
	class NewPlaceMapListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e){
			if(!comboListener.isActive())
				return;
			comboListener.deActivate();
			if(!map.hasImage())
				return;
			if(places == null)
				places = Places.createPlaces();
			
			Position pos = Position.createPosition(e.getX(),e.getY());
			Dimension mapSize = map.getPreferredSize();
			if(mapSize.width < pos.getX() || mapSize.height < pos.getY())
				return;
			
			Category cat = Category.getCategoryInstance("None");
			if(list.getSelectedValue() != null)
				cat = list.getSelectedValue();
			
			PlaceType type = (PlaceType) newPlaceChooser.getSelectedItem();
			Place place = placeFactory.createQueriedPlace(type, MapApp.this, pos, cat);
			if(place != null){
				addPlace(place);
				changed = true;
			}
			map.repaint();
		}
	}
	private void addPlace(Place p){
		places.add(p);
		map.add(p);
	}
	class WhatIsHereMapListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e){
			if(!WISListener.isActive())
				return;
			WISListener.deActivate();
			if(places == null)
				return;
			
			//this will search for pixels outside of the map border but nothing _should_ be there anyway
			int gridsize = WHAT_IS_HERE_GRID_SIZE;
			int startX = (int) (e.getX() - Math.ceil(gridsize/2));
			int endX = startX + gridsize;
			int startY = (int) (e.getY() - Math.ceil(gridsize/2));
			int endY = startY + gridsize;
			
			for(int x = startX; x<=endX;x++){
				for(int y = startY; y<=endY;y++)
					places.setVisibilityByPosition(Position.createPosition(x,y));				
			}
		}
	}

	private JPanel populateUpperBar(JPanel upperBar) {
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.TRAILING);
		upperBar.setLayout(layout);

		JLabel newComboLabel = new JLabel("New:");
		upperBar.add(newComboLabel);

		newPlaceChooser = new JComboBox<>(PlaceType.values());
		comboListener = new NewPlaceListener();
		newPlaceChooser.addActionListener(comboListener);
		upperBar.add(newPlaceChooser);

		searchInput = new JTextField("Search", 10);
		searchInput.addFocusListener(new SearchTextListener());
		upperBar.add(searchInput);

		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new SearchPlaceListener());
		upperBar.add(searchButton);

		JButton hideButton = new JButton("Hide");
		hideButton.addActionListener(new HideListener());
		upperBar.add(hideButton);

		JButton removeButton = new JButton("Remove");
		removeButton.addActionListener(new RemoveListener());
		upperBar.add(removeButton);

		JButton whatIsHereButton = new JButton("What is here?");
		WISListener = new WhatIsHereListener();
		whatIsHereButton.addActionListener(WISListener);
		upperBar.add(whatIsHereButton);

		return upperBar;
	}
	class NewPlaceListener implements ActionListener{
		boolean active;
		@Override
		public void actionPerformed(ActionEvent ae) {
			if(!map.hasImage())
				return;
			
			active = true;
			map.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}
		public boolean isActive(){
			return active;
		}
		public void deActivate(){
			active = false;
			map.setCursor(Cursor.getDefaultCursor());
		}
	}
	class WhatIsHereListener implements ActionListener{
		boolean active;
		@Override
		public void actionPerformed(ActionEvent ae) {
			active = true;
			MapApp.this.map.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}
		public boolean isActive(){
			return active;
		}
		public void deActivate(){
			active = false;
			MapApp.this.map.setCursor(Cursor.getDefaultCursor());
		}
	}
	class SearchPlaceListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent ae) {
			if(places == null)
				return;
			
			places.unMarkAll();
			ArrayList<Place> placesByName = places.getPlacesByName(searchInput.getText());
			if(placesByName == null)
				return;
			
			for(Place p : placesByName){
				places.setMarked(p,true);
				p.setVisible(true);
			}
			map.repaint();
		}
	}
	class SearchTextListener implements FocusListener{
		@Override
		public void focusGained(FocusEvent fe) {
			String currentText = ((JTextField)fe.getComponent()).getText();
			if(currentText.equals("Search"))
				((JTextField)fe.getComponent()).setText("");
		}
		@Override
		public void focusLost(FocusEvent fe) {
			String currentText = ((JTextField)fe.getComponent()).getText();
			if(currentText == null || currentText.isEmpty())	
				((JTextField)fe.getComponent()).setText("Search");				
		}
	}
	class HideListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			hideMarked();
			clearMarked();
		}
	}
	class RemoveListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Place[] toRemove = removeMarked();
			if(toRemove == null)
				return;
			for(Place p : toRemove)
				map.remove(p);
			
			map.repaint();
			changed = true;
		}
	}
	private void clearMarked(){
		if(places != null)
			places.unMarkAll();
	}
	private Place[] removeMarked(){
		if(places!=null)
			return places.removeMarked();
		return null;
	}
	private void hideMarked(){
		if(places!=null)
			places.hideMarked();		
	}
	private JPanel populatePlaceCategoryChooser(JPanel placeCategoryChooser) {
		BoxLayout layout = new BoxLayout(placeCategoryChooser, BoxLayout.Y_AXIS);
		placeCategoryChooser.setLayout(layout);

		JLabel categoryLabel = new JLabel("Categories");
		categoryLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		placeCategoryChooser.add(categoryLabel);

		sortedList = new SortedList();
		list = new JList<>(sortedList);
		list.addListSelectionListener(new CategoryListListener());
		populateListWithCategories(sortedList);
		
		list.setFixedCellWidth(20);
		JScrollPane listScroll = new JScrollPane(list);
		listScroll.setPreferredSize(new Dimension(listScroll.getWidth(), 250));
		placeCategoryChooser.add(listScroll);

		JButton hideCategories = new JButton("Hide category");
		hideCategories.addActionListener(new HideCategoryListener());
		hideCategories.setAlignmentX(JButton.CENTER_ALIGNMENT);
		placeCategoryChooser.add(hideCategories);

		return placeCategoryChooser;
	}
	class CategoryListListener implements ListSelectionListener{
		@SuppressWarnings("unchecked")
		@Override
		public void valueChanged(ListSelectionEvent lse) {
			if(places == null)
				return;
			if(lse.getValueIsAdjusting() != false)
				return;
			
			for(Category s : ((JList<Category>)lse.getSource()).getSelectedValuesList())
				places.setVisibleByCategory(s, true);	
		}
	}
	private void populateListWithCategories(SortedList list){
		for(Entry<String, Category> c : Category.getCurrentTypes())
			 list.addSorted(c.getValue());
	}
	class HideCategoryListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(places == null)
				return;
			for(Category cat : list.getSelectedValuesList())
				places.setVisibleByCategory(cat, false);					
		}
	}
	class SaveListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent aev) {
			if(places == null)
				return;
			fileChooser.setFileFilter(placeFilter);
			int choice = fileChooser.showSaveDialog(MapApp.this);
			if(choice == JFileChooser.APPROVE_OPTION)
				savePlaces(fileChooser.getSelectedFile());		
		}
	}
	private void savePlaces(File file){ 
		if(file.exists())
			file.delete();
		
		try(PrintWriter pw = new PrintWriter(new FileWriter(file,true))){
			FileHandler.writePlacesToFile(places.getAllPlaces().entrySet(), pw);
			changed = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void start() {
		this.setVisible(true);
	}

	private void exitWithoutSaving() {
		System.exit(0);
	}
	private int showMessage(String message, String title){
		return JOptionPane.showConfirmDialog(this, message, title, JOptionPane.OK_CANCEL_OPTION);
	}

	private void queryChangedExit() {
		int choice = showMessage("Unsaved changes, exit anyway?","Exit");
		if (choice == JOptionPane.OK_OPTION) {
			exitWithoutSaving();
		}
	}

	private void checkedStop() {
		if (changed)
			queryChangedExit();
		else 
			exitWithoutSaving();
	}

	class WindowHandler extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent wev) {
			checkedStop();
		}
	}

	class ExitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent aev) {
			checkedStop();
		}
	}

	class LoadListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent aev) {
			JMenuItem selected = (JMenuItem) aev.getSource();
			if(changed && showMessage("Unsaved changes, load new anyway?","Load") != JOptionPane.OK_OPTION)
				return;
			load(selected);
		}
	}

	private void load(JMenuItem selected) {
		if (fileChooser == null)
			fileChooser = new JFileChooser(".");
		switch (selected.getText()) {
		case "New map":
			loadNewMap(fileChooser);
			break;
		case "Load places":
			loadNewPlaces(fileChooser);
			break;
		}
		map.repaint();
		mapPane.revalidate();
	}

	private void loadNewMap(JFileChooser jfc) {
		jfc.setFileFilter(pictureFilter);
		if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File selected = jfc.getSelectedFile();
			map.setImage(new ImageIcon(selected.getAbsolutePath()));
		}
	}

	private void loadNewPlaces(JFileChooser jfc) {
		jfc.setFileFilter(placeFilter);
		if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

			removeAllPlaces();
			File selected = jfc.getSelectedFile();
			try (Scanner sc = new Scanner(new FileReader(selected))) {
				addPlacesFromArray(FileHandler.readFileContent(sc));
				changed = false;
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(this, "File does not exist");
			}
		}
	}
	private void removeAllPlaces(){
		places = Places.createPlaces();
		map.removeAll();
	}

	private void addPlacesFromArray(ArrayList<String> fromFile) {
		for (String s : fromFile) {
			try{
				String[] placeValues = s.split(",");
				
				String name = placeValues[4];
				Position pos = Position.createPosition(Integer.parseInt(placeValues[2]), Integer.parseInt(placeValues[3]));
				Category cat;
				cat = Category.getCategoryInstance(placeValues[1]);
				
				Place place = null;
				switch (placeValues[0]) {
				case "Named":
					place = placeFactory.createSafeNamedPlace(name, pos, cat);
					break;
				case "Described":
					String description = placeValues[5];
					place = placeFactory.createSafeDescribedPlace(name, pos, cat, description);			
					break;
				}
				if(place != null)
					addPlace(place);
			}catch(NumberFormatException nfe){
				System.out.println("Position value not an int\n" + nfe.getMessage());
			}catch(IllegalArgumentException iae){
				System.out.println("Category not allowed\n" + iae.getMessage());
			}catch(NullPointerException npe){
				System.out.println("Category cannot be null\n" + npe.getMessage());
			}
		}
	}

	class SortedList extends DefaultListModel<Category> {
		public void addSorted(Category cat) {
			int pos = 0;			
			while (pos < getSize() && cat.toString().compareToIgnoreCase(get(pos).toString()) > 0)
				pos++;
			
			add(pos, cat);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MapApp mainProg = new MapApp();
				mainProg.start();
			}
		});
	}

}
