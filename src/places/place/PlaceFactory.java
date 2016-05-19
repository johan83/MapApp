package places.place;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import places.Category;
import places.Position;
import places.place.Place.PlaceType;

public class PlaceFactory{
	public static Place createSafePlace(PlaceType type, PlaceData data){
		switch(type){
		case Named:
			return NamedPlace.createSafeNamedPlace(data.getName(), data.getPosition(), data.getCat());
		case Described:
			return DescribedPlace.createSafeDescribedPlace(data.getName(), data.getPosition(), data.getCat(), data.getDescription());
		default:
			return null;
		}
	}
	public static Place createQueriedPlace(PlaceType type, Component parent, PlaceData data){
		switch(type){
		case Named:
			return createQueriedNamedPlace(parent,data.getPosition(),data.getCat());
		case Described:
			return createQueriedDescribedPlace(parent,data.getPosition(),data.getCat());
		default:
			return null;
		}
		
	}
	private static NamedPlace createQueriedNamedPlace(Component parent, Position pos, Category cat){
		NamedPlace place = null;
		try{			
			List<JLabel> labels = getStandardLabels();
			List<JTextField> fields = getStandardFields();
			
			JPanel panel = populateNewPlacePanel(new JPanel(), labels, fields);
			
			int choice = JOptionPane.showConfirmDialog(parent, panel, "New named place",JOptionPane.OK_CANCEL_OPTION);
			if(choice == JOptionPane.OK_OPTION){
				place = NamedPlace.createSafeNamedPlace(fields.get(0).getText(), pos, cat);
			}
		}catch(IllegalArgumentException e){
			System.out.println("Wrong arguments supplied\n" + e.getMessage());	
		}
		return place;		
	}
	private static DescribedPlace createQueriedDescribedPlace(Component parent, Position pos, Category cat){
		DescribedPlace place = null;
		try{			
			List<JLabel> labels = getStandardLabels();
			labels.add(new JLabel("Description"));
			List<JTextField> fields = getStandardFields();
			fields.add(new JTextField(10));
			
			JPanel panel = populateNewPlacePanel(new JPanel(),labels,fields);
			
			int choice = JOptionPane.showConfirmDialog(parent, panel, "New described place",JOptionPane.OK_CANCEL_OPTION);
			if(choice == JOptionPane.OK_OPTION){
				place = DescribedPlace.createSafeDescribedPlace(fields.get(0).getText(), pos, cat, fields.get(1).getText());
			}
		}catch(IllegalArgumentException e){
			System.out.println("Wrong arguments supplied\n" + e.getMessage());			
		}
		return place;		
	}
	//Allows change of standard input for places
	private static List<JLabel> getStandardLabels(){
		List<JLabel> labels = new ArrayList<>();
		labels.add(new JLabel("Name:"));		
		return labels;
	}
	private static List<JTextField> getStandardFields(){
		List<JTextField> fields = new ArrayList<>();
		fields.add(new JTextField(10));
		return fields;
	}
	private static JPanel populateNewPlacePanel(JPanel panel,List<JLabel> labels, List<JTextField> fields){
		if(labels.size() != fields.size())
			throw new IllegalArgumentException("Labels, fields arrays must be same length");
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		cons.weightx = 1; 
		cons.anchor = GridBagConstraints.EAST;
		for(int i = 0; i<labels.size();i++){
			cons.gridy = i;
			cons.gridx = 0;
			panel.add(labels.get(i), cons);
			cons.gridx = 1;
			panel.add(fields.get(i), cons);
		}
		
		return panel;				
	}
}
