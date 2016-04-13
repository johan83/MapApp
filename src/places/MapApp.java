package places;

import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class MapApp extends JFrame{
	public static final String title = "MapApp";
	
	public static final String[] ALLOWED_PLACE_TYPES = {"Named","Described"};
	
	private JComboBox<String> newPlaceChooser;
	private JTextField searchInput;
	
	public MapApp(){
		super(title);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.add(createMainView(new JPanel()));
		this.pack();
	}
	
	public JPanel createMainView(JPanel panel){
		JPanel mainView = panel;
		mainView.add(createUpperBar(new JPanel()));
		return mainView;
	}
	public JPanel createUpperBar(JPanel inputPanel){
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.TRAILING);
		JPanel panel = inputPanel;
		panel.setLayout(layout);
		
		JLabel newComboLabel = new JLabel("New:");
		panel.add(newComboLabel);
		
		newPlaceChooser = new JComboBox<>(ALLOWED_PLACE_TYPES);
		panel.add(newPlaceChooser);
		
		searchInput = new JTextField("Search",10);
		panel.add(searchInput);
		
		return panel;
	}
	private void start(){
		this.setVisible(true);
	}
	private void stop(){
		this.setVisible(false);
		System.exit(0);
	}
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				MapApp mainProg = new MapApp();
				mainProg.start();
			}	
		});
	}
}
