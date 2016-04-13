package places;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class MapApp extends JFrame {
	public static final String title = "MapApp";

	public static final String[] ALLOWED_PLACE_TYPES = { "Named", "Described" };

	private JComboBox<String> newPlaceChooser;
	private JTextField searchInput;

	private ImageIcon img = new ImageIcon("resources/jarvafaltet.png");
	
	private boolean changed;

	public MapApp() {
		super(title);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new windowHandler());
		this.add(populateMainView(new JPanel()));
		this.pack();
		this.centerFrameOnDefaultMonitor();
	}
	
	private void centerFrameOnDefaultMonitor(){
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		int frameLocationX = (int) ((width - getWidth())/2); //Center the window on the X axis
		int frameLocationY = (int) ((height - getHeight())/2); //Center the window on the Y axis		
		this.setLocation(frameLocationX,frameLocationY);
	}

	private JPanel populateMainView(JPanel mainView) {
		GridBagLayout gbl = new GridBagLayout();
		mainView.setLayout(gbl);
		GridBagConstraints cons = new GridBagConstraints();
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.fill = GridBagConstraints.BOTH;
		JScrollPane test = new JScrollPane(new Map(img));
		test.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		test.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		mainView.add(test,cons);

		cons.gridx = 0;
		cons.gridy = 0;
		cons.weightx = 0;
		cons.weighty = 0;
		cons.insets = new Insets(0,0,0,0);
		mainView.add(createUpperBar(new JPanel()),cons);
		
		return mainView;
	}

	private JPanel createUpperBar(JPanel upperBar) {
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

	private class Map extends JPanel {
		private ImageIcon map;

		public Map(ImageIcon mapIcon) {
			map = mapIcon;
			this.setPreferredSize(new Dimension(map.getIconWidth(),map.getIconHeight()));
			setLayout(null);
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(map.getImage(), 0, 0, getWidth(), getHeight(), this);
			
		}
	}

	private void start() {
		this.setVisible(true);
	}

	private void exitWithoutSaving() {
		System.exit(0);
	}

	private class windowHandler extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent wev) {
			if (changed) {
				queryChangedExit();
			} else {
				exitWithoutSaving();
			}
		}

		private void queryChangedExit() {
			int choice = JOptionPane.showConfirmDialog(MapApp.this, "Unsaved changes, exit anyway?", "Exit",
					JOptionPane.OK_CANCEL_OPTION);
			if (choice == JOptionPane.OK_OPTION) {
				exitWithoutSaving();
			}
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
