package Tetris;

import java.util.Random;
import java.util.*;
import javax.swing.*;

public class Board
{
    //variables
    private SquareType[][] squares;
    private int width;
    private int height;

    private Poly falling;
    private int fallingX;
    private int fallingY;

    private List<BoardListener> boardListeners;

    private boolean gameOver;
    private int points = 0;

    private CollisionHandler cHandler;

    private static final int BORDER = 2;

    //get and set - methods
    public Poly getFalling() {
	return falling;
    }

    public int getFallingX() {
	return fallingX;
    }

    public int getFallingY() {
	return fallingY;
    }

    public int getHeight() {
    	return height;
    }

    public int getWidth() {
    	return width;
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

    public CollisionHandler getCHandler() {
	return cHandler;
    }

    //constructor
    public Board(final int height, final int width) {
	this.height = height;
	this.width = width;
	gameOver = false;
	cHandler = new DefaultCollisionHandler();
	boardListeners = new ArrayList<BoardListener>();

	squares =  new SquareType[height+BORDER*2][width+BORDER*2];

	for (int row = 0; row < height+BORDER*2; row++) {
	    for (int column = 0; column < width+BORDER*2; column++) {
		if ( (row < BORDER) || (row >= height+BORDER) ||
		     (column < BORDER) || (column >= width+BORDER) ) {
		    squares[row][column] = SquareType.OUTSIDE;
		}
		else {
		    squares[row][column] = SquareType.EMPTY;
		}
	    }
	}
	notifyListeners();
    }

    //old method, probably doesn't work anymore because of updates to other code
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

    public SquareType getType(int y, int x){
	return squares[y+BORDER][x+BORDER];
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
	    if (cHandler.hasCollision(this)) {
		fallingY--;
		for (int row = 0; row < falling.getHeight(); row++) {
		    for (int column = 0; column < falling.getWidth(); column++) {
			if (falling.getPolyType(row, column) != SquareType.EMPTY) {
			    squares[BORDER+fallingX+row][BORDER+fallingY+column]
				    = falling.getPolyType(row, column);
			}
		    }
		}
		int removedRows = 0;
		for (int c = 0; c < width; c++) {
		    if (isRowFull(c)) {
			removedRows++;
			removeRow(c);
		    }
		}
		switchCHandler(removedRows);
		addPoints(removedRows);
		falling = null;
	    }
	    notifyListeners();
	}
	else {
	    Random rnd = new Random();
	    falling = TetrominoMaker.getPoly(rnd.nextInt(SquareType.values().length -2));
	    fallingX = squares.length/2 - 3; //kan bli problem om ojämnt antal!!!!
	    fallingY = 0;
	    if (cHandler.hasCollision(this)) {
		gameOver = true;
		falling = null;
	    }
	    notifyListeners();
	}
    }

    public void moveRight() {
	if (falling != null) {
	    fallingX++;
	    if (cHandler.hasCollision(this)) {
		fallingX--;
	    }
	}
    	notifyListeners();
    }

    public void moveLeft() {
	if (falling != null) {
	    fallingX--;
	    if (cHandler.hasCollision(this)) {
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
	    if (cHandler.hasCollision(this)) {
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

    public void removeRow(int column) {
	for (int c = column; c > 0; c--) {
	    for (int r = 0; r < height; r++) {
		squares[r+BORDER][c+BORDER] = getType(r, c-1);
	    }
	}
	notifyListeners();
    }

    public boolean isRowFull(int column) {
	boolean yes = true;
	for (int r = 0; r < height; r++) {
	    if (getType(r, column) == SquareType.EMPTY) {
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

    public void erase(int y, int x) {
	squares[BORDER+y][BORDER+x] = SquareType.EMPTY;
    }

    public boolean possibility(int row, int column) {
	boolean possible = false;
	for (int r = row; r < width; r++) {
	    if (getType(column, r) == SquareType.EMPTY) {
		possible = true;
	    }
	}
	return possible;
    }


    public boolean possibleAll(int row, int column) {
	boolean pAll = true;
	for (int i = fallingY; i < fallingY + falling.getWidth(); i++) {
	    if (!possibility(i, column)) {
		pAll = false;
	    }
	}
	return pAll;
    }

    public void pushDownColumn(int row, int column) {
	if (possibleAll(row, column)) {
	    int whichRow = 0;
	    for (int r = row; r < width; r++) {
		if (getType(column, r) == SquareType.EMPTY) {
		    whichRow = r;
		    break;
		}
	    }
	    for (int r = whichRow; r >= row; r--) {
		squares[BORDER+column][BORDER+r] = getType(column, r-1);
	    }
	}
	else {
	    cHandler = new DefaultCollisionHandler();
	    fallingY--;
	}
	notifyListeners();
    }

    public void switchCHandler(int removedRows) {
	switch(removedRows) {
	    case 0:
	    case 1:
		cHandler = new DefaultCollisionHandler();
		break;
	    case 2:
		cHandler = new FallThrough();
		break;
	    case 3:
		cHandler = new Heavy();
		break;
	    default:
		cHandler = new DefaultCollisionHandler();
		break;
	}
    }
}
