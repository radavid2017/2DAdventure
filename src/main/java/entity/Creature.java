package entity;

import animations.AnimationState;
import animations.StateMachine;
import animations.TypeAnimation;
import features.Direction;
import game.GamePanel;

import java.util.LinkedHashMap;

public abstract class Creature extends Entity {
    /** Liste de imagini pentru realizarea animatiilor de miscare */
    public AnimationState walkUp, walkDown, walkLeft, walkRight;
    /** Lista de animatii */
    public StateMachine movement = null;
    public StateMachine idle = null;
    public StateMachine attackState = null;

    public Creature(GamePanel gp) {
        super(gp);
    }

    public void checkCollisions() {
        collisionOn = false;
        gPanel.collisionDetector.manageTileCollision(this);
        if (this instanceof Player) {
            gPanel.collisionDetector.manageObjCollision(this);
        }
        gPanel.collisionDetector.checkEntity(this, gPanel.npcList);
        gPanel.collisionDetector.checkEntity(this, gPanel.monsterList);
        gPanel.collisionDetector.checkPlayer(this);
        gPanel.collisionDetector.checkEntity(this, gPanel.interactiveTiles);
    }

    public void update() {
        checkCollisions();

        if (!collisionOn && inMotion) {
            this.manageMovement();
        }

        manageInvincible();

//        currentTime++;
//        if (currentTime >= timeToChangeFrame) {
        currentAnimation.updateFrames();
//            currentTime = 0;
//        }
    }

    public void setupMovement(String creaturePath) {
        movement = new StateMachine();
        movement.loadCompleteAnimation(gPanel, creaturePath + "\\movement", TypeAnimation.IN_MOTION);
        currentAnimation = movement.down;
    }

    public void setupIdle(String creaturePath) {
        idle = new StateMachine();
        idle.loadCompleteAnimation(gPanel, creaturePath + "\\idle", TypeAnimation.IDLE);
    }

    public void setupStaticIdle(String creaturePath) {
        idle = new StateMachine();
        idle.loadCompleteAnimation(gPanel, creaturePath + "\\idle", TypeAnimation.IDLE);
        currentAnimation = idle.down;
//        idle.loadSingleAnimation(gPanel, creaturePath + "\\idle", TypeAnimation.IDLE, Direction.DOWN);

        /** setari statice */
        this.collisionOn = false;
        inMotion = false;
        this.direction = Direction.DOWN;
        for (int i = 0; i < idle.states.size(); i++) {
            idle.states.get(i).timeToChangeFrame = 28;
        }
    }

    public void setupAttack(String creaturePath) {
        attackState = new StateMachine();
        attackState.loadCompleteAnimation(gPanel, creaturePath + "\\attack\\", TypeAnimation.ATTACK);
    }
}
