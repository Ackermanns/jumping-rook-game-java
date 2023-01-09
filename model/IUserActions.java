package com.example.jumpingrookgame.model;

public interface IUserActions {
    public void undoLastMove();
    public <T> void moveControl(T playerMove);
    public void moveUp();
    public void moveRight();
    public void moveDown();
    public void moveLeft();
}
