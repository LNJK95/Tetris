package Tetris;

import java.util.ArrayList;
import java.util.Arrays;

public class TetrominoMaker
{
    private ArrayList<SquareType> nm =
	    new ArrayList<SquareType>(Arrays.asList(SquareType.values()));


    public int getNumberOfTypes() {
	return SquareType.values().length - 2;
    }

    public static Poly getPoly(int n) {
	switch(n) {
	    case 0:
		SquareType[][] IType = new SquareType[][]{
			{SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY},
			{SquareType.I, SquareType.I, SquareType.I, SquareType.I},
			{SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY},
			{SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY}
		};
		Poly IPoly = new Poly(IType);
		return IPoly;
	    case 1:
		SquareType[][] JType = new SquareType[][]{
			{SquareType.J, SquareType.EMPTY, SquareType.EMPTY},
			{SquareType.J, SquareType.J, SquareType.J},
			{SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY}
		};
		Poly JPoly = new Poly(JType);
		return JPoly;
	    case 2:
		SquareType[][] LType = new SquareType[][]{
			{SquareType.EMPTY, SquareType.EMPTY, SquareType.L},
			{SquareType.L, SquareType.L, SquareType.L},
			{SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY}
		};
		Poly LPoly = new Poly(LType);
		return LPoly;
	    case 3:
		SquareType[][] OType = new SquareType[][]{
			{SquareType.O, SquareType.O},
			{SquareType.O, SquareType.O}
		};
		Poly OPoly = new Poly(OType);
		return OPoly;
	    case 4:
		SquareType[][] SType = new SquareType[][]{
			{SquareType.EMPTY, SquareType.S, SquareType.S},
			{SquareType.S, SquareType.S, SquareType.EMPTY},
			{SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY}
		};
		Poly SPoly = new Poly(SType);
		return SPoly;
	    case 5:
		SquareType[][] TType = new SquareType[][]{
			{SquareType.EMPTY, SquareType.T, SquareType.EMPTY},
			{SquareType.T, SquareType.T, SquareType.T},
			{SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY}
		};
		Poly TPoly = new Poly(TType);
		return TPoly;
	    case 6:
		SquareType[][] ZType = new SquareType[][]{
			{SquareType.Z, SquareType.Z, SquareType.EMPTY},
			{SquareType.EMPTY, SquareType.Z, SquareType.Z},
			{SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY}
		};
		Poly ZPoly = new Poly(ZType);
		return ZPoly;
	    default:
		throw new IllegalArgumentException("Ogiltigt index: " + n);
	}
    }
}
