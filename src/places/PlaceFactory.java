package places;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlaceFactory {
	
	public static NamedPlace queryNewNamedPlace(Component parent, Position pos, TravelCategory cat){
		NamedPlace place = null;
		try{
			JLabel[] labels = {new JLabel("Name:")};
			JTextField nameField = new JTextField(10);
			JTextField[] fields = {nameField};
			
			JPanel panel = populateNewPlacePanel(new JPanel(),labels,fields);
			
			int choice = JOptionPane.showConfirmDialog(parent, panel, "New named place",JOptionPane.OK_CANCEL_OPTION);
			if(choice == JOptionPane.OK_OPTION){
				place = new NamedPlace(nameField.getText(), pos, cat);
			}
		}catch(IllegalArgumentException e){
			e.printStackTrace();			
		}
		
		return place;		
	}
	public static DescribedPlace queryNewDescribedPlace(Component parent, Position pos, TravelCategory cat){
		DescribedPlace place = null;
		try{
			JLabel[] labels = {new JLabel("Name:"),new JLabel("Description:")};
			
			JTextField nameField = new JTextField(10);
			JTextField descField = new JTextField(10);
			JTextField[] fields = {nameField,descField};
			
			JPanel panel = populateNewPlacePanel(new JPanel(),labels,fields);
			
			int choice = JOptionPane.showConfirmDialog(parent, panel, "New described place",JOptionPane.OK_CANCEL_OPTION);
			if(choice == JOptionPane.OK_OPTION){
				place = new DescribedPlace(nameField.getText(), pos, cat, descField.getText());
			}
		}catch(IllegalArgumentException e){
			e.printStackTrace();			
		}
		
		return place;		
	}
	private static JPanel populateNewPlacePanel(JPanel panel,JLabel[] labels, JTextField[] fields){
		if(labels.length != fields.length)
			throw new IllegalArgumentException("Labels, fields arrays must be same length\n");
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		cons.weightx = 1; 
		cons.anchor = GridBagConstraints.EAST;
		for(int i = 0; i<labels.length;i++){
			cons.gridy = i;
			cons.gridx = 0;
			panel.add(labels[i], cons);
			cons.gridx = 1;
			panel.add(fields[i], cons);
		}
		
		return panel;				
	}
//	public Place(String name, Position position, TravelCategory color,String type) {
//		sizeX = 25;
//		sizeY = 30;
//		font = new Font("TimesRoman", Font.PLAIN, 18);
//		this.name = name;
//		this.position = position;
//		this.color = color;
//		this.type = type;
//		this.setBounds(position.getX() - sizeX / 2, position.getY() - sizeY, sizeX, sizeY);
//		this.setPreferredSize(new Dimension(sizeX, sizeY));
//		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//		this.addMouseListener(new PlaceMarker());
//		setVisible(true);
//	}
//	public DescribedPlace(String name, Position position, TravelCategory color, String description){
//		super(name, position, color,"Described");
//		this.description = description;
//	}
//	public NamedPlace(String name, Position position, TravelCategory color){
//		super(name, position, color,"Named");
//		
//	}
}
