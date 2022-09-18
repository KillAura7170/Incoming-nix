package me.alpha432.oyvey.features.modules.movement;

import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.init.Blocks;

public class IceSpeed
extends Module {
    private static IceSpeed INSTANCE = new IceSpeed();
    private final Setting<Float> speed = this.register(new Setting<Float>("Speed", Float.valueOf(0.4f), Float.valueOf(0.2f), Float.valueOf(1.5f)));

    public IceSpeed() {
        super("IceSpeed", "Speeds you up on ice.", Module.Category.MOVEMENT, false, false, false);
        INSTANCE = this;
    }

    public static IceSpeed getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new IceSpeed();
        }
        return INSTANCE;
    }

    @Override
    public void onUpdate() {
        Blocks.ICE.slipperiness = this.speed.getValue(true).floatValue();
        Blocks.PACKED_ICE.slipperiness = this.speed.getValue(true).floatValue();
        Blocks.FROSTED_ICE.slipperiness = this.speed.getValue(true).floatValue();
    }

    @Override
    public void onDisable() {
        Blocks.ICE.slipperiness = 0.98f;
        Blocks.PACKED_ICE.slipperiness = 0.98f;
        Blocks.FROSTED_ICE.slipperiness = 0.98f;
    }
}

