import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class SquareLetter extends JPanel{
	private JLabel letter;
	
	public SquareLetter() {
		super();
		this.setPreferredSize(new Dimension(50, 50));
		this.setMinimumSize(new Dimension(50, 50));
		this.setBackground(new Color(182, 182, 182));
		this.setForeground(new Color(0,0,0));
		//this.setLayout(null);
		
		CreateLetter("");
//		this.update(this.getGraphics());
	}
	
	public SquareLetter(String s) {
		super();
		this.setPreferredSize(new Dimension(50, 50));
		this.setMinimumSize(new Dimension(50, 50));
		this.setBackground(new Color(182, 182, 182));
		this.setForeground(new Color(0,0,0));
		//this.setLayout(null);
		
		CreateLetter(s);
//		this.update(this.getGraphics());
	}
	
	public SquareLetter(char c) {
		this(Character.toString(c));
	}
	
	private void CreateLetter(String s) {
		letter = new JLabel(s, SwingConstants.CENTER);
		letter.setFont(new Font("Arial",Font.BOLD,20));
		letter.setPreferredSize(new Dimension(25, 25));
		letter.setMinimumSize(new Dimension(25, 25));
		letter.setMaximumSize(new Dimension(50, 50));
		//letter.setForeground(new Color(0, 0, 0));
		this.add(letter);
	}
	
	public void UpdateLetter(String s) {
		letter.setText(s);
//		this.update(this.getGraphics());
	}
	
	public void UpdateLetter(char c) {
		letter.setText(Character.toString(c));
	}
	
	
	public void SetNeutral() {
		this.setBackground(new Color(182, 182, 182));
		this.setForeground(new Color(0,0,0));
//		this.update(this.getGraphics());
	};
	
	public void SetCorrect() {
		this.setBackground(new Color(149, 0, 255));
		this.setForeground(new Color(255,255,255));
//		this.update(this.getGraphics());
	};
	
}

class wordRow extends JPanel{
	private FlowLayout layout;
	private int length;
	private int max;
	private List<SquareLetter> slist;
	private boolean fixed_size;
	
	public wordRow() {
		super();
		fixed_size = false;
		layout = new FlowLayout();
		this.setLayout(layout);
		this.slist = new ArrayList<SquareLetter>();
		slist.add(new SquareLetter());
		this.add(slist.get(0));
		length = 0;
	}
	
	public wordRow(int blanks) {
		this();
		fixed_size = true;
		length = 0;
		max = blanks;
		for (int i = 1; i < blanks ; i++) {
			slist.add(new SquareLetter());
			this.add(slist.get(i));
		}
	}
	
	public wordRow(String word) {
		this();
		fixed_size = true;
		length = word.length();
		max = length;
		slist.get(0).UpdateLetter(word.toUpperCase().charAt(0));
		for (int i = 1; i < max; i++) {
			slist.add(new SquareLetter(word.toUpperCase().charAt(i)));
			this.add(slist.get(i));
		}
	}
	
	public void add(char c) {
		if (fixed_size) {
			if (length < max) {
				slist.get(length).UpdateLetter(Character.toString(c));
				length++;
			}
		}
		else {
			System.out.printf("[SL] %d %d\n",length,slist.size());
			if (length < slist.size()) {
				System.out.println("[SL] CAN PUT");
				slist.get(length).UpdateLetter(Character.toString(c));
			}
			else {
				System.out.println("[SL] NEED TO MAKE NEW BOX");
				slist.add(new SquareLetter(Character.toString(c)));
				this.add(slist.get(length));
			}
			length++;
			System.out.printf("[SL] POST %d %d\n",length,slist.size());
		}
	}
	
	public void delete() {
		if (length > 0) {
			if (length > 1 && !fixed_size) {
				this.remove(slist.get(length-1));
				slist.remove(length-1);
			}
			else { //special rule for final square
				slist.get(length-1).UpdateLetter("");
			}
			length--;
		}
	}
	
	public void SetString(String s) {
		for (int i = 0; i < this.length; i++) {
			this.delete();
		}
		this.length = 0;
		
		for (int i = 0; i < this.max && i < s.length(); i++) {
			this.add(s.toCharArray()[i]);
		}
	}
	
	public void Resize(int newsize) {
		this.removeAll();
		this.slist = new ArrayList<SquareLetter>();
		this.fixed_size = true;
		this.length = 0;
		max = newsize;
		for (int i = 0; i < max ; i++) {
			slist.add(new SquareLetter());
			this.add(slist.get(i));
		}
	}
}
