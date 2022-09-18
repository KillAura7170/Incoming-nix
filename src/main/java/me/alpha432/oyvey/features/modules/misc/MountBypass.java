/*
 * Decompiled with CFR 0.151.
 */
package me.alpha432.oyvey.features.modules.misc;

import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.util.InventoryUtil;
import me.alpha432.oyvey.event.events.PacketEvent;
import me.alpha432.oyvey.features.command.Command;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.modules.combat.AutoWeb;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockWeb;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MountBypass
extends Module {
    public Setting<Type> type = this.register(new Setting<Type>("Type", Type.Old));
    private int lastHotbarSlot;

    public MountBypass() {
        super("MountBypass", "mb", Module.Category.MISC, true, false, false);
    }

    @Override
    public void onEnable() {
        if (this.type.getValue(true) == Type.New) {
            if (MountBypass.fullNullCheck()) {
                return;
            }
            this.lastHotbarSlot = AutoWeb.mc.player.inventory.currentItem;
        }
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        CPacketUseEntity packet;
        if (this.type.getValue(true) == Type.Old && event.getPacket() instanceof CPacketUseEntity && (packet = (CPacketUseEntity)event.getPacket()).getEntityFromWorld((World)MountBypass.mc.world) instanceof AbstractChestHorse && packet.getAction() == CPacketUseEntity.Action.INTERACT_AT) {
            event.setCanceled(true);
            Command.sendMessage("<" + this.getDisplayName() + "> attempted a mountbypass");
        }
        if (this.type.getValue(true) == Type.New && event.getPacket() instanceof CPacketUseEntity) {
            packet = (CPacketUseEntity)event.getPacket();
            int chestSlot = InventoryUtil.findHotbarBlock(BlockChest.class);
            if (chestSlot == -1) {
                Command.sendMessage("<" + this.getDisplayName() + "> you are out of chests");
                this.disable();
                return;
            }
            if (MountBypass.mc.player.inventory.currentItem != this.lastHotbarSlot && MountBypass.mc.player.inventory.currentItem != chestSlot) {
                this.lastHotbarSlot = MountBypass.mc.player.inventory.currentItem;
            }
            int originalSlot = AutoWeb.mc.player.inventory.currentItem;
            int webSlot = InventoryUtil.findHotbarBlock(BlockWeb.class);
            if (webSlot == -1) {
                this.toggle();
            }
            MountBypass.mc.player.inventory.currentItem = webSlot == -1 ? webSlot : webSlot;
            MountBypass.mc.playerController.updateController();
            MountBypass.mc.player.inventory.currentItem = originalSlot;
            MountBypass.mc.playerController.updateController();
            Command.sendMessage("<" + this.getDisplayName() + "> attempted a mountbypass");
        }
    }

    public static enum Type {
        Old,
        New;

    }
}

