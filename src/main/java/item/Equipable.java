package item;

import game.CharacterClass;
import game.GamePanel;
import item.equipable.TypeEquipable;

public abstract class Equipable extends Item {

    public CharacterClass playerClass;
    public TypeEquipable typeEquipable;

    public Equipable(GamePanel gPanel, TypeEquipable typeEquipable, CharacterClass playerClass, int price) {
        super(gPanel, TypeItem.Equipable, price);
        this.playerClass = playerClass;
        this.typeEquipable = typeEquipable;
        objPath += "equipable/";
    }
}
