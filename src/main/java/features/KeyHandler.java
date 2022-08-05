package features;

import game.GamePanel;
import game.UI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/** KeyHandler - implementarea controlului de la tastatura asupra jocului */
public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    private boolean upWasPressed,downWasPressed,leftWasPressed,rightWasPressed;
    public boolean checkDrawTime = false;
    GamePanel gPanel;

    public KeyHandler(GamePanel gPanel) {
        this.gPanel = gPanel;
    }

    /** Pentru tastele de mers spre nord/sud am observat ca daca apas alta directie, player-ul nu schimba directia
     *  Asadar, aceste 2 functii fac posibila comutarea intre deplasari */
    // adauga pe stiva prima tasta apasata
    private void stackFirstPressed() {

    }
    // primeste ca comanda prima tasta apasata
    private void retrieveFirstPressed() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        /** getKeyCode - returneaza valoarea din codul ascii asociat butonului */
        int code = e.getKeyCode();
        boolean cd = e.isActionKey();

        if (!UI.GAME_OVER) {
            switch (code) {
                // MOVEMENT
                case KeyEvent.VK_W -> upPressed = true;
                case KeyEvent.VK_S -> downPressed = true;
                case KeyEvent.VK_A -> leftPressed = true;
                case KeyEvent.VK_D -> rightPressed = true;
                // ZOOM IN
                case KeyEvent.VK_UP -> Camera.zoomInOut(1);
                // ZOOM OUT
                case KeyEvent.VK_DOWN -> Camera.zoomInOut(-1);
            }
        }

        // EXIT GAME
        if (code == KeyEvent.VK_ESCAPE)
            System.exit(0);

        // DEBUG
        if (code == KeyEvent.VK_T) {
            checkDrawTime = !checkDrawTime;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            // MOVEMENT
            case KeyEvent.VK_W ->  upPressed = false;
            case KeyEvent.VK_S -> downPressed = false;
            case KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_D -> rightPressed = false;
        }
    }
}