/*    */ package me.alpha432.oyvey.features.modules.player;
/*    */ 
/*    */ import me.alpha432.oyvey.event.events.PacketEvent;
/*    */ import me.alpha432.oyvey.features.modules.Module;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PosDesync
/*    */   extends Module
/*    */ {
/*    */   public PosDesync() {
/* 15 */     super("PosDesync", "pd", Module.Category.PLAYER, true, false, false);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send event) {
/* 20 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer.Position || event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer.PositionRotation || event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer.Rotation || event.getPacket() instanceof net.minecraft.network.play.client.CPacketConfirmTeleport)
/* 21 */       event.setCanceled(true); 
/*    */   }
/*    */ }


/* Location:              C:\Users\KillAura\Desktop\Incoming 1.0.2 libs.jar!\me\alpha432\oyvey\features\modules\player\PosDesync.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */