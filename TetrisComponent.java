package Tetris;

import javax.swing.*;
import java.awt.*;

public class TetrisComponent extends JComponent implements BoardListener
{
    private Board board;
    private final static int SQUARE_WIDTH = 30;
    private final static int MARGIN = 0;

    public TetrisComponent(Board board) {
	this.board = board;
	this.setPreferredSize(getPreferredSize());
    }

    //Rätt???
    public Dimension getPreferredSize() {
	Dimension size =
		new Dimension(SQUARE_WIDTH*board.getHeight() + (board.getHeight()-1)*MARGIN,
			      SQUARE_WIDTH*board.getWidth() + (board.getWidth()-1)*MARGIN);
	return size;
    }

    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	g2d.setBackground(Color.BLACK);

	for (int row = 0; row < board.getHeight(); row++) {
	    for (int column = 0; column < board.getWidth(); column++) {
		if (board.getFalling() != null) {
		    if ((row >= board.getFallingX() &&
			row <= board.getFallingX() + board.getFalling().getWidth() - 1 &&
			column >= board.getFallingY() &&
			column <= board.getFallingY() + board.getFalling().getHeight() - 1) &&
			( board.getFalling().getPolyType(row - board.getFallingX(), column - board.getFallingY()) != SquareType.EMPTY ) ) {

			Rectangle rect =
				new Rectangle(row * SQUARE_WIDTH + MARGIN * row, column * SQUARE_WIDTH + MARGIN * column, SQUARE_WIDTH, SQUARE_WIDTH);

			g2d.setPaint(squareColor(board.getFalling().getPolyType(row - board.getFallingX(), column - board.getFallingY())));
			g2d.fill(rect);
		    }
		    else {
			Rectangle rect =
				new Rectangle(row * SQUARE_WIDTH + MARGIN * row, column * SQUARE_WIDTH + MARGIN * column, SQUARE_WIDTH, SQUARE_WIDTH);

			g2d.setPaint(squareColor(board.getType(row, column)));
			g2d.fill(rect);
		    }
		}
		else {
		    Rectangle rect =
			    new Rectangle(row * SQUARE_WIDTH + MARGIN * row, column * SQUARE_WIDTH + MARGIN * column, SQUARE_WIDTH, SQUARE_WIDTH);

		    g2d.setPaint(squareColor(board.getType(row, column)));
		    g2d.fill(rect);
		}
	    }
	}
    }

    private Color squareColor(SquareType square) {
	switch(square) {
	    case EMPTY:
		return Color.BLACK;
	    case I:
		return Color.BLUE;
	    case O:
		return Color.YELLOW;
	    case T:
		return Color.RED;
	    case S:
		return Color.GRAY;
	    case Z:
		return Color.GREEN;
	    case J:
		return Color.ORANGE;
	    case L:
		return Color.MAGENTA;
	    default:
		return Color.WHITE;
	}
    }

    public void boardChanged() {
	repaint();
    }
}
