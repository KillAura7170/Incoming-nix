/*
 * Decompiled with CFR 0.151.
 */
package me.alpha432.oyvey.features.modules.misc;

import com.google.common.eventbus.Subscribe;

import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.util.Wrapper;
import me.alpha432.oyvey.event.events.PacketEvent;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiDesync
extends Module {
    private final Setting<Boolean> crystal = this.register(new Setting<Boolean>("Crystal", true));
    private final Setting<Boolean> sneakp = this.register(new Setting<Boolean>("SneakPacket", false));

    public AntiDesync() {
        super("AntiDesync", "ad", Module.Category.MISC, true, false, false);
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent event) {
        SPacketSoundEffect packet;
        if (this.crystal.getValue(true).booleanValue() && event.getPacket() instanceof SPacketSoundEffect && (packet = (SPacketSoundEffect)event.getPacket()).getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
            try {
                for (Entity e : Wrapper.getWorld().loadedEntityList) {
                    if (!(e instanceof EntityEnderCrystal) || !(e.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0)) continue;
                    e.setDead();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    @Subscribe
    public void onUpdate() {
        if (this.sneakp.getValue(true).booleanValue()) {
            AntiDesync.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AntiDesync.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            AntiDesync.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AntiDesync.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
}

