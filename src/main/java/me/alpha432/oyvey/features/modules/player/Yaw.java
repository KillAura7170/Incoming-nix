/*
 * Decompiled with CFR 0.151.
 */
package me.alpha432.oyvey.features.modules.player;

import java.util.Objects;

import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.event.events.UpdateWalkingPlayerEvent;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Yaw
extends Module {
    public Setting<Boolean> lockYaw = this.register(new Setting<Boolean>("LockYaw", false));
    public Setting<Boolean> byDirection = this.register(new Setting<Boolean>("ByDirection", false));
    public Setting<Direction> direction = this.register(new Setting<Object>("Direction", (Object)Direction.NORTH, v -> this.byDirection.getValue(true)));
    public Setting<Integer> yaw = this.register(new Setting<Object>("Yaw", Integer.valueOf(0), Integer.valueOf(-180), Integer.valueOf(180), v -> this.byDirection.getValue(true) == false));
    public Setting<Boolean> lockPitch = this.register(new Setting<Boolean>("LockPitch", false));
    public Setting<Integer> pitch = this.register(new Setting<Integer>("Pitch", 0, -180, 180));

    public Yaw() {
        super("Yaw", "Locks your yaw", Module.Category.PLAYER, true, false, false);
    }

    @SubscribeEvent
    public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent event) {
        if (this.lockYaw.getValue(true).booleanValue()) {
            if (this.byDirection.getValue(true).booleanValue()) {
                switch (this.direction.getValue(true)) {
                    case NORTH: {
                        this.setYaw(180);
                        break;
                    }
                    case NE: {
                        this.setYaw(225);
                        break;
                    }
                    case EAST: {
                        this.setYaw(270);
                        break;
                    }
                    case SE: {
                        this.setYaw(315);
                        break;
                    }
                    case SOUTH: {
                        this.setYaw(0);
                        break;
                    }
                    case SW: {
                        this.setYaw(45);
                        break;
                    }
                    case WEST: {
                        this.setYaw(90);
                        break;
                    }
                    case NW: {
                        this.setYaw(135);
                    }
                }
            } else {
                this.setYaw(this.yaw.getValue(true));
            }
        }
        if (this.lockPitch.getValue(true).booleanValue()) {
            if (Yaw.mc.player.isRiding()) {
                Objects.requireNonNull(Yaw.mc.player.getRidingEntity()).rotationPitch = this.pitch.getValue(true).intValue();
            }
            Yaw.mc.player.rotationPitch = this.pitch.getValue(true).intValue();
        }
    }

    private void setYaw(int yaw) {
        if (Yaw.mc.player.isRiding()) {
            Objects.requireNonNull(Yaw.mc.player.getRidingEntity()).rotationYaw = yaw;
        }
        Yaw.mc.player.rotationYaw = yaw;
    }

    public static enum Direction {
        NORTH,
        NE,
        EAST,
        SE,
        SOUTH,
        SW,
        WEST,
        NW;

    }
}

