package item.equipable.shield;

import defense.IDoDefense;
import game.CharacterClass;
import game.GamePanel;
import item.Equipable;
import item.equipable.TypeEquipable;
import object.TypeObject;
import item.equipable.shield.ModelShield;

import java.util.ArrayList;
import java.util.List;

public abstract class Shield extends Equipable {
    public int defense = 0;
    public List<IDoDefense> defenseTypes = new ArrayList<>();
    public String shieldPath = "res/item/equipable/shield/";
    public ModelShield modelShield;

    public Shield (int defense, IDoDefense defenseType, GamePanel gp, CharacterClass playerClass, int price) {
        super(gp, TypeEquipable.Shield, playerClass, price);
        this.defense = defense;
        addDefenseType(defenseType);
    }

    public void addDefenseType(IDoDefense defenseType) {
        if (defenseType != null)
            defenseTypes.add(defenseType);
    }

    public void setDefenseType(int index, IDoDefense defenseType) {
        for (int i = 0; i < defenseTypes.size(); i++) {
            if (i == index)
                if (defenseTypes.get(i) != null)
                    defenseTypes.set(index, defenseType);
        }
    }
}
