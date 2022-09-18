/*
 * Decompiled with CFR 0.151.
 */
package me.alpha432.oyvey.features.modules.movement;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.entity.Entity;

public class ReverseStep
extends Module {
    private final Setting<Integer> speed = this.register(new Setting<Integer>("Speed", 8, 1, 20));
    private final Setting<Boolean> inliquid = this.register(new Setting<Boolean>("Liquid", false));
    private final Setting<Cancel> canceller = this.register(new Setting<Cancel>("CancelType", Cancel.None));
    private static ReverseStep INSTANCE = new ReverseStep();

    public ReverseStep() {
        super("ReverseStep", "rs", Module.Category.MOVEMENT, true, false, false);
        this.setInstance();
    }

    public static ReverseStep getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReverseStep();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onUpdate() {
        if (ReverseStep.nullCheck()) {
            return;
        }
        if (ReverseStep.mc.player.isSneaking() || ReverseStep.mc.player.isDead || ReverseStep.mc.player.collidedHorizontally || !ReverseStep.mc.player.onGround || ReverseStep.mc.player.isInWater() && this.inliquid.getValue(true) == false || ReverseStep.mc.player.isInLava() && this.inliquid.getValue(true) == false || ReverseStep.mc.player.isOnLadder() || ReverseStep.mc.gameSettings.keyBindJump.isKeyDown() || OyVey.moduleManager.isModuleEnabled("Burrow") || ReverseStep.mc.player.noClip || OyVey.moduleManager.isModuleEnabled("Packetfly") || OyVey.moduleManager.isModuleEnabled("Phase") || ReverseStep.mc.gameSettings.keyBindSneak.isKeyDown() && this.canceller.getValue(true) == Cancel.Shift || ReverseStep.mc.gameSettings.keyBindSneak.isKeyDown() && this.canceller.getValue(true) == Cancel.Both || ReverseStep.mc.gameSettings.keyBindJump.isKeyDown() && this.canceller.getValue(true) == Cancel.Space || ReverseStep.mc.gameSettings.keyBindJump.isKeyDown() && this.canceller.getValue(true) == Cancel.Both || OyVey.moduleManager.isModuleEnabled("Strafe")) {
            return;
        }
        for (double y = 0.0; y < 90.5; y += 0.01) {
            if (ReverseStep.mc.world.getCollisionBoxes((Entity)ReverseStep.mc.player, ReverseStep.mc.player.getEntityBoundingBox().offset(0.0, -y, 0.0)).isEmpty()) continue;
            ReverseStep.mc.player.motionY = (float)(-this.speed.getValue(true).intValue()) / 10.0f;
            break;
        }
    }

    public static enum Cancel {
        None,
        Space,
        Shift,
        Both;

    }
}

