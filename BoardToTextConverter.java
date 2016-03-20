package Tetris;

public class BoardToTextConverter
{
    public static void helpConvert(StringBuilder con, SquareType type) {
	switch (type) {
	    case EMPTY:
		con.append("#");
		break;
	    case I:
		con.append("I");
		break;
	    case O:
		con.append("O");
		break;
	    case T:
		con.append("T");
		break;
	    case S:
		con.append("S");
		break;
	    case Z:
		con.append("Z");
		break;
	    case J:
		con.append("J");
		break;
	    case L:
		con.append("L");
		break;
	    default:
		//????
		break;
	}
    }

    //Ser if och else ok ut????
    public static String convertToText(Board board){

	StringBuilder conversion = new StringBuilder();

	for (int row=0; row<board.getHeight(); row++) {
	    for (int column=0; column< board.getWidth(); column++) {
		if (board.getFalling() != null) {
		    if (row >= board.getFallingX() &&
			row <= board.getFallingX() + board.getFalling().getWidth() - 1 &&
			column >= board.getFallingY() &&
			column <= board.getFallingY() + board.getFalling().getHeight() - 1) {

			helpConvert(conversion, board.getFalling().getPolyType(row - board.getFallingX(), column - board.getFallingY()));
		    } else {

			helpConvert(conversion, board.getType(row, column));
		    }
		}
		else {
		    helpConvert(conversion, board.getType(row, column));
		}

	    }
	    conversion.append("\n");
	}
	String result = conversion.toString();
	//System.out.println(result);
	return result;
    }
}
