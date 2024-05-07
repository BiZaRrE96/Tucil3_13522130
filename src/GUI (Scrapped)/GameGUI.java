import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.wb.swing.FocusTraversalOnArray;

public class GameGUI extends JFrame{
	JPanel mainWindow;
	JPanel wordlePanel;
	GridBagLayout wordleTiles;
	GridBagLayout mainGrid;
	private JScrollPane scrollPane;
	private JPanel mid_panel;
	private List<wordRow> midwordrow;
	private wordRow startwordrow;
	private wordRow endwordrow;
	DictionarySeeker DS = null;
    String startword, endword;
    Runtime rt = Runtime.getRuntime();
    private StringBuilder sb;
    boolean startWordConfirmed;
    private logic_block lb;
    private JPanel report_bar;
    private JLabel TimeReport;
    private JLabel StepsReport;
    private boolean waitcheck;
    private JComboBox comboBox;
    private JLabel MemReport;
    private JLabel ErrorLabel;
    private JPanel panel;
    
    enum algo {
    	UCS,
    	GBFS,
    	ASTAR
    }
    
	public GameGUI() {
		super();
		this.setFocusable(true);
		mainWindow = new JPanel();
		mainWindow.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		wordlePanel = new JPanel();
		mainGrid = new GridBagLayout();
		mainGrid.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0};
		mainGrid.columnWeights = new double[]{0.0};
		wordleTiles = new GridBagLayout();

		mainWindow.setLayout(mainGrid);
		
		wordlePanel.setMaximumSize(new Dimension(2147483647, 2147483647));
		mainGrid.setConstraints(wordlePanel, new GridBagConstraints(1,0,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,new Insets(5,5,5,5),0,0));
		GridBagConstraints gbc_wordlePanel = new GridBagConstraints();
		gbc_wordlePanel.fill = GridBagConstraints.BOTH;
		gbc_wordlePanel.insets = new Insets(15, 15, 5, 0);
		gbc_wordlePanel.gridx = 0;
		gbc_wordlePanel.gridy = 0;
		mainWindow.add(wordlePanel, gbc_wordlePanel);
		wordlePanel.setLayout(new GridLayout(0, 1, 0, 0));
		
//		start_word_panel = new JPanel();
//		wordlePanel.add(start_word_panel);
//		
		startwordrow = new wordRow();
		endwordrow = new wordRow(5);
		endwordrow.setMaximumSize(new Dimension(200, 100));
		endwordrow.setPreferredSize(new Dimension(200, 40));
		endwordrow.setMinimumSize(new Dimension(280, 40));
		FlowLayout flowLayout = (FlowLayout) endwordrow.getLayout();
		flowLayout.setVgap(25);
		midwordrow = new ArrayList<wordRow>();
		
		endwordrow.setVisible(false);
//		sword_panel.add(startwordrow);

