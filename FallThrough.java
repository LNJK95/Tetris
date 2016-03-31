package Tetris;

public class FallThrough implements CollisionHandler
{
    private String type = "FallThrough";

    public String getType() {
    	return type;
    }

    public boolean hasCollision(Board board) {
	boolean boo = false;
	for (int row = 0; row < board.getFalling().getHeight(); row++) {
	    for (int column = 0; column < board.getFalling().getWidth(); column++) {
		if (( board.getFalling().getPolyType(row, column) != SquareType.EMPTY ) &&
		    (board.getType( board.getFallingX() + row, board.getFallingY() + column ) == SquareType.OUTSIDE)) {
		    boo = true;
		    return boo;
		}
		else {
		    boo = false;
		}
	    }
	}
	for (int row = 0; row < board.getFalling().getHeight(); row++) {
	    for (int column = 0; column < board.getFalling().getWidth(); column++) {
		if (board.getFalling().getPolyType(row, column) != SquareType.EMPTY) {
		    board.erase(board.getFallingX() + row, board.getFallingY() + column);
		}
	    }
	}
	return boo;
    }
}
