/*
 * Decompiled with CFR 0.151.
 */
package me.alpha432.oyvey.features.modules.player;

import me.alpha432.oyvey.event.events.PacketEvent;
import me.alpha432.oyvey.event.events.UpdateWalkingPlayerEvent;
import me.alpha432.oyvey.features.command.Command;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityDesync
extends Module {
    private Entity Riding = null;

    public EntityDesync() {
        super("EntityDesync", "ed", Module.Category.PLAYER, true, false, false);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (EntityDesync.mc.player == null) {
            this.Riding = null;
            this.toggle();
            return;
        }
        if (!EntityDesync.mc.player.isRiding()) {
            Command.sendMessage("You are not riding an entity");
            this.Riding = null;
            this.toggle();
            return;
        }
        this.Riding = EntityDesync.mc.player.getRidingEntity();
        EntityDesync.mc.player.dismountRidingEntity();
        EntityDesync.mc.world.removeEntity(this.Riding);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (this.Riding != null) {
            this.Riding.isDead = false;
            if (!EntityDesync.mc.player.isRiding()) {
                EntityDesync.mc.world.spawnEntity(this.Riding);
                EntityDesync.mc.player.startRiding(this.Riding, true);
            }
            this.Riding = null;
            Command.sendMessage("Forced a remount");
        }
    }

    @SubscribeEvent
    public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent event) {
        if (this.Riding == null) {
            return;
        }
        if (EntityDesync.mc.player.isRiding()) {
            return;
        }
        EntityDesync.mc.player.onGround = true;
        this.Riding.setPosition(EntityDesync.mc.player.posX, EntityDesync.mc.player.posY, EntityDesync.mc.player.posZ);
        EntityDesync.mc.player.connection.sendPacket((Packet)new CPacketVehicleMove(this.Riding));
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSetPassengers) {
            if (this.Riding == null) {
                return;
            }
            SPacketSetPassengers l_Packet = (SPacketSetPassengers)event.getPacket();
            Entity en = EntityDesync.mc.world.getEntityByID(l_Packet.getEntityId());
            if (en == this.Riding) {
                for (int i : l_Packet.getPassengerIds()) {
                    Entity ent = EntityDesync.mc.world.getEntityByID(i);
                    if (ent != EntityDesync.mc.player) continue;
                    return;
                }
                Command.sendMessage("You dismounted");
                this.toggle();
            }
        } else if (event.getPacket() instanceof SPacketDestroyEntities) {
            SPacketDestroyEntities l_Packet = (SPacketDestroyEntities)event.getPacket();
            for (int l_EntityId : l_Packet.getEntityIDs()) {
                if (l_EntityId != this.Riding.getEntityId()) continue;
                Command.sendMessage("Entity is now null SPacketDestroyEntities");
                return;
            }
        }
    }
}

