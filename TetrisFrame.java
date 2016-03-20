package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TetrisFrame extends JFrame
{
    public TetrisFrame(final Board board) throws HeadlessException {
	super("Tetris");
	final TetrisComponent gameArea = new TetrisComponent(board);
	this.setLayout(new BorderLayout());
	this.add(gameArea, BorderLayout.CENTER);

	final JLabel score = new JLabel("Score: " + board.getPoints());

	final JMenuBar menuBar = new JMenuBar();
	final JMenu options = new JMenu("Options");

	JMenuItem quit = new JMenuItem("Quit");
	options.add(quit);
	quit.addActionListener(new QuitListener());
	JMenuItem newGame = new JMenuItem("New Game");
	options.add(newGame);
	newGame.addActionListener(new NewGameListener());

	menuBar.add(options);
	this.setJMenuBar(menuBar);
	this.add(score, BorderLayout.PAGE_START);

	this.setVisible(true);
	this.pack();

	board.addBoardListener(gameArea);

	//keybindings
	class LeftAction extends AbstractAction {
	    @Override public void actionPerformed(final ActionEvent e) {
		board.moveLeft();
	    }
	}
	class RightAction extends AbstractAction {
	    @Override public void actionPerformed(final ActionEvent e) {
		board.moveRight();
	    }
	}
	class RotateAction extends AbstractAction {
	    @Override public void actionPerformed(final ActionEvent e) {
		board.rotate();
	    }
	}
	class DownAction extends AbstractAction {
	    @Override public void actionPerformed(final ActionEvent e) {
		board.moveDown();
	    }
	}
	class QuitAction extends AbstractAction {
	    @Override public void actionPerformed(final ActionEvent e) {
		int answer = JOptionPane.showConfirmDialog(new JFrame(), "Do you really want to quit?", "Quit?", JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION) {
		    System.exit(0);
		}
	    }
	}
	class NewGameAction extends AbstractAction {
	    @Override public void actionPerformed(final ActionEvent e) {
		dispose();
		new TetrisFrame(new Board(10, 20));
	    }
	}

	final InputMap in = gameArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	in.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight" );
	in.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft" );
	in.put(KeyStroke.getKeyStroke("UP"), "rotate");
	in.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
	in.put(KeyStroke.getKeyStroke("ESCAPE"), "quit");
	final ActionMap act = gameArea.getActionMap();
	act.put("moveRight", new RightAction());
	act.put("moveLeft", new LeftAction());
	act.put("rotate", new RotateAction());
	act.put("moveDown", new DownAction());
	act.put("quit", new QuitAction());

	//Do a step
	final Action doOneStep = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) {
		if (!board.getGameOver()) {
		    board.tick();
		    score.setText("Score: " + board.getPoints());
		}
		else {
		    System.out.println("hello");
		    dispose();
		    new TetrisFrame(board);
		}
	    }
	};

	final Timer clockTimer = new Timer(500, doOneStep);
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

    private class NewGameListener implements ActionListener {
	@Override public void actionPerformed(final ActionEvent e) {
	    dispose();
	    new TetrisFrame(new Board(10,20));
	}
    }
}
