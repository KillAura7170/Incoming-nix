package me.alpha432.oyvey.features.modules.module2b2t;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import me.alpha432.oyvey.features.modules.Module;


public class NoSlow2b extends Module
{
    public NoSlow2b() {
        super("NoSlow2b", "2b2tNoSlow", Module.Category.MODULE2B2T, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (NoSlow2b.mc.player == null || NoSlow2b.mc.world == null) {
            return;
        }
        if (NoSlow2b.mc.player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() == Items.GOLDEN_APPLE && NoSlow2b.mc.gameSettings.keyBindUseItem.isKeyDown()) {
            NoSlow2b.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.findGappleInHotbar()));
        }
    }

    private int findGappleInHotbar() {
        int slot = 0;
        for (int i = 0; i < 9; ++i) {
            if (NoSlow2b.mc.player.inventory.getStackInSlot(i).getItem() == Items.GOLDEN_APPLE) {
                slot = i;
                break;
            }
        }
        return slot;
    }
}