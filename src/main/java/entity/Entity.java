package entity;

import features.AnimationState;
import features.Camera;
import features.Direction;
import features.StateMachine;
import game.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/** Super clasa - Entitate
 *  Aceasta contine variabile care pot fi folosite
 *  de orice entitate precum north-ul, monstrii si NPC-uri
 */
public abstract class Entity {

    GamePanel gPanel;
    public double worldX; // pozitia pe axa ox
    public double worldY; // pozitia pe axa oy
    public double speed; // viteza de deplasare a entitatii

    /** Liste de imagini pentru realizarea animatiilor de miscare */
    public AnimationState walkUp, walkDown, walkLeft, walkRight;
    /** Lista de animatii */
    public StateMachine movement = new StateMachine();
//    // directii diagonale - optional
    public BufferedImage[] upLeft,upRight,downLeft,downRight;
    /** Variabila pentru a declansa animatia corecta in functie de directia de miscare */
    public Direction direction;

    /** Declararea blocului de coliziune */
    public Rectangle solidArea = new Rectangle(32, 32, 38, 48); // aria de coliziune implicita pentru toate entitatile
    public int solidAreaDefaultX = 32, solidAreaDefaultY = 32;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public int timeToChangeFrame = 12;
    public int currentTime = 0;

    /** Constrcutor entitate */
    public Entity(GamePanel gPanel) {
        this.gPanel = gPanel;
    }

    public Entity() {

    }

    /** Metodele de actualizare si scriere a informatiilor asupra entitatii */
    // actualizare
    public void update() {
        setAction();

        collisionOn = false;
        gPanel.collisionDetector.manageTileCollision(this);
        gPanel.collisionDetector.manageObjCollision(this, false);
        gPanel.collisionDetector.checkPlayer(this);

        if (!collisionOn)
            this.manageMovement();

        currentTime++;
        if (currentTime >= timeToChangeFrame) {
            AnimationState.updateFrames();
            currentTime = 0;
        }

    }

    public void setAction() {

    }

    /** Movement management */
    public void manageMovement() {
        switch (this.direction) {
            case UP -> this.worldY -= this.speed;
            case DOWN -> this.worldY += this.speed;
            case LEFT -> this.worldX -= this.speed;
            case RIGHT -> this.worldX += this.speed;
        }
    }

    /** Desenarea entitatii */
    public void draw(Graphics2D g2D) {
        double screenX = worldX - gPanel.player.worldX + gPanel.player.screenX;
        double screenY = worldY - gPanel.player.worldY + gPanel.player.screenY;
        // Instantiere camera
        Camera camera = new Camera(worldX, worldY, screenX, screenY, gPanel);

        BufferedImage sprite = null;
        sprite = movement.manageAnimations(direction, true);

        // Management Camera
        camera.manageEntity(g2D, sprite);

        g2D.setColor(Color.red);
        g2D.drawRect((int) (screenX + solidArea.x), (int) (screenY + solidArea.y), solidArea.width, solidArea.height);
    }
    // incarcarea animatiilor de miscare
        /** ANIMATII MOVEMENT */
    public void loadMovementAnimations(String entityPath) {
//        String movementFolderName = JOptionPane.showInputDialog("Numele folderului de animatie:");
//        String characterName = "archer";
        // UP
//        RenameFolderFiles.rename("E:\\AplicatiiCV\\2DAdventure\\res\\player\\movement\\north");
//        walkUp = new AnimationState(this.gPanel, "walkUp", Direction.UP, entityPath + "\\" + characterName + "\\movement\\north");
        walkUp = new AnimationState(this.gPanel, "walkUp", Direction.UP, entityPath + "\\movement\\north");
        System.out.println("Animatia " + walkUp.title + " incarcata cu succes.");
        // RIGHT
//        walkRight = new AnimationState(this.gPanel, "walkRight", Direction.RIGHT,entityPath + "\\" + characterName + "\\movement\\east");
        walkRight = new AnimationState(this.gPanel, "walkRight", Direction.RIGHT,entityPath + "\\movement\\east");
        System.out.println("Animatia " + walkRight.title + " incarcata cu succes.");
        // DOWN
//        walkDown = new AnimationState(this.gPanel, "walkDown", Direction.DOWN, entityPath + "\\" + characterName + "\\movement\\south");
        walkDown = new AnimationState(this.gPanel, "walkDown", Direction.DOWN, entityPath + "\\movement\\south");
        System.out.println("Animatia " + walkDown.title + " incarcata cu succes.");
        // LEFT
//        walkLeft = new AnimationState(this.gPanel, "walkLeft", Direction.LEFT, entityPath + "\\" + characterName + "\\movement\\west");
        walkLeft = new AnimationState(this.gPanel, "walkLeft", Direction.LEFT, entityPath + "\\movement\\west");
        System.out.println("Animatia " + walkLeft.title + " incarcata cu succes.");

        movement = new StateMachine();
        for (AnimationState animationState : Arrays.asList(this.walkUp, this.walkDown, this.walkRight, this.walkLeft)) {
            this.movement.add(animationState);
        }
    }

    public void setPosition(double worldX, double worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
    }

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

}
