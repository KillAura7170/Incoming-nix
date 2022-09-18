package me.alpha432.oyvey.features.modules.module2b2t;

import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.client.settings.KeyBinding;

public class AutoWalk
        extends Module {
    public AutoWalk() {
        super("AutoWalk", "Automatically walks in a straight line", Module.Category.MODULE2B2T, true, false, false);
    }

    @Override
    public void onUpdate() {
        KeyBinding.setKeyBindState(AutoWalk.mc.gameSettings.keyBindForward.getKeyCode(), true);
    }

    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState(AutoWalk.mc.gameSettings.keyBindForward.getKeyCode(), false);
    }
}

