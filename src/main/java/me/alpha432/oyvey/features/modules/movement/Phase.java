/*
 * Decompiled with CFR 0.151.
 */
package me.alpha432.oyvey.features.modules.movement;

import com.google.common.eventbus.Subscribe;
import io.netty.util.internal.ConcurrentSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.util.EntityUtil;
import me.alpha432.oyvey.util.Timer;
import me.alpha432.oyvey.event.events.MoveEvent;
import me.alpha432.oyvey.event.events.PacketEvent;
import me.alpha432.oyvey.event.events.PushEvent;
import me.alpha432.oyvey.event.events.UpdateWalkingPlayerEvent;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Phase
extends Module {
    public Setting<Mode> mode = this.register(new Setting<Mode>("Mode", Mode.Phase));
    public Setting<Integer> xMove = this.register(new Setting<Object>("MoveX", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1000), v -> this.mode.getValue(true) == Mode.Phase));
    public Setting<Integer> yMove = this.register(new Setting<Object>("MoveY", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1000), v -> this.mode.getValue(true) == Mode.Phase));
    public Setting<Boolean> sneakpackets = this.register(new Setting<Object>("SneakPacket", Boolean.valueOf(true), v -> this.mode.getValue(true) == Mode.Phase));
    public Setting<Boolean> extra = this.register(new Setting<Object>("ExtraPacket", Boolean.valueOf(true), v -> this.mode.getValue(true) == Mode.Phase));
    public Setting<Integer> offset = this.register(new Setting<Object>("ExtraOffset", Integer.valueOf(1337), Integer.valueOf(-1337), Integer.valueOf(1337), v -> this.mode.getValue(true) == Mode.Phase && this.extra.getValue(true) != false));
    public Setting<Boolean> fallPacket = this.register(new Setting<Object>("FallPacket", Boolean.valueOf(true), v -> this.mode.getValue(true) == Mode.Phase));
    public Setting<Boolean> teleporter = this.register(new Setting<Object>("Teleport", Boolean.valueOf(true), v -> this.mode.getValue(true) == Mode.Phase));
    public Setting<Boolean> boundingBox = this.register(new Setting<Object>("BoundingBox", Boolean.valueOf(true), v -> this.mode.getValue(true) == Mode.Phase));
    public Setting<Integer> teleportConfirm = this.register(new Setting<Object>("TPConfirm", Integer.valueOf(2), Integer.valueOf(0), Integer.valueOf(4), v -> this.mode.getValue(true) == Mode.Phase));
    public Setting<Boolean> ultraPacket = this.register(new Setting<Object>("DoublePacket", Boolean.valueOf(false), v -> this.mode.getValue(true) == Mode.Phase));
    public Setting<Boolean> updates = this.register(new Setting<Object>("Update", Boolean.valueOf(false), v -> this.mode.getValue(true) == Mode.Phase));
    public Setting<Boolean> setOnMove = this.register(new Setting<Object>("SetMove", Boolean.valueOf(false), v -> this.mode.getValue(true) == Mode.Phase));
    public Setting<Boolean> cliperino = this.register(new Setting<Object>("NoClip", Boolean.valueOf(false), v -> this.mode.getValue(true) == Mode.Phase && this.setOnMove.getValue(true) != false));
    public Setting<Boolean> scanPackets = this.register(new Setting<Object>("ScanPackets", Boolean.valueOf(false), v -> this.mode.getValue(true) == Mode.Phase));
    public Setting<Boolean> resetConfirm = this.register(new Setting<Object>("Reset", Boolean.valueOf(false), v -> this.mode.getValue(true) == Mode.Phase));
    public Setting<Boolean> posLook = this.register(new Setting<Object>("PosLook", Boolean.valueOf(false), v -> this.mode.getValue(true) == Mode.Phase));
    public Setting<Boolean> cancel = this.register(new Setting<Object>("Cancel", Boolean.valueOf(false), v -> this.mode.getValue(true) == Mode.Phase && this.posLook.getValue(true) != false));
    public Setting<Boolean> cancelType = this.register(new Setting<Object>("SetYaw", Boolean.valueOf(false), v -> this.mode.getValue(true) == Mode.Phase && this.posLook.getValue(true) != false && this.cancel.getValue(true) != false));
    public Setting<Boolean> onlyY = this.register(new Setting<Object>("OnlyY", Boolean.valueOf(false), v -> this.mode.getValue(true) == Mode.Phase && this.posLook.getValue(true) != false));
    public Setting<Integer> cancelPacket = this.register(new Setting<Object>("Packets", Integer.valueOf(20), Integer.valueOf(0), Integer.valueOf(20), v -> this.mode.getValue(true) == Mode.Phase && this.posLook.getValue(true) != false));
    private final Set<CPacketPlayer> packets = new ConcurrentSet();
    private static Phase INSTANCE = new Phase();
    private boolean teleport = true;
    private int teleportIds = 0;
    private int posLookPackets;
    public Setting<Boolean> flight = this.register(new Setting<Object>("Flight", Boolean.valueOf(true), v -> this.mode.getValue(true) == Mode.Packetfly));
    public Setting<Integer> flightMode = this.register(new Setting<Object>("FMode", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1), v -> this.mode.getValue(true) == Mode.Packetfly));
    public Setting<Boolean> doAntiFactor = this.register(new Setting<Object>("Factorize", Boolean.valueOf(true), v -> this.mode.getValue(true) == Mode.Packetfly));
    public Setting<Double> antiFactor = this.register(new Setting<Object>("AntiFactor", Double.valueOf(1.0), Double.valueOf(0.1), Double.valueOf(3.0), v -> this.mode.getValue(true) == Mode.Packetfly));
    public Setting<Double> extraFactor = this.register(new Setting<Object>("ExtraFactor", Double.valueOf(1.0), Double.valueOf(0.1), Double.valueOf(3.0), v -> this.mode.getValue(true) == Mode.Packetfly));
    public Setting<Boolean> strafeFactor = this.register(new Setting<Object>("StrafeFactor", Boolean.valueOf(true), v -> this.mode.getValue(true) == Mode.Packetfly));
    public Setting<Integer> loops = this.register(new Setting<Object>("Loops", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(10), v -> this.mode.getValue(true) == Mode.Packetfly));
    public Setting<Boolean> clearTeleMap = this.register(new Setting<Object>("ClearMap", Boolean.valueOf(true), v -> this.mode.getValue(true) == Mode.Packetfly));
    public Setting<Integer> mapTime = this.register(new Setting<Object>("ClearTime", Integer.valueOf(30), Integer.valueOf(1), Integer.valueOf(500), v -> this.mode.getValue(true) == Mode.Packetfly));
    public Setting<Boolean> clearIDs = this.register(new Setting<Object>("ClearIDs", Boolean.valueOf(true), v -> this.mode.getValue(true) == Mode.Packetfly));
    public Setting<Boolean> setYaw = this.register(new Setting<Object>("SetYaw", Boolean.valueOf(true), v -> this.mode.getValue(true) == Mode.Packetfly));
    public Setting<Boolean> setID = this.register(new Setting<Object>("SetID", Boolean.valueOf(true), v -> this.mode.getValue(true) == Mode.Packetfly));
    public Setting<Boolean> setMove = this.register(new Setting<Object>("SetMove", Boolean.valueOf(false), v -> this.mode.getValue(true) == Mode.Packetfly));
    public Setting<Boolean> nocliperino = this.register(new Setting<Object>("NoClip", Boolean.valueOf(false), v -> this.mode.getValue(true) == Mode.Packetfly));
    public Setting<Boolean> sendTeleport = this.register(new Setting<Object>("Teleport", Boolean.valueOf(true), v -> this.mode.getValue(true) == Mode.Packetfly));
    public Setting<Boolean> resetID = this.register(new Setting<Object>("ResetID", Boolean.valueOf(true), v -> this.mode.getValue(true) == Mode.Packetfly));
    public Setting<Boolean> setPos = this.register(new Setting<Object>("SetPos", Boolean.valueOf(false), v -> this.mode.getValue(true) == Mode.Packetfly));
    public Setting<Boolean> invalidPacket = this.register(new Setting<Object>("InvalidPacket", Boolean.valueOf(true), v -> this.mode.getValue(true) == Mode.Packetfly));
    private final Map<Integer, IDtime> teleportmap = new ConcurrentHashMap<Integer, IDtime>();
    private int flightCounter = 0;
    private int teleportID = 0;

    public Phase() {
        super("Packetfly", "phase && pfly", Module.Category.MOVEMENT, true, false, false);
        this.setInstance();
    }

    public static Phase getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Phase();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onTick() {
        if (this.mode.getValue(true) == Mode.Packetfly) {
            this.teleportmap.entrySet().removeIf(idTime -> this.clearTeleMap.getValue(true) != false && ((IDtime)idTime.getValue()).getTimer().passedS(this.mapTime.getValue(true).intValue()));
        }
    }

    @Override
    @Subscribe
    public void onUpdate() {
        if (this.sneakpackets.getValue(true).booleanValue()) {
            Phase.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Phase.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            Phase.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Phase.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }

    @Override
    public void onDisable() {
        if (this.mode.getValue(true) == Mode.Phase) {
            this.packets.clear();
            this.posLookPackets = 0;
            if (Phase.mc.player != null) {
                if (this.resetConfirm.getValue(true).booleanValue()) {
                    this.teleportIds = 0;
                }
                Phase.mc.player.noClip = false;
            }
        }
    }

    @Override
    public String getDisplayInfo() {
        return this.mode.currentEnumName();
    }

    @SubscribeEvent
    public void onMove(MoveEvent event) {
        if (this.mode.getValue(true) == Mode.Phase) {
            if (this.setOnMove.getValue(true).booleanValue() && event.getStage() == 0 && !mc.isSingleplayer() && this.mode.getValue(true) == Mode.Phase) {
                event.setX(Phase.mc.player.motionX);
                event.setY(Phase.mc.player.motionY);
                event.setZ(Phase.mc.player.motionZ);
                if (this.cliperino.getValue(true).booleanValue()) {
                    Phase.mc.player.noClip = true;
                }
            }
            if (event.getStage() != 0 || mc.isSingleplayer() || this.mode.getValue(true) != Mode.Phase) {
                return;
            }
            if (!this.boundingBox.getValue(true).booleanValue() && !this.updates.getValue(true).booleanValue()) {
                this.doPhase(event);
            }
        }
        if (this.mode.getValue(true) == Mode.Packetfly && this.setMove.getValue(true).booleanValue() && this.flightCounter != 0) {
            event.setX(Phase.mc.player.motionX);
            event.setY(Phase.mc.player.motionY);
            event.setZ(Phase.mc.player.motionZ);
            if (this.nocliperino.getValue(true).booleanValue() && this.checkHitBoxes()) {
                Phase.mc.player.noClip = true;
            }
        }
    }

    @SubscribeEvent
    public void onPush(PushEvent event) {
        if (this.mode.getValue(true) == Mode.Phase && event.getStage() == 1) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPushOutOfBlocks(PushEvent event) {
        if (this.mode.getValue(true) == Mode.Packetfly && event.getStage() == 1) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onMove(UpdateWalkingPlayerEvent event) {
        if (this.mode.getValue(true) == Mode.Phase) {
            if (Phase.fullNullCheck() || event.getStage() != 0 || this.mode.getValue(true) != Mode.Phase) {
                return;
            }
            if (this.boundingBox.getValue(true).booleanValue()) {
                this.doBoundingBox();
            } else if (this.updates.getValue(true).booleanValue()) {
                this.doPhase(null);
            }
        }
        if (this.mode.getValue(true) == Mode.Packetfly) {
            if (event.getStage() == 1) {
                return;
            }
            Phase.mc.player.setVelocity(0.0, 0.0, 0.0);
            double speed = 0.0;
            boolean checkCollisionBoxes = this.checkHitBoxes();
            double d = Phase.mc.player.movementInput.jump && (checkCollisionBoxes || !EntityUtil.isMoving()) ? (this.flight.getValue(true).booleanValue() && !checkCollisionBoxes ? (this.flightMode.getValue(true) == 0 ? (this.resetCounter(10) ? -0.032 : 0.062) : (this.resetCounter(20) ? -0.032 : 0.062)) : 0.062) : (Phase.mc.player.movementInput.sneak ? -0.062 : (!checkCollisionBoxes ? (this.resetCounter(4) ? (this.flight.getValue(true).booleanValue() ? -0.04 : 0.0) : 0.0) : (speed = 0.0)));
            if (this.doAntiFactor.getValue(true).booleanValue() && checkCollisionBoxes && EntityUtil.isMoving() && speed != 0.0) {
                speed /= this.antiFactor.getValue(true).doubleValue();
            }
            double[] strafing = this.getMotion(this.strafeFactor.getValue(true) != false && checkCollisionBoxes ? 0.031 : 0.26);
            for (int i = 1; i < this.loops.getValue(true) + 1; ++i) {
                Phase.mc.player.motionX = strafing[0] * (double)i * this.extraFactor.getValue(true);
                Phase.mc.player.motionY = speed * (double)i;
                Phase.mc.player.motionZ = strafing[1] * (double)i * this.extraFactor.getValue(true);
                this.sendPackets(Phase.mc.player.motionX, Phase.mc.player.motionY, Phase.mc.player.motionZ, this.sendTeleport.getValue(true));
            }
        }
    }

    private void doPhase(MoveEvent event) {
        if (this.mode.getValue(true) == Mode.Phase) {
            if (!this.boundingBox.getValue(true).booleanValue()) {
                double[] dirSpeed = this.getMotion(this.teleport ? (double)this.yMove.getValue(true).intValue() / 10000.0 : (double)(this.yMove.getValue(true) - 1) / 10000.0);
                double posX = Phase.mc.player.posX + dirSpeed[0];
                double posY = Phase.mc.player.posY + (Phase.mc.gameSettings.keyBindJump.isKeyDown() ? (this.teleport ? (double)this.yMove.getValue(true).intValue() / 10000.0 : (double)(this.yMove.getValue(true) - 1) / 10000.0) : 1.0E-8) - (Phase.mc.gameSettings.keyBindSneak.isKeyDown() ? (this.teleport ? (double)this.yMove.getValue(true).intValue() / 10000.0 : (double)(this.yMove.getValue(true) - 1) / 10000.0) : 2.0E-8);
                double posZ = Phase.mc.player.posZ + dirSpeed[1];
                CPacketPlayer.PositionRotation packetPlayer = new CPacketPlayer.PositionRotation(posX, posY, posZ, Phase.mc.player.rotationYaw, Phase.mc.player.rotationPitch, false);
                this.packets.add((CPacketPlayer)packetPlayer);
                Phase.mc.player.connection.sendPacket((Packet)packetPlayer);
                if (this.teleportConfirm.getValue(true) != 3) {
                    Phase.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.teleportIds - 1));
                    ++this.teleportIds;
                }
                if (this.extra.getValue(true).booleanValue()) {
                    CPacketPlayer.PositionRotation packet = new CPacketPlayer.PositionRotation(Phase.mc.player.posX, (double)this.offset.getValue(true).intValue() + Phase.mc.player.posY, Phase.mc.player.posZ, Phase.mc.player.rotationYaw, Phase.mc.player.rotationPitch, true);
                    this.packets.add((CPacketPlayer)packet);
                    Phase.mc.player.connection.sendPacket((Packet)packet);
                }
                if (this.teleportConfirm.getValue(true) != 1) {
                    Phase.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.teleportIds + 1));
                    ++this.teleportIds;
                }
                if (this.ultraPacket.getValue(true).booleanValue()) {
                    CPacketPlayer.PositionRotation packet2 = new CPacketPlayer.PositionRotation(posX, posY, posZ, Phase.mc.player.rotationYaw, Phase.mc.player.rotationPitch, false);
                    this.packets.add((CPacketPlayer)packet2);
                    Phase.mc.player.connection.sendPacket((Packet)packet2);
                }
                if (this.teleportConfirm.getValue(true) == 4) {
                    Phase.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.teleportIds));
                    ++this.teleportIds;
                }
                if (this.fallPacket.getValue(true).booleanValue()) {
                    Phase.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Phase.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                }
                Phase.mc.player.setPosition(posX, posY, posZ);
                this.teleport = this.teleporter.getValue(true) == false || !this.teleport;
                boolean bl = this.teleport;
                if (event != null) {
                    event.setX(0.0);
                    event.setY(0.0);
                    event.setX(0.0);
                } else {
                    Phase.mc.player.motionX = 0.0;
                    Phase.mc.player.motionY = 0.0;
                    Phase.mc.player.motionZ = 0.0;
                }
            }
            Phase.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Phase.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            Phase.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Phase.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }

    private void doBoundingBox() {
        if (this.mode.getValue(true) == Mode.Phase) {
            double[] dirSpeed = this.getMotion(this.teleport ? (double)0.0225f : (double)0.0224f);
            Phase.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(Phase.mc.player.posX + dirSpeed[0], Phase.mc.player.posY + (Phase.mc.gameSettings.keyBindJump.isKeyDown() ? (this.teleport ? 0.0625 : 0.0624) : 1.0E-8) - (Phase.mc.gameSettings.keyBindSneak.isKeyDown() ? (this.teleport ? 0.0625 : 0.0624) : 2.0E-8), Phase.mc.player.posZ + dirSpeed[1], Phase.mc.player.rotationYaw, Phase.mc.player.rotationPitch, false));
            Phase.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(Phase.mc.player.posX, -1337.0, Phase.mc.player.posZ, Phase.mc.player.rotationYaw, Phase.mc.player.rotationPitch, true));
            Phase.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Phase.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
            Phase.mc.player.setPosition(Phase.mc.player.posX + dirSpeed[0], Phase.mc.player.posY + (Phase.mc.gameSettings.keyBindJump.isKeyDown() ? (this.teleport ? 0.0625 : 0.0624) : 1.0E-8) - (Phase.mc.gameSettings.keyBindSneak.isKeyDown() ? (this.teleport ? 0.0625 : 0.0624) : 2.0E-8), Phase.mc.player.posZ + dirSpeed[1]);
            this.teleport = !this.teleport;
            Phase.mc.player.motionZ = 0.0;
            Phase.mc.player.motionY = 0.0;
            Phase.mc.player.motionX = 0.0;
            Phase.mc.player.noClip = this.teleport;
        }
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        CPacketPlayer packet;
        if (this.mode.getValue(true) == Mode.Packetfly && event.getPacket() instanceof CPacketPlayer && !this.packets.remove(packet = (CPacketPlayer)event.getPacket())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive event) {
        if (this.mode.getValue(true) == Mode.Phase && this.posLook.getValue(true).booleanValue() && event.getPacket() instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
            if (Phase.mc.player.isEntityAlive() && Phase.mc.world.isBlockLoaded(new BlockPos(Phase.mc.player.posX, Phase.mc.player.posY, Phase.mc.player.posZ)) && !(Phase.mc.currentScreen instanceof GuiDownloadTerrain)) {
                if (this.teleportIds <= 0) {
                    this.teleportIds = packet.getTeleportId();
                }
                if (this.cancel.getValue(true).booleanValue() && this.cancelType.getValue(true).booleanValue()) {
                    packet.yaw = Phase.mc.player.rotationYaw;
                    packet.pitch = Phase.mc.player.rotationPitch;
                    return;
                }
                if (!(!this.cancel.getValue(true).booleanValue() || this.posLookPackets < this.cancelPacket.getValue(true) || this.onlyY.getValue(true).booleanValue() && (Phase.mc.gameSettings.keyBindForward.isKeyDown() || Phase.mc.gameSettings.keyBindRight.isKeyDown() || Phase.mc.gameSettings.keyBindLeft.isKeyDown() || Phase.mc.gameSettings.keyBindBack.isKeyDown()))) {
                    this.posLookPackets = 0;
                    event.setCanceled(true);
                }
                ++this.posLookPackets;
            }
        }
        if (this.mode.getValue(true) == Mode.Packetfly && event.getPacket() instanceof SPacketPlayerPosLook && !Phase.fullNullCheck()) {
            BlockPos pos;
            SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
            if (Phase.mc.player.isEntityAlive() && Phase.mc.world.isBlockLoaded(pos = new BlockPos(Phase.mc.player.posX, Phase.mc.player.posY, Phase.mc.player.posZ), false) && !(Phase.mc.currentScreen instanceof GuiDownloadTerrain) && this.clearIDs.getValue(true).booleanValue()) {
                this.teleportmap.remove(packet.getTeleportId());
            }
            if (this.setYaw.getValue(true).booleanValue()) {
                packet.yaw = Phase.mc.player.rotationYaw;
                packet.pitch = Phase.mc.player.rotationPitch;
            }
            if (this.setID.getValue(true).booleanValue()) {
                this.teleportID = packet.getTeleportId();
            }
        }
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Send event) {
        if (this.mode.getValue(true) == Mode.Phase && this.scanPackets.getValue(true).booleanValue() && event.getPacket() instanceof CPacketPlayer) {
            CPacketPlayer packetPlayer = (CPacketPlayer)event.getPacket();
            if (this.packets.contains(packetPlayer)) {
                this.packets.remove(packetPlayer);
            } else {
                event.setCanceled(true);
            }
        }
    }

    private double[] getMotion(double speed) {
        float moveForward = Phase.mc.player.movementInput.moveForward;
        float moveStrafe = Phase.mc.player.movementInput.moveStrafe;
        float rotationYaw = Phase.mc.player.prevRotationYaw + (Phase.mc.player.rotationYaw - Phase.mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
        if (moveForward != 0.0f) {
            if (moveStrafe > 0.0f) {
                rotationYaw += (float)(moveForward > 0.0f ? -45 : 45);
            } else if (moveStrafe < 0.0f) {
                rotationYaw += (float)(moveForward > 0.0f ? 45 : -45);
            }
            moveStrafe = 0.0f;
            if (moveForward > 0.0f) {
                moveForward = 1.0f;
            } else if (moveForward < 0.0f) {
                moveForward = -1.0f;
            }
        }
        double posX = (double)moveForward * speed * -Math.sin(Math.toRadians(rotationYaw)) + (double)moveStrafe * speed * Math.cos(Math.toRadians(rotationYaw));
        double posZ = (double)moveForward * speed * Math.cos(Math.toRadians(rotationYaw)) - (double)moveStrafe * speed * -Math.sin(Math.toRadians(rotationYaw));
        return new double[]{posX, posZ};
    }

    private boolean checkHitBoxes() {
        return !Phase.mc.world.getCollisionBoxes((Entity)Phase.mc.player, Phase.mc.player.getEntityBoundingBox().expand(-0.0625, -0.0625, -0.0625)).isEmpty();
    }

    private boolean resetCounter(int counter) {
        if (++this.flightCounter >= counter) {
            this.flightCounter = 0;
            return true;
        }
        return false;
    }

    private void sendPackets(double x, double y, double z, boolean teleport) {
        Vec3d vec = new Vec3d(x, y, z);
        Vec3d position = Phase.mc.player.getPositionVector().add(vec);
        Vec3d outOfBoundsVec = this.outOfBoundsVec(vec, position);
        this.packetSender((CPacketPlayer)new CPacketPlayer.Position(position.x, position.y, position.z, Phase.mc.player.onGround));
        if (this.invalidPacket.getValue(true).booleanValue()) {
            this.packetSender((CPacketPlayer)new CPacketPlayer.Position(outOfBoundsVec.x, outOfBoundsVec.y, outOfBoundsVec.z, Phase.mc.player.onGround));
        }
        if (this.setPos.getValue(true).booleanValue()) {
            Phase.mc.player.setPosition(position.x, position.y, position.z);
        }
        this.teleportPacket(position, teleport);
    }

    private void teleportPacket(Vec3d pos, boolean shouldTeleport) {
        if (shouldTeleport) {
            Phase.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(++this.teleportID));
            this.teleportmap.put(this.teleportID, new IDtime(pos, new Timer()));
        }
    }

    private Vec3d outOfBoundsVec(Vec3d offset, Vec3d position) {
        return position.add(0.0, 1337.0, 0.0);
    }

    private void packetSender(CPacketPlayer packet) {
        this.packets.add(packet);
        Phase.mc.player.connection.sendPacket((Packet)packet);
    }

    private void clean() {
        this.teleportmap.clear();
        this.flightCounter = 0;
        if (this.resetID.getValue(true).booleanValue()) {
            this.teleportID = 0;
        }
        this.packets.clear();
    }

    public static enum Mode {
        Phase,
        Packetfly,
        Alternative;

    }

    public static class IDtime {
        private final Vec3d pos;
        private final Timer timer;

        public IDtime(Vec3d pos, Timer timer) {
            this.pos = pos;
            this.timer = timer;
            this.timer.reset();
        }

        public Vec3d getPos() {
            return this.pos;
        }

        public Timer getTimer() {
            return this.timer;
        }
    }
}