		wordlePanel.add(startwordrow);
		startwordrow.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 50));
		
		panel = new JPanel();
		panel.setBounds(new Rectangle(0, 0, 100, 200));
		panel.setPreferredSize(new Dimension(300, 25));
		panel.setSize(new Dimension(0, 50));
		wordlePanel.add(panel);
		
		mid_panel = new JPanel();
		scrollPane = new JScrollPane(mid_panel);
		panel.add(scrollPane);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setFocusTraversalKeysEnabled(false);
		scrollPane.setFocusable(false);
		mid_panel.setLayout(new BoxLayout(mid_panel, BoxLayout.Y_AXIS));
		wordlePanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{startwordrow, scrollPane, mid_panel, endwordrow, panel}));
		wordlePanel.add(endwordrow);
		
		DisplayAtRow(0,"TEST");
        getContentPane().add(mainWindow);
        
        JButton button = new JButton("SEEK");
        button.setFocusTraversalKeysEnabled(false);
        button.setFocusPainted(false);
        button.setFocusable(false);
        button.addActionListener(new ActionListener() {
        	  @Override
        	  public void actionPerformed(ActionEvent e) {
        	    System.out.println("Button clicked!");
        	    TryStarting();
        	  }
        });
        
        report_bar = new JPanel();
        GridBagConstraints gbc_report_bar = new GridBagConstraints();
        gbc_report_bar.insets = new Insets(0, 0, 5, 0);
        gbc_report_bar.gridx = 0;
        gbc_report_bar.gridy = 1;
        mainWindow.add(report_bar, gbc_report_bar);
        report_bar.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        TimeReport = new JLabel("New label");
        report_bar.add(TimeReport);
        
        StepsReport = new JLabel("New label");
        report_bar.add(StepsReport);
        
        MemReport = new JLabel("New label");
        report_bar.add(MemReport);
        
        comboBox = new JComboBox();
        comboBox.setFocusable(false);
        comboBox.setModel(new DefaultComboBoxModel(algo.values()));
        comboBox.setPreferredSize(new Dimension(100, 20));
        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.insets = new Insets(0, 0, 5, 0);
        gbc_comboBox.gridx = 0;
        gbc_comboBox.gridy = 2;
        mainWindow.add(comboBox, gbc_comboBox);
        
        button.setMaximumSize(new Dimension(200,200));
        mainGrid.setConstraints(button,  new GridBagConstraints(2,0,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(5,5,5,5),0,0));
        GridBagConstraints gbc_button = new GridBagConstraints();
        gbc_button.insets = new Insets(5, 5, 5, 0);
        gbc_button.gridx = 0;
        gbc_button.gridy = 3;
        mainWindow.add(button, gbc_button);
        
        ErrorLabel = new JLabel("New label");
        GridBagConstraints gbc_errorLabel = new GridBagConstraints();
        gbc_errorLabel.insets = new Insets(0, 0, 5, 0);
        gbc_errorLabel.gridx = 0;
        gbc_errorLabel.gridy = 4;
        mainWindow.add(ErrorLabel, gbc_errorLabel);
        
        waitcheck = false;
        report_bar.setVisible(false);
        ErrorLabel.setVisible(false);
        //KeyListener related stuff
        startWordConfirmed = false;
        sb = new StringBuilder();
        lb = new logic_block();
        this.addKeyListener(new KeyListener() {
		    public void keyTyped(KeyEvent e) {
		    	if (!waitcheck) {
		    		if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) { //Backspace		    		
		    			if (sb.length() > 0) {
		    				if (!startWordConfirmed) {
		    					startwordrow.delete();
		    				}
		    				else {
		    					endwordrow.delete();
		    				}
		    				sb.delete(sb.length()-1,sb.length());
		    			}
		    			else if (startWordConfirmed) {
		    				startWordConfirmed = false;
		    				endwordrow.setVisible(false);
		    				sb.append(lb.startword);
		    			}
		    		}
		    		else if (e.getKeyChar() == KeyEvent.VK_ENTER) { //Enter
		    			int result;
		    			if (!startWordConfirmed) {
		    				waitcheck = true; //mutex
		    				result = lb.test_start(sb.toString().toLowerCase());
		    				if (result == 0) {
		    					startWordConfirmed = true;
		    					endwordrow.setVisible(true);
		    					System.out.println(sb.length());
		    					endwordrow.Resize(sb.length());
		    					sb.delete(0,sb.length());
		    					endwordrow.validate();
		    					endwordrow.repaint();
		    				}
		    				else {
		    					result = lb.test_end(sb.toString().toLowerCase());
		    					// call algo here
		    				}
		    				waitcheck = false;
		    			}
		    		}
		    		else { //Normal input
		    			char c = e.getKeyChar();
		    			if (Character.isAlphabetic(c)) {		    			
		    				//System.out.printf("%c\n",c);
		    				c = Character.toUpperCase(c);
		    				if (!startWordConfirmed) {
		    					startwordrow.add(c);
		    				}
		    				else {
		    					endwordrow.add(c);
		    				}
		    				sb.append(c);
		    			}
		    			
		    		}
		    		
		    		System.out.println(sb.toString());
		    		mainWindow.validate();
		    		mainWindow.repaint();
		    	}
		    }

		    public void keyPressed(KeyEvent e) {
		    	//Unused
		    }

		    public void keyReleased(KeyEvent e) {
		    	//Unused
		    }
			
		});
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(519, 376);
        setLocationRelativeTo(null);
        setVisible(true);
	}
	

	public void DisplayAtRow(int row, String string) {
		//GridbagConstraints
	}
	
	public void TryStarting() {
		System.out.println("Attempting to start");
		lb.test_end(sb.toString());
		if (lb.startword.length() == lb.endword.length() && lb.startword.length() > 0) {
			ErrorLabel.setVisible(false);
			Node retval = null;
			
			//data purposes
			rt.gc(); //Garbage collect
			long start_time = System.currentTimeMillis();
			
			long mem1 = rt.totalMemory() - rt.freeMemory();
			if (comboBox.getSelectedItem() == algo.UCS) {
				retval = lb.UCS();
			}
			else if (comboBox.getSelectedItem() == algo.GBFS){
				retval = lb.GBFS();
			}
			else if (comboBox.getSelectedItem() == algo.ASTAR) {
				retval = lb.ASTAR();
			}
			long mem2 = rt.totalMemory() - rt.freeMemory();
			
			long end_time = System.currentTimeMillis();
			MemReport.setText(String.format("Memory used : %d\n",mem2-mem1));
			StepsReport.setText(String.format("Nodes visited : %d\n",Algorithm.last_node_visits));
			TimeReport.setText(String.format("Time taken in ms : %d\n",end_time-start_time));
			
			report_bar.setVisible(true);
			
			mid_panel.removeAll();
			midwordrow.clear();
			
			if (retval == null) {
				ErrorLabel.setText("Cannot find a path...");
				ErrorLabel.setVisible(true);
			}
			else {
				for (int i = 1; i < retval.getTrail().size(); i++) {
					midwordrow.add(new wordRow(retval.getTrail().get(i)));
					mid_panel.add(midwordrow.get(i-1));
					scrollPane.setViewportView(mid_panel);
				}
			}
		}
		else {
			ErrorLabel.setText("Please re-check input!");
			ErrorLabel.setVisible(true);
		}
		mainWindow.validate();
    	mainWindow.repaint();
	}
	
	public static void main(String args[]) {
		System.out.println("Running");
		SwingUtilities.invokeLater(GameGUI::new);
	}
}
