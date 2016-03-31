package Tetris;

public interface CollisionHandler
{
    public String getType();

    public boolean hasCollision(Board board);
}
