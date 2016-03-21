package Tetris;

public class Highscore
{
    private int score;
    private String player;

    public Highscore(int score, String player) {
	this.score = score;
	this.player = player;
    }

    public int getScore() {
	return score;
    }

    public String getPlayer() {
	return player;
    }

    public String toString() {
	return player + " - " + score;
    }
}
