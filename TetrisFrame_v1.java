package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TetrisFrame_v1 extends JFrame
{
    public TetrisFrame_v1(String title, final Board board) throws HeadlessException {
	super(title);
	final JTextArea textarea = new JTextArea(board.getHeight(), board.getWidth());
	textarea.setText(BoardToTextConverter.convertToText(board));
	this.setLayout(new BorderLayout());
	this.add(textarea, BorderLayout.CENTER);
	textarea.setFont(new Font("Monospaced",Font.PLAIN,20));

	final JMenuBar bar = new JMenuBar();
	final JMenu options = new JMenu("Options");

	JMenuItem quit = new JMenuItem("Quit");
	options.add(quit);
	quit.addActionListener(new QuitListener());

	bar.add(options);
	this.setJMenuBar(bar);

	this.setVisible(true);
	this.pack();

	final Action doOneStep = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) {
		board.randomBoard(board);
		textarea.setText(BoardToTextConverter.convertToText(board));
	    }
	};

	final Timer clockTimer = new Timer(1000, doOneStep);
	clockTimer.setCoalesce(true);
	clockTimer.start();
	//clockTimer.stop();
    }

    private class QuitListener implements ActionListener {
	@Override public void actionPerformed(final ActionEvent	e) {
	    int answer = JOptionPane.showConfirmDialog(new JFrame(), "Do you really want to quit?", "Quit?", JOptionPane.YES_NO_OPTION);
	    if (answer == JOptionPane.YES_OPTION) {
		System.exit(0);
	    }
	}
    }
}
