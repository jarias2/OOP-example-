// Josimar Arias
// Slot Machine
// homework 4
import java.util.ArrayList;
import java.util.Random; 
import javax.swing.JFrame; 
import javax.swing.JPanel;
import java.awt.Color; 
import javax.swing.JButton;
import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent; 
import java.awt.BorderLayout; 
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;

/**
 * The is a slot machine app / you
 * will start with a balance of $1.00
 *  press bet max to wage $.50
 *  press bet min to wage $.10
 *  press spin to wage $0.25
 */


/**
 * The JFrame descendant  houses all the user interface
 * components. 
 * @author arias
 *
 */
class SlotMachineFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Random chance; 
	// slot panels 
	private SlotPanel bleft; 
	private SlotPanel bcenter; 
	private SlotPanel bright;
	private JButton Spin; 
	private JButton max; 
	private JButton min;
	// bet min = 10, bet max = 50 spin = 25
	private double moneyremain; 
	private double minbet = 0.1; 
	private  double spin = 0.25;
	private  double maxbet = 0.5;
	// info panel
	private WinningsPanel win; 
	public SlotMachineFrame() {
		chance = new Random(); 
		// set up for the user interface 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setBounds(300,300,500,500);
		setTitle("Slot Machine Game"); 
		bleft = new SlotPanel(chance);
		bcenter = new SlotPanel(chance);
		bright = new SlotPanel(chance);
		Container j = getContentPane(); 
		j.setLayout(new BorderLayout());
		JPanel panbutton=  new JPanel(); 
		panbutton.setLayout(new GridLayout());
		panbutton.add(bleft); 
		panbutton.add(bcenter); 
		panbutton.add( bright);
		j.add(panbutton, BorderLayout.CENTER);
		Spin = new JButton("spin");
		JPanel panCenter = new JPanel(); 
		panCenter.setLayout(new FlowLayout());
		
		
		// I got help on this section from tutors 
		Spin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				turn(spin); 
			}
		}); 
		
		
		max = new JButton("bet max");
		max.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				turn(maxbet); 
			}
		}); 
		min = new JButton("bet min");
		min.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				turn(minbet); 
			}
		}); 
	
		
		
			panCenter.add(max); 
			panCenter.add(min); 
			panCenter.add(Spin);
			j.add(panCenter, BorderLayout.SOUTH); 
			win = new WinningsPanel(); 
			j.add(win,BorderLayout.NORTH);
			
			// start balance 
			moneyremain = 1.00; 
	}
		

	public void reload() {
		if(moneyremain == 0) {
			win.setMessage("You have no money left. ");
		}else {
			win.setMessage(String.format("current balance: $%.2f",moneyremain));
		}
	}
	// slot checker 
	public void turn(double wager) {
		ArrayList<Integer> colors = new ArrayList<Integer>();
		ArrayList<Integer> shapes = new ArrayList<Integer>();
		
		colors.add(bleft.getColor());
		colors.add(bright.getColor());
		colors.add(bcenter.getColor());
		
		shapes.add(bleft.getshape());
		shapes.add(bright.getshape());
		shapes.add(bcenter.getshape());
		
		if(moneyremain > 0) {
			bleft.turn(); 
			bcenter.turn(); 
			bright.turn();
			
			
			// compares the shapes and colors and adds or removes money 
			if(shapes.get(0) == shapes.get(1) && shapes.get(1) == shapes.get(2)
					&& colors.get(0) == colors.get(1) && colors.get(1) == colors.get(2)){
				
				moneyremain += wager; 
				
			}else  {
				moneyremain -= wager; 
				if (moneyremain < 0.10) {
					moneyremain = 0 ;
					// disables the buttons if no money left
					min.setEnabled(false);
					max.setEnabled(false);
					Spin.setEnabled(false);
					
				}
			}
		}else {
			moneyremain = 0 ; 
			
		}
		reload();
		/*
		 * the repaint() method controls the update paint cycle. 
		 * this method component repaints itself 
		 */
		repaint();
	}
}





/**
 * The JPanel descendant  houses all the user interface 
 * components. 
 * @author arias
 *
 */
class SlotPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// storing of color 
		private int color; 
		
	// stores the shape info of circle or rectangle 
	private int shapes; 
	
	private Random chance; 
	public SlotPanel(Random chance) {
		this.chance = chance; 
		turn();
	}
	


 // set the color to either red blue or green 
public void setColor(int color) {
	if(color < 0) {
		color = 0;
		
	}else if (color > 2) {
		color = 2; 
	}
	this.color = color; 
}
// gets the color 
public int getColor() {
	 return color; 
}

public int getshape() {
	return shapes; 
}
/**\
 * This function is to set the color of the shapes either red blue or green 
 */
public void paintComponent(Graphics s) {
	super.paintComponent(s);
	if(color == 0) {
		s.setColor(Color.BLUE);
	}else if (color == 1) {
		s.setColor(Color.RED);
	}else {
		s.setColor(Color.GREEN);
	}
	if(shapes == 0 ) {
		s.fillOval(20,20,100,100);
	}else {
		s.fillRect(20,20,100,100);
	}
}
//slot randomizer 
public void turn() {
	color = chance.nextInt(3);
	shapes = chance.nextInt(2);
}

public String toString() {
	return String.format("(%d,%d)",shapes,color); 
}
}
/**
 * WinningsPanel: the Jpanel descendent that occupies the north 
 * section of the SlotFrame 
 * @author arias
 *
 */
class WinningsPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String promt; 
	public String getMessage() {
		return promt; 
	}
	public void setMessage(String out) {
		promt = out ; 
	}
	public void paintComponent(Graphics s) {
		s.drawString(promt,50,50);
	}
	public WinningsPanel() {
		this.setPreferredSize(new Dimension(400,100));
		
		promt = "Welcome to Slot Machine"; 
	}

}

public class machine {
// create the slot machine and create it visible 
	public static void main(String[] args) {
		SlotMachineFrame frm = new SlotMachineFrame(); 
		
		frm.setVisible(true);
	}

}
