/*
 * Decompiled with CFR 0.151.
 */
package me.alpha432.oyvey.features.modules.player;

import java.util.Objects;
import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.event.events.PacketEvent;
import me.alpha432.oyvey.event.events.UpdateWalkingPlayerEvent;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Godmode
extends Module {
    private final Setting<Mode> mode = this.register(new Setting<Mode>("Mode", Mode.Entity));
    private final Setting<Boolean> entitytrue = this.register(new Setting<Object>("Entity", Boolean.valueOf(false), v -> this.mode.getValue(true) == Mode.Entity));
    private final Setting<Boolean> remount = this.register(new Setting<Object>("Remount", Boolean.valueOf(false), v -> this.mode.getValue(true) == Mode.Entity && this.entitytrue.getValue(true) != false));
    public final Setting<Boolean> footsteps = this.register(new Setting<Boolean>("FootSteps", false));
    public Minecraft mc = Minecraft.getMinecraft();
    private Entity riding;
    public Entity entity;

    public Godmode() {
        super("God", "godmodes doe", Module.Category.PLAYER, true, false, false);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mode.getValue(true) == Mode.Entity) {
            if (this.mc.world != null && this.mc.player.getRidingEntity() != null) {
                this.entity = this.mc.player.getRidingEntity();
                this.mc.renderGlobal.loadRenderers();
                this.hideEntity();
                this.mc.player.setPosition((double)Minecraft.getMinecraft().player.getPosition().getX(), (double)(Minecraft.getMinecraft().player.getPosition().getY() - 1), (double)Minecraft.getMinecraft().player.getPosition().getZ());
            }
            if (this.mc.world != null && this.remount.getValue(true).booleanValue()) {
                this.remount.setValue(false);
            }
        }
        if (this.mode.getValue(true) == Mode.Riding) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.player.getRidingEntity() != null) {
                this.riding = mc.player.getRidingEntity();
                mc.player.dismountRidingEntity();
                mc.world.removeEntity(this.riding);
                mc.player.setPosition((double)mc.player.getPosition().getX(), (double)(mc.player.getPosition().getY() - 1), (double)mc.player.getPosition().getZ());
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (this.mode.getValue(true) == Mode.Entity) {
            if (this.remount.getValue(true).booleanValue()) {
                this.remount.setValue(false);
            }
            this.mc.player.dismountRidingEntity();
            this.mc.getConnection().sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.mc.getConnection().sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        if (this.mode.getValue(true) == Mode.Riding && this.riding != null) {
            Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketUseEntity(this.riding, EnumHand.MAIN_HAND));
        }
        if (this.mode.getValue(true) == Mode.Lock) {
            Minecraft.getMinecraft().player.respawnPlayer();
        }
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer.Position || event.getPacket() instanceof CPacketPlayer.PositionRotation) {
            event.setCanceled(true);
        }
        if (this.mode.getValue(true) == Mode.Riding) {
            if (event.getPacket() instanceof CPacketUseEntity) {
                Entity entity;
                Minecraft mc = Minecraft.getMinecraft();
                CPacketUseEntity packet = (CPacketUseEntity)event.getPacket();
                if (this.riding != null && (entity = packet.getEntityFromWorld((World)mc.world)) != null) {
                    this.riding.posX = entity.posX;
                    this.riding.posY = entity.posY;
                    this.riding.posZ = entity.posZ;
                    this.riding.rotationYaw = mc.player.rotationYaw;
                    mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(mc.player.rotationYaw, mc.player.rotationPitch, true));
                    mc.player.connection.sendPacket((Packet)new CPacketInput(mc.player.movementInput.moveForward, mc.player.movementInput.moveStrafe, false, false));
                    mc.player.connection.sendPacket((Packet)new CPacketVehicleMove(this.riding));
                }
            }
            if (event.getPacket() instanceof CPacketPlayer.Position || event.getPacket() instanceof CPacketPlayer.PositionRotation) {
                event.setCanceled(true);
            }
        }
    }

    private void hideEntity() {
        if (this.mc.player.getRidingEntity() != null) {
            this.mc.player.dismountRidingEntity();
            this.mc.world.removeEntity(this.entity);
        }
    }

    private void showEntity(Entity entity2) {
        entity2.isDead = false;
        this.mc.world.loadedEntityList.add(entity2);
        this.mc.player.startRiding(entity2, true);
    }

    @SubscribeEvent
    public void onPlayerWalkingUpdate(UpdateWalkingPlayerEvent event) {
        if (this.entity == null) {
            return;
        }
        if (this.mode.getValue(true) == Mode.Entity && event.getStage() == 0) {
            if (this.remount.getValue(true).booleanValue() && Objects.requireNonNull(OyVey.moduleManager.getModuleByClass(Godmode.class)).isEnabled()) {
                this.showEntity(this.entity);
            }
            this.entity.setPositionAndRotation(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ, Minecraft.getMinecraft().player.rotationYaw, Minecraft.getMinecraft().player.rotationPitch);
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(this.mc.player.rotationYaw, this.mc.player.rotationPitch, true));
            this.mc.player.connection.sendPacket((Packet)new CPacketInput(this.mc.player.movementInput.moveForward, this.mc.player.movementInput.moveStrafe, false, false));
            this.mc.player.connection.sendPacket((Packet)new CPacketVehicleMove(this.entity));
        }
        if (this.mode.getValue(true) == Mode.Riding && this.riding != null) {
            this.riding.posX = this.mc.player.posX;
            this.riding.posY = this.mc.player.posY + (double)(this.footsteps.getValue(true) != false ? 0.3f : 0.0f);
            this.riding.posZ = this.mc.player.posZ;
            this.riding.rotationYaw = Minecraft.getMinecraft().player.rotationYaw;
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(this.mc.player.rotationYaw, this.mc.player.rotationPitch, true));
            this.mc.player.connection.sendPacket((Packet)new CPacketInput(this.mc.player.movementInput.moveForward, this.mc.player.movementInput.moveStrafe, false, false));
            this.mc.player.connection.sendPacket((Packet)new CPacketVehicleMove(this.riding));
        }
    }

    @Override
    public void onUpdate() {
        Minecraft mc = Minecraft.getMinecraft();
        if (this.mode.getValue(true) == Mode.Lock) {
            if (mc.currentScreen instanceof GuiGameOver) {
                mc.displayGuiScreen(null);
            }
            mc.player.setHealth(20.0f);
            mc.player.getFoodStats().setFoodLevel(20);
            mc.player.isDead = false;
        }
    }

    public static enum Mode {
        Entity,
        Riding,
        Lock,
        Portal;

    }
}

