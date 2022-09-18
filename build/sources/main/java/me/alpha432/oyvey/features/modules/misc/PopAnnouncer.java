package me.alpha432.oyvey.features.modules.misc;

import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;

public class PopAnnouncer extends Module {
  public PopAnnouncer() {
    super("PopAnnouncer", "Announce pop", Module.Category.MISC, true, false, false);
  }
  
  public void doAnnounce(EntityPlayer entity) {
    mc.player.connection.sendPacket((Packet)new CPacketChatMessage("Just keep popping " + entity.getName()));
  }
}
