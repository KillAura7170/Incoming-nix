/*
 * Decompiled with CFR 0.151.
 */
package me.alpha432.oyvey.features.modules.movement;

import java.text.DecimalFormat;
import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

public class Step
extends Module {
    private static Step instance;
    Setting<Double> height = this.register(new Setting<Double>("Height", 2.5, 0.5, 2.5));
    Setting<Mode> mode = this.register(new Setting<Mode>("Mode", Mode.Vanilla));
    private int ticks = 0;

    public Step() {
        super("Step", "s", Module.Category.MOVEMENT, true, false, false);
        instance = this;
    }

    public static Step getInstance() {
        if (instance == null) {
            instance = new Step();
        }
        return instance;
    }

    @Override
    public void onToggle() {
        Step.mc.player.stepHeight = 0.6f;
    }

    @Override
    public void onUpdate() {
        if (Step.mc.world == null || Step.mc.player == null) {
            return;
        }
        if (Step.mc.player.isInWater() || Step.mc.player.isInLava() || Step.mc.player.isOnLadder() || Step.mc.gameSettings.keyBindJump.isKeyDown()) {
            return;
        }
        if (OyVey.moduleManager.getModuleByClass(Speed.class).isEnabled()) {
            return;
        }
        if (this.mode.getValue(true) == Mode.Normal) {
            double[] dir = Step.forward(0.1);
            boolean twofive = false;
            boolean two = false;
            boolean onefive = false;
            boolean one = false;
            if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 2.6, dir[1])).isEmpty() && !Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 2.4, dir[1])).isEmpty()) {
                twofive = true;
            }
            if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 2.1, dir[1])).isEmpty() && !Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 1.9, dir[1])).isEmpty()) {
                two = true;
            }
            if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 1.6, dir[1])).isEmpty() && !Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 1.4, dir[1])).isEmpty()) {
                onefive = true;
            }
            if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 1.0, dir[1])).isEmpty() && !Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 0.6, dir[1])).isEmpty()) {
                one = true;
            }
            if (Step.mc.player.collidedHorizontally && (Step.mc.player.moveForward != 0.0f || Step.mc.player.moveStrafing != 0.0f) && Step.mc.player.onGround) {
                int i;
                if (one && this.height.getValue(true) >= 1.0) {
                    double[] oneOffset = new double[]{0.42, 0.753};
                    for (i = 0; i < oneOffset.length; ++i) {
                        Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + oneOffset[i], Step.mc.player.posZ, Step.mc.player.onGround));
                    }
                    Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 1.0, Step.mc.player.posZ);
                    this.ticks = 1;
                }
                if (onefive && this.height.getValue(true) >= 1.5) {
                    double[] oneFiveOffset = new double[]{0.42, 0.75, 1.0, 1.16, 1.23, 1.2};
                    for (i = 0; i < oneFiveOffset.length; ++i) {
                        Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + oneFiveOffset[i], Step.mc.player.posZ, Step.mc.player.onGround));
                    }
                    Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 1.5, Step.mc.player.posZ);
                    this.ticks = 1;
                }
                if (two && this.height.getValue(true) >= 2.0) {
                    double[] twoOffset = new double[]{0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43};
                    for (i = 0; i < twoOffset.length; ++i) {
                        Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + twoOffset[i], Step.mc.player.posZ, Step.mc.player.onGround));
                    }
                    Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 2.0, Step.mc.player.posZ);
                    this.ticks = 2;
                }
                if (twofive && this.height.getValue(true) >= 2.5) {
                    double[] twoFiveOffset = new double[]{0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907};
                    for (i = 0; i < twoFiveOffset.length; ++i) {
                        Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + twoFiveOffset[i], Step.mc.player.posZ, Step.mc.player.onGround));
                    }
                    Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 2.5, Step.mc.player.posZ);
                    this.ticks = 2;
                }
            }
        }
        if (this.mode.getValue(true) == Mode.Vanilla) {
            DecimalFormat df = new DecimalFormat("#");
            Step.mc.player.stepHeight = Float.parseFloat(df.format(this.height.getValue(true)));
        }
    }

    @Override
    public String getDisplayInfo() {
        return this.mode.currentEnumName();
    }

    @Override
    public void onDisable() {
        Step.mc.player.stepHeight = 0.5f;
    }

    public static double[] forward(double speed) {
        float forward = Step.mc.player.movementInput.moveForward;
        float side = Step.mc.player.movementInput.moveStrafe;
        float yaw = Step.mc.player.prevRotationYaw + (Step.mc.player.rotationYaw - Step.mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += (float)(forward > 0.0f ? -45 : 45);
            } else if (side < 0.0f) {
                yaw += (float)(forward > 0.0f ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        double posX = (double)forward * speed * cos + (double)side * speed * sin;
        double posZ = (double)forward * speed * sin - (double)side * speed * cos;
        return new double[]{posX, posZ};
    }

    public static enum Mode {
        Vanilla,
        Normal;

    }
}

