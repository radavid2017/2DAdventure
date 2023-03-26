package item.consumable.potion;

import entity.Entity;
import features.Dialogue;
import game.GamePanel;
import game.GameState;
import item.Consumable;
import item.TypeItem;
import item.consumable.TypeConsumable;
import item.consumable.key.KeyModel;

public abstract class OBJ_Potion extends Consumable {
    PotionModel potionModel;
    int healingValue;

    public OBJ_Potion(GamePanel gPanel) {
        super(gPanel, TypeConsumable.Potion);
        setHealingValue();
        objPath += "potions/";
    }

    @Override
    public void setDefaultSolidArea() {

    }

    public abstract void setHealingValue();

    public void use() {
        GamePanel.gameState = GameState.Dialogue;
        getGamePanel().ui.setCurrentDialogue(new Dialogue("Ai consumat " + name + "!\n" +
                "Incepi sa te vindeci..."));


        getGamePanel().player.life += healingValue;

        if (getGamePanel().player.life > getGamePanel().player.maxLife) {
            getGamePanel().player.life = getGamePanel().player.maxLife;
        }
        getGamePanel().playSE("powerup.wav");

    }
}