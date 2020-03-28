package org.bonge.bukkit.entity.living.animal;

import org.bonge.bukkit.world.BongeLocation;
import org.bukkit.Location;
import org.bukkit.entity.Chicken;

public class BongeChicken extends BongeAbstractAnimal<org.spongepowered.api.entity.living.animal.Chicken> implements Chicken {
    public BongeChicken(org.spongepowered.api.entity.living.animal.Chicken entity) {
        super(entity);
    }

    @Override
    public double getEyeHeight() {
        return 1;
    }

    @Override
    public double getEyeHeight(boolean ignorePose) {
        return 1;
    }

    @Override
    public Location getEyeLocation() {
        return new BongeLocation(getLocation()).add(0, 1, 0);
    }
}
