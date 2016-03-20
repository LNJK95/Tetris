package Tetris;

import java.util.Random;
import java.util.*;
import javax.swing.*;

public class Board
{
    private SquareType[][] squares;
    private int width;
    private int height;
    private Poly falling;
    private int fallingX;
    private int fallingY;
    private List<BoardListener> boardListeners;
    private boolean gameOver;
    private int points = 0;

    public Poly getFalling() {
	return falling;
    }

    public int getFallingX() {
	return fallingX;
    }

    public int getFallingY() {
	return fallingY;
    }

    public boolean getGameOver() {
	return gameOver;
    }

    public void setGameOver(boolean gameOver) {
	this.gameOver = gameOver;
    }

    public int getPoints() {
	return points;
    }

    public Board(final int height, final int width) {
	this.width = width;
	this.height = height;
	gameOver = false;
	boardListeners = new ArrayList<BoardListener>();

	squares =  new SquareType[height+4][width+4];

	for (int row=0; row < height+4; row++) {
	    for (int column=0; column<width+4; column++) {
		if ( (row < 2) || (row > height+1) || (column < 2) || (column > width+1) ) {
		    squares[row][column] = SquareType.OUTSIDE;
		}
		else {
		    squares[row][column] = SquareType.EMPTY;
		}
	    }
	}
	notifyListeners();
    }

    public Board randomBoard(Board board) {
	Random rnd = new Random();
	for (int h = 0; h < height; h++){
	    for (int w = 0; w < width; w++) {
		squares[h][w] = SquareType.values()[rnd.nextInt(SquareType.values().length)];
	    }
	}
	notifyListeners();
	return board;
    }

    public int getHeight() {
	return height;
    }

    public int getWidth() {
	return width;
    }

    public SquareType getType(int x, int y){
	return squares[x+2][y+2];
    }

    public void addBoardListener(BoardListener bl) {
	boardListeners.add(bl);
    }

    private void notifyListeners() {
	for (BoardListener boardListener : boardListeners) {
	    boardListener.boardChanged();
	}
    }

    public void tick() {
	if (falling != null) {
	    fallingY++;
	    if (hasCollision()) {
		fallingY--;
		for (int row = 0; row < falling.getHeight(); row++) {
		    for (int column = 0; column < falling.getWidth(); column++) {
			if (falling.getPolyType(row, column) != SquareType.EMPTY) {
			    squares[2+fallingX+row][2+fallingY+column] = falling.getPolyType(row, column);
			}
		    }
		}
		int removedRows = 0;
		for (int r = 0; r < width; r++) {
		    if (isRowFull(r)) {
			removedRows++;
			removeRow(r);
		    }
		}
		addPoints(removedRows);
		System.out.println(points);
		falling = null;
	    }
	    notifyListeners();
	}
	else {
	    Random rnd = new Random();
	    falling = TetrominoMaker.getPoly(rnd.nextInt(SquareType.values().length -2));
	    fallingX = squares.length/2 - 3; //kan bli problem om ojämnt antal!!!!
	    fallingY = 0;
	    if (hasCollision()) {
		gameOver = true;
		falling = null;
	    }
	    notifyListeners();
	}
    }

    public void moveRight() {
	if (falling != null) {
	    fallingX++;
	    if (hasCollision()) {
		fallingX--;
	    }
	}
    	notifyListeners();
    }

    public void moveLeft() {
	if (falling != null) {
	    fallingX--;
	    if (hasCollision()) {
		fallingX++;
	    }
	}
	notifyListeners();
    }

    public void moveDown() {
	if (!gameOver) {
	    tick();
	}
    }

    public void rotate() {
	if (falling != null) {
	    Poly old = new Poly(falling.getFullPoly());
	    falling = rotateRight();
	    if (hasCollision()) {
		falling = old;
	    }
	    notifyListeners();
	}
    }

    //haha ska kanske inte ligga här lmao
    public Poly rotateRight() {
	int size = falling.getHeight();
	SquareType[][] squarinos = new SquareType[size][size];

	for (int r = 0; r < size; r++) {
	    for (int c = 0; c < size; c++) {
		squarinos[c][size - 1 - r] = falling.getPolyType(r,c);
	    }
	}
	Poly newPoly = new Poly(squarinos);
	return newPoly;
    }

    public boolean hasCollision() {
	boolean boo = false;
	for (int row = 0; row < falling.getHeight(); row++) {
	    for (int column = 0; column < falling.getWidth(); column++) {
		if ((falling.getPolyType(row, column) != SquareType.EMPTY) && (getType(fallingX + row, fallingY + column) != SquareType.EMPTY)) {
		    boo = true;
		    return boo;
		}
		else {
		    boo = false;
		}
	    }
	}
	return boo;
    }

    public void removeRow(int row) {
	for (int r = row; r > 0; r--) {
	    for (int c = 0; c < height; c++) {
		squares[c + 2][r + 2] = getType(c, r-1);
	    }
	}
	notifyListeners();
    }

    public boolean isRowFull(int row) {
	boolean yes = true;
	for (int c = 0; c < height; c++) {
	    if (getType(c, row) == SquareType.EMPTY) {
		yes = false;
	    }
	}
	return yes;
    }

    private void addPoints(int rows) {
	switch(rows) {
	    case 0:
		break;
	    case 1:
		points += 100;
		break;
	    case 2:
		points += 300;
		break;
	    case 3:
		points += 500;
		break;
	    case 4:
		points += 800;
		break;
	    default:
		points +=800;
		break;
	}
    }
}
