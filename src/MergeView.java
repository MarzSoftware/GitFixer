
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;

public class MergeView implements ActionListener{

	public JFrame frame;
	private JTextArea headTxtArea;
	private JTextArea tailTxtArea;
	private JButton left;
	private JButton right;
	private JMenuItem load;
	private JLabel lblConflict;
	
	private MergeViewListener delegate;
	private GitConflict currentConflict;
	
	/**
	 * Create the application.
	 */
	public MergeView(MergeViewListener delegate) {
		this.delegate = delegate;
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
				
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		
		headTxtArea = new JTextArea();
		headTxtArea.setLineWrap(true);
		headTxtArea.setText("Head");
		panel.add(new JScrollPane(headTxtArea), BorderLayout.WEST);
		
		tailTxtArea = new JTextArea();
		tailTxtArea.setText("Tail");
		tailTxtArea.setLineWrap(true);
		panel.add(new JScrollPane(tailTxtArea), BorderLayout.EAST);
		
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1,2));
		left = new JButton("Keep Head");
		right = new JButton("Keep Tail");
		buttonPanel.add(left);
		buttonPanel.add(right);
		left.addActionListener(this);
		right.addActionListener(this);
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		load = new JMenuItem("Load");
		load.addActionListener(this);
		mnFile.add(load);
		
		lblConflict = new JLabel("");
		menuBar.add(lblConflict);
	}

	public void displayConflict(final GitConflict conflict) {
		currentConflict = conflict;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				headTxtArea.setText(conflict.getHead());	
				tailTxtArea.setText(conflict.getTail());
				lblConflict.setText("commit " + conflict.getCommitID());
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == left) {
			delegate.keepHead(currentConflict);
		} else if (e.getSource() == right) {
			delegate.keepTail(currentConflict);
		} else if (e.getSource() == load) {
			JFileChooser chooser = new JFileChooser();
		    int returnVal = chooser.showOpenDialog(frame);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	delegate.loadFile(chooser.getSelectedFile().getAbsolutePath());
		    }
		}
		
	}

}
