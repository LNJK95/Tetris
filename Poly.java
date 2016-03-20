package Tetris;

public class Poly
{
    private SquareType[][] poly;

    public Poly(final SquareType[][] poly) {
	this.poly = poly;
    }

    public int getHeight() {
	return poly.length;
    }

    public int getWidth() {
	return poly[0].length;
    }

    public SquareType getPolyType(int x, int y) {
	return poly[x][y];
    }

    public SquareType[][] getFullPoly() {
	return poly;
    }
}
