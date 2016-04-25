package places;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import places.Place.PlaceType;

public class PlaceFactory{
	
	public static PlaceFactory createFactory(){
		return new PlaceFactory();
	}
	public static NamedPlace createSafeNamedPlace(String name, Position pos, Category cat){
		NamedPlace place = null;
		
		try{
			place = new NamedPlace(name,pos,cat);
		}catch(IllegalArgumentException e){
			e.printStackTrace();			
		}catch(NullPointerException e){
			e.printStackTrace();			
		}
		
		return place;
	}
	
	public static DescribedPlace createSafeDescribedPlace(String name, Position pos, Category cat, String desc){
		DescribedPlace place = null;
		
		try{
			place = new DescribedPlace(name,pos,cat,desc);
		}catch(IllegalArgumentException e){
			System.out.println("Wrong arguments supplied\n" + e.getMessage());			
		}catch(NullPointerException e){
			System.out.println("Null argument\n" + e.getMessage());				
		}
		
		return place;
	}
	public static Place createQueriedPlace(PlaceType type, Component parent, Position pos, Category cat){
		switch(type){
		case Named:
			return createQueriedNamedPlace(parent,pos,cat);
		case Described:
			return createQueriedDescribedPlace(parent,pos,cat);
		default:
			return null;
		}
		
	}
	private static NamedPlace createQueriedNamedPlace(Component parent, Position pos, Category cat){
		NamedPlace place = null;
		try{			
			ArrayList<JLabel> labels = getStandardLabels();
			ArrayList<JTextField> fields = getStandardFields();
			
			JPanel panel = populateNewPlacePanel(new JPanel(), labels, fields);
			
			int choice = JOptionPane.showConfirmDialog(parent, panel, "New named place",JOptionPane.OK_CANCEL_OPTION);
			if(choice == JOptionPane.OK_OPTION){
				place = new NamedPlace(fields.get(0).getText(), pos, cat);
			}
		}catch(IllegalArgumentException e){
			System.out.println("Wrong arguments supplied\n" + e.getMessage());	
		}
		return place;		
	}
	private static DescribedPlace createQueriedDescribedPlace(Component parent, Position pos, Category cat){
		DescribedPlace place = null;
		try{			
			ArrayList<JLabel> labels = getStandardLabels();
			labels.add(new JLabel("Description"));
			ArrayList<JTextField> fields = getStandardFields();
			fields.add(new JTextField(10));
			
			JPanel panel = populateNewPlacePanel(new JPanel(),labels,fields);
			
			int choice = JOptionPane.showConfirmDialog(parent, panel, "New described place",JOptionPane.OK_CANCEL_OPTION);
			if(choice == JOptionPane.OK_OPTION){
				place = new DescribedPlace(fields.get(0).getText(), pos, cat, fields.get(1).getText());
			}
		}catch(IllegalArgumentException e){
			System.out.println("Wrong arguments supplied\n" + e.getMessage());			
		}
		return place;		
	}
	private static ArrayList<JLabel> getStandardLabels(){
		ArrayList<JLabel> labels = new ArrayList<>();
		labels.add(new JLabel("Name:"));		
		return labels;
	}
	private static ArrayList<JTextField> getStandardFields(){
		ArrayList<JTextField> fields = new ArrayList<>();
		fields.add(new JTextField(10));
		return fields;
	}
	private static JPanel populateNewPlacePanel(JPanel panel,ArrayList<JLabel> labels, ArrayList<JTextField> fields){
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
