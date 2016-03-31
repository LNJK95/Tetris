package Tetris;

public class DefaultCollisionHandler implements CollisionHandler
{
    private String type = "Default";

    public String getType() {
	return type;
    }

    public boolean hasCollision(Board board) {
	boolean boo = false;
	for (int row = 0; row < board.getFalling().getHeight(); row++) {
	    for (int column = 0; column < board.getFalling().getWidth(); column++) {
		if (( board.getFalling().getPolyType(row, column) != SquareType.EMPTY ) &&
		    (board.getType( board.getFallingX() + row, board.getFallingY() + column ) != SquareType.EMPTY)) {
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
}
