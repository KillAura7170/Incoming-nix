/*
 * Decompiled with CFR 0.151.
 */
package me.alpha432.oyvey.features.modules.player;

import me.alpha432.oyvey.util.InventoryUtil;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

public class MCP
extends Module {
    private boolean clicked = false;

    public MCP() {
        super("MCP", "mcp", Module.Category.PLAYER, false, false, false);
    }

    @Override
    public void onEnable() {
        if (MCP.fullNullCheck()) {
            this.disable();
        }
    }

    @Override
    public void onTick() {
        if (Mouse.isButtonDown((int)2)) {
            if (!this.clicked) {
                this.throwPearl();
            }
            this.clicked = true;
        } else {
            this.clicked = false;
        }
    }

    private void throwPearl() {
        int pearlSlot = InventoryUtil.findHotbarBlock(ItemEnderPearl.class);
        boolean offhand = MCP.mc.player.getHeldItemOffhand().getItem() == Items.ENDER_PEARL;
        boolean bl = offhand;
        if (pearlSlot != -1 || offhand) {
            int oldslot = MCP.mc.player.inventory.currentItem;
            if (!offhand) {
                InventoryUtil.switchToHotbarSlot(pearlSlot, false);
            }
            MCP.mc.playerController.processRightClick((EntityPlayer)MCP.mc.player, (World)MCP.mc.world, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            if (!offhand) {
                InventoryUtil.switchToHotbarSlot(oldslot, false);
            }
        }
    }
}

