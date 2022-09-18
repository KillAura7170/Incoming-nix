/*
 * Decompiled with CFR 0.151.
 */
package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.event.events.PacketEvent;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Animations
extends Module {
    private final Setting<Mode> mode = this.register(new Setting<Mode>("Mode", Mode.Low));
    private final Setting<Swing> swing = this.register(new Setting<Swing>("Swing", Swing.Mainhand));
    private final Setting<Boolean> slow = this.register(new Setting<Boolean>("Slow", true));

    public Animations() {
        super("Animations", "Change animations", Module.Category.RENDER, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (Animations.nullCheck()) {
            return;
        }
        if (this.swing.getValue(true) == Swing.Offhand) {
            Animations.mc.player.swingingHand = EnumHand.OFF_HAND;
        }
        if (this.mode.getValue(true) == Mode.High && (double)Animations.mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9) {
            Animations.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
            Animations.mc.entityRenderer.itemRenderer.itemStackMainHand = Animations.mc.player.getHeldItemMainhand();
        }
    }

    @Override
    public void onEnable() {
        if (this.slow.getValue(true).booleanValue()) {
            Animations.mc.player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 255000));
        }
    }

    @Override
    public void onDisable() {
        if (this.slow.getValue(true).booleanValue()) {
            Animations.mc.player.removePotionEffect(MobEffects.MINING_FATIGUE);
        }
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        Object raw = event.getPacket();
        if (raw instanceof CPacketAnimation) {
            CPacketAnimation packet = (CPacketAnimation)raw;
            if (this.swing.getValue(true) == Swing.Packet) {
                event.setCanceled(true);
            }
        }
    }

    private static enum Mode {
        Low,
        High;

    }

    private static enum Swing {
        Mainhand,
        Offhand,
        Packet;

    }
}

