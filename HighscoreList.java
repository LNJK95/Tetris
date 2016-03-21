package Tetris;

import java.util.*;

public class HighscoreList
{
    private static final HighscoreList INSTANCE = new HighscoreList();
    private List<Highscore> hsList;

    private HighscoreList() {
	hsList = new ArrayList<Highscore>();
    }

    public static HighscoreList getInstance() {
	return INSTANCE;
    }

    public void addHighscore(Highscore hs) {
	hsList.add(hs);
    }

    public String toString() {
	Collections.sort(hsList, new ScoreComparator());
	StringBuilder sb = new StringBuilder();
	for (Highscore hs : hsList) {
	    sb.append(hs + "\n");
	}
	String lst = sb.toString();
	return lst;
    }
}
