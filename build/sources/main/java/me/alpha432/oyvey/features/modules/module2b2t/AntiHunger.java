package me.alpha432.oyvey.features.modules.module2b2t;

import me.alpha432.oyvey.event.events.PacketEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.setting.Setting;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiHunger extends Module {
    public Setting<Boolean> cancelSprint = register(new Setting<>("CancelSprint", true));
    public Setting<Boolean> ground = register(new Setting<>("Ground", true));

    public AntiHunger() {
        super("AntiHunger", "Prevents you from getting Hungry.", Category.MODULE2B2T, true, false, false);
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (this.ground.getValue(true) && event.getPacket() instanceof CPacketPlayer) {
            CPacketPlayer packet = event.getPacket();
            packet.onGround = (mc.player.fallDistance >= 0.0F || mc.playerController.isHittingBlock);
        }
        if (this.cancelSprint.getValue(true) && event.getPacket() instanceof CPacketEntityAction) {
            CPacketEntityAction packet = event.getPacket();
            if (packet.getAction() == CPacketEntityAction.Action.START_SPRINTING || packet.getAction() == CPacketEntityAction.Action.STOP_SPRINTING)
                event.setCanceled(true);
        }
    }
}