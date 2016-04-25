package places;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;

@SuppressWarnings("serial")
class BackgroundMap extends JLayeredPane {
	private ImageIcon map;
	
	private BackgroundMap(){
		setLayout(null);
	}
	//Factory method
	public static BackgroundMap createMap(){
		return new BackgroundMap();
	}
	public BackgroundMap(ImageIcon img) {
		setImage(img);
		setLayout(null);
	}

	public void setImage(ImageIcon img) {
		map = img;
		this.setPreferredSize(new Dimension(map.getIconWidth(), map.getIconHeight()));
	}
	public boolean hasImage(){
		return map != null;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (map != null){
			g.drawImage(map.getImage(), 0, 0, map.getIconWidth(), map.getIconHeight(), this);
		}
	}
}
