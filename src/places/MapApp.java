package places;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

	private ImageIcon img = new ImageIcon("resources/jarvafaltet.png"); //temporary

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
		file.add(map);

		JMenuItem places = new JMenuItem("Load places");
		file.add(places);

		JMenuItem save = new JMenuItem("Save");
		file.add(save);

		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ExitListener());
		file.add(exit);

		return menu;
	}

	private JPanel populateMainView(JPanel mainView) {
		GridBagLayout gbl = new GridBagLayout();
		mainView.setLayout(gbl);
		GridBagConstraints cons = new GridBagConstraints();

		cons.gridx = 0;
		cons.gridy = 0;
		cons.weightx = 0;
		cons.weighty = 0;
		cons.anchor = GridBagConstraints.CENTER;
		cons.fill = GridBagConstraints.NONE;
		mainView.add(populateUpperBar(new JPanel()), cons);

		cons.gridx = 0;
		cons.gridy = 1;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.anchor = GridBagConstraints.CENTER;
		cons.fill = GridBagConstraints.BOTH;
		JScrollPane test = new JScrollPane(new Map(img));
		test.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		test.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		mainView.add(test, cons);

		cons.gridx = 1;
		cons.gridy = 1;
		cons.weightx = 0;
		cons.weighty = 0;
		cons.anchor = GridBagConstraints.CENTER;
		cons.fill = GridBagConstraints.NONE;
		mainView.add(populatePlaceCategoryChooser(new JPanel()), cons);

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

	private class WindowHandler extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent wev) {
			stop();
		}
	}
	private class ExitListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			stop();			
		}
	}

	private class Map extends JPanel {
		private ImageIcon map;

		public Map(ImageIcon mapIcon) {
			map = mapIcon;
			this.setPreferredSize(new Dimension(map.getIconWidth(), map.getIconHeight()));
			setLayout(null);
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(map.getImage(), 0, 0, getWidth(), getHeight(), this);

		}
	}

	private class SortedList extends DefaultListModel<String> {
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
