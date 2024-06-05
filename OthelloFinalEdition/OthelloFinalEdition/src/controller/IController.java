package controller;

import model.OthelloGame;
import processing.event.KeyEvent;

public interface IController{
    void nextFrame();

    /**
     * user Input for Keyboard
     * @param key
     * @param keyCode
     */
   

    /**
     * user Input for mouse
     * @param x
     * @param y
     */
    void userInput(int x, int y);
     void userInput(KeyEvent event);

    void newGame();

    OthelloGame getGame();

}
