/*
 * Decompiled with CFR 0.151.
 */
package me.alpha432.oyvey.features.modules.misc;

import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;

public class RenameBypass
extends Module {
    private final Setting<Integer> slotid = this.register(new Setting<Integer>("slotid", 36, 0, 44));

    public RenameBypass() {
        super("RenameBypass", "rb", Module.Category.MISC, true, false, false);
    }

    @Override
    public void onEnable() {
        if (RenameBypass.mc.world != null) {
            RenameBypass.mc.playerController.windowClick(0, this.slotid.getValue(true).intValue(), 0, ClickType.PICKUP, (EntityPlayer)RenameBypass.mc.player);
            RenameBypass.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)RenameBypass.mc.player);
            RenameBypass.mc.playerController.windowClick(0, this.slotid.getValue(true).intValue(), 0, ClickType.PICKUP, (EntityPlayer)RenameBypass.mc.player);
            this.disable();
        }
    }
}

