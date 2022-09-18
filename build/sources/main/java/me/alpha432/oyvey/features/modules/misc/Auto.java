/*
 * Decompiled with CFR 0.151.
 */
package me.alpha432.oyvey.features.modules.misc;

import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.util.MathUtil;
import me.alpha432.oyvey.util.Timer;
import me.alpha432.oyvey.event.events.PacketEvent;
import me.alpha432.oyvey.features.command.Command;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Auto
extends Module {
    private static Auto INSTANCE = new Auto();
    public Setting<Mode> mode = this.register(new Setting<Mode>("Mode", Mode.Reconnect));
    private final Setting<Boolean> logtrue = this.register(new Setting<Boolean>("Log", Boolean.valueOf(false), v -> this.mode.getValue(true) == Mode.Log));
    private final Setting<Float> health = this.register(new Setting<Object>("Health", Float.valueOf(16.0f), Float.valueOf(0.1f), Float.valueOf(36.0f), v -> this.mode.getValue(true) == Mode.Log && this.logtrue.getValue(true) != false));
    private final Setting<Boolean> bed = this.register(new Setting<Object>("Beds", Boolean.valueOf(true), v -> this.mode.getValue(true) == Mode.Log && this.logtrue.getValue(true) != false));
    private final Setting<Float> range = this.register(new Setting<Object>("BedRange", Float.valueOf(6.0f), Float.valueOf(0.1f), Float.valueOf(36.0f), v -> this.mode.getValue(true) == Mode.Log && this.logtrue.getValue(true) != false && this.bed.getValue(true) != false));
    private final Setting<Boolean> logout = this.register(new Setting<Object>("LogoutOff", Boolean.valueOf(true), v -> this.mode.getValue(true) == Mode.Log && this.logtrue.getValue(true) != false));
    private final Setting<Boolean> reconnecttrue = this.register(new Setting<Boolean>("Reconnect", Boolean.valueOf(false), v -> this.mode.getValue(true) == Mode.Reconnect && this.logtrue.getValue(true) == false));
    private final Setting<Integer> delay = this.register(new Setting<Object>("Delay", Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(15), v -> this.mode.getValue(true) == Mode.Reconnect && this.reconnecttrue.getValue(true) != false));
    private static ServerData serverData;
    private final Setting<Boolean> respawntrue = this.register(new Setting<Object>("Respawn", Boolean.valueOf(false), v -> this.mode.getValue(true) == Mode.Respawn));
    public Setting<Boolean> antiDeathScreen = this.register(new Setting<Object>("AntiDeathScreen", Boolean.valueOf(true), v -> this.mode.getValue(true) == Mode.Respawn && this.respawntrue.getValue(true) != false));
    public Setting<Boolean> deathCoords = this.register(new Setting<Object>("DeathCoords", Boolean.valueOf(false), v -> this.mode.getValue(true) == Mode.Respawn && this.respawntrue.getValue(true) != false));
    public Setting<Boolean> respawn = this.register(new Setting<Object>("Respawn", Boolean.valueOf(true), v -> this.mode.getValue(true) == Mode.Respawn && this.respawntrue.getValue(true) != false));

    public Auto() {
        super("Auto", "a", Module.Category.MISC, true, false, false);
        this.setInstance();
    }

    public static Auto getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Auto();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onTick() {
        if (!Auto.nullCheck() && Auto.mc.player.getHealth() <= this.health.getValue(true).floatValue()) {
            Auto.mc.player.connection.sendPacket((Packet)new SPacketDisconnect((ITextComponent)new TextComponentString("AutoLogged")));
            if (this.logout.getValue(true).booleanValue()) {
                this.disable();
            }
        }
    }

    @SubscribeEvent
    public void onReceivePacket(PacketEvent.Receive event) {
        SPacketBlockChange packet;
        if (event.getPacket() instanceof SPacketBlockChange && this.bed.getValue(true).booleanValue() && (packet = (SPacketBlockChange)event.getPacket()).getBlockState().getBlock() == Blocks.BED && Auto.mc.player.getDistanceSqToCenter(packet.getBlockPosition()) <= MathUtil.square(this.range.getValue(true).floatValue())) {
            Auto.mc.player.connection.sendPacket((Packet)new SPacketDisconnect((ITextComponent)new TextComponentString("Logged")));
            if (this.logout.getValue(true).booleanValue()) {
                this.disable();
            }
        }
    }

    @SubscribeEvent
    public void sendPacket(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiDisconnected) {
            this.updateLastConnectedServer();
            GuiDisconnected disconnected = (GuiDisconnected)event.getGui();
            event.setGui((GuiScreen)new GuiDisconnectedHook(disconnected));
        }
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        this.updateLastConnectedServer();
    }

    public void updateLastConnectedServer() {
        ServerData data = mc.getCurrentServerData();
        if (data != null) {
            serverData = data;
        }
    }

    @SubscribeEvent
    public void onDisplayDeathScreen(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiGameOver) {
            if (this.deathCoords.getValue(true).booleanValue() && event.getGui() instanceof GuiGameOver) {
                Command.sendMessage(String.format("You died at x %d y %d z %d", (int)Auto.mc.player.posX, (int)Auto.mc.player.posY, (int)Auto.mc.player.posZ));
            }
            if (this.respawn.getValue(true) != false && Auto.mc.player.getHealth() <= 0.0f || this.antiDeathScreen.getValue(true).booleanValue() && Auto.mc.player.getHealth() > 0.0f) {
                event.setCanceled(true);
                Auto.mc.player.respawnPlayer();
            }
        }
    }

    public static enum Mode {
        Log,
        Reconnect,
        Respawn;

    }

    private class GuiDisconnectedHook
    extends GuiDisconnected {
        private final Timer timer;

        public GuiDisconnectedHook(GuiDisconnected disconnected) {
            super(disconnected.parentScreen, disconnected.reason, disconnected.message);
            this.timer = new Timer();
            this.timer.reset();
        }

        public void updateScreen() {
            if (this.timer.passedS(((Integer)Auto.this.delay.getValue(true)).intValue())) {
                this.mc.displayGuiScreen((GuiScreen)new GuiConnecting(this.parentScreen, this.mc, serverData == null ? this.mc.currentServerData : serverData));
            }
        }

        public void drawScreen(int mouseX, int mouseY, float partialTicks) {
            super.drawScreen(mouseX, mouseY, partialTicks);
            String s = "Reconnecting in " + MathUtil.round((double)((long)((Integer)Auto.this.delay.getValue(true) * 1000) - this.timer.getPassedTimeMs()) / 1000.0, 1);
            Auto.this.renderer.drawString(s, this.width / 2 - Auto.this.renderer.getStringWidth(s) / 2, this.height - 16, 0xFFFFFF, true);
        }
    }
}

