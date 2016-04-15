package places;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
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
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * TODO:
 * add fileloader logic
 * add places rendering
 * add new places logic
 * 
 */

@SuppressWarnings("serial")
public class MapApp extends JFrame {
	public static final String title = "MapApp";

	public static final String[] ALLOWED_PLACE_TYPES = { "Named", "Described" };

	private JComboBox<String> newPlaceChooser;
	private JTextField searchInput;
	private SortedList sortedList;
	private Map map;
	private JScrollPane mapPane;

	private JFileChooser fileChooser;
	private Places places;

	private boolean changed;

	public MapApp() {
		super(title);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowHandler());
		this.setJMenuBar(populateMenuBar(new JMenuBar()));
		this.add(populateMainView(new JPanel()));
		this.pack();
		this.centerFrameOnDefaultMonitor();
	}

	private void centerFrameOnDefaultMonitor() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		int frameLocationX = (int) ((width - getWidth()) / 2); // Center the
																// window on the
																// X axis
		int frameLocationY = (int) ((height - getHeight()) / 2); // Center the
																	// window on
																	// the Y
																	// axis
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
		file.add(save);

		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ExitListener());
		file.add(exit);

		return menu;
	}

	private JPanel populateMainView(JPanel mainView) {
		mainView.setLayout(new BorderLayout());

		mainView.add(populateUpperBar(new JPanel()), BorderLayout.NORTH);

		map = new Map();
		mapPane = new JScrollPane(map, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainView.add(mapPane, BorderLayout.CENTER);

		mainView.add(populatePlaceCategoryChooser(new JPanel()), BorderLayout.EAST);

		return mainView;
	}

	private JPanel populateUpperBar(JPanel upperBar) {
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.TRAILING);
		upperBar.setLayout(layout);

		JLabel newComboLabel = new JLabel("New:");
		upperBar.add(newComboLabel);

		newPlaceChooser = new JComboBox<>(ALLOWED_PLACE_TYPES);
		upperBar.add(newPlaceChooser);

		searchInput = new JTextField("Search", 10);
		upperBar.add(searchInput);

		JButton searchButton = new JButton("Search");
		upperBar.add(searchButton);

		JButton hideButton = new JButton("Hide");
		upperBar.add(hideButton);

		JButton removeButton = new JButton("Remove");
		upperBar.add(removeButton);

		JButton whatIsHereButton = new JButton("What is here?");
		upperBar.add(whatIsHereButton);

		return upperBar;
	}

	private JPanel populatePlaceCategoryChooser(JPanel placeCategoryChooser) {
		BoxLayout layout = new BoxLayout(placeCategoryChooser, BoxLayout.Y_AXIS);
		placeCategoryChooser.setLayout(layout);

		JLabel categoryLabel = new JLabel("Categories");
		categoryLabel.setAlignmentX(CENTER_ALIGNMENT);
		placeCategoryChooser.add(categoryLabel);

		sortedList = new SortedList();
		JList<String> list = new JList<>(sortedList);
		list.setFixedCellWidth(20);
		JScrollPane listScroll = new JScrollPane(list);
		listScroll.setPreferredSize(new Dimension(listScroll.getWidth(), 250));
		placeCategoryChooser.add(listScroll);

		JButton hideCategories = new JButton("Hide category");
		hideCategories.setAlignmentX(CENTER_ALIGNMENT);
		placeCategoryChooser.add(hideCategories);

		return placeCategoryChooser;
	}

	private void start() {
		this.setVisible(true);
	}

	private void exitWithoutSaving() {
		System.exit(0);
	}

	private void queryChangedExit() {
		int choice = JOptionPane.showConfirmDialog(MapApp.this, "Unsaved changes, exit anyway?", "Exit",
				JOptionPane.OK_CANCEL_OPTION);
		if (choice == JOptionPane.OK_OPTION) {
			exitWithoutSaving();
		}
	}

	private void stop() {
		if (changed) {
			queryChangedExit();
		} else {
			exitWithoutSaving();
		}

	}

	class WindowHandler extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent wev) {
			stop();
		}
	}

	class ExitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent aev) {
			stop();
		}
	}
	class PlaceMarker extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			switch(e.getButton()){
			case MouseEvent.BUTTON1:
				((Place)e.getSource()).setMarked(true);
				break;
			case MouseEvent.BUTTON2:
				((Place)e.getSource()).setMarked(false);
				break;
			}
			map.repaint();
		}
	}

	class LoadListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent aev) {
			JMenuItem selected = (JMenuItem) aev.getSource();
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
		jfc.setFileFilter(new FileNameExtensionFilter("Pictures", "png", "jpg", "jpeg"));
		if (jfc.showOpenDialog(MapApp.this) == JFileChooser.APPROVE_OPTION) {
			
			File selected = jfc.getSelectedFile();
			map.setImage(new ImageIcon(selected.getAbsolutePath()));
			
		}
	}

	private void loadNewPlaces(JFileChooser jfc) {
		jfc.setFileFilter(new FileNameExtensionFilter("Places", "places"));
		if (jfc.showOpenDialog(MapApp.this) == JFileChooser.APPROVE_OPTION) {

			File selected = jfc.getSelectedFile();
			try (Scanner sc = new Scanner(new FileReader(selected))) {
				addPlacesFromArray(new FileHandler(sc).readFileContent());
				addPlacesToMap();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(MapApp.this, "File does not exist");
			}

		}
	}

	private void addPlacesFromArray(ArrayList<String> fromFile) {
		if (places == null)
			places = new Places(new HashMap<Position, Place>(), new HashMap<String, ArrayList<Place>>());

		for (String s : fromFile) {
			String[] values = s.split(",");
			switch (values[0]) {
			case "Named":
				String name = values[4];
				Position pos = new Position(Integer.parseInt(values[2]), Integer.parseInt(values[3]));
				TravelCategory cat;
				switch (values[1]) {
				case "Buss":
					cat = TravelCategory.BUS;
					break;
				case "Tåg":
					cat = TravelCategory.TRAIN;
					break;
				case "Tunnelbana":
					cat = TravelCategory.SUBWAY;
					break;
				default:
					cat = TravelCategory.NO_CATEGORY;
				}
				NamedPlace place = new NamedPlace(name, pos, cat);
				places.add(place);
				break;
			case "Described":
				break;
			}
		}
	}
	private void addPlacesToMap(){
		if(places != null){
			for(Entry<Position, Place> s : places.getAllPlaces().entrySet()){
				Place currentPlace = s.getValue();
				map.add(currentPlace);
			}
		}
	}

	class Map extends JPanel {
		private ImageIcon map;

		public Map() {
			setLayout(null);
		}

		public void setImage(ImageIcon img) {
			map = img;
			this.setPreferredSize(new Dimension(map.getIconWidth(), map.getIconHeight()));
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (map != null){
				g.drawImage(map.getImage(), 0, 0, map.getIconWidth(), map.getIconHeight(), this);
			}
		}
	}
	

	class SortedList extends DefaultListModel<String> {
		@SuppressWarnings("unused")
		public void addSorted(String s) {
			int pos = 0;

			while (pos < getSize() && s.compareToIgnoreCase(get(pos)) > 0) {
				pos++;
			}
			add(pos, s);
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
