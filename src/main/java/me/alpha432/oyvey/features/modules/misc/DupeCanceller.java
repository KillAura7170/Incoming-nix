/*    */ package me.alpha432.oyvey.features.modules.misc;
/*    */ 
/*    */ import me.alpha432.oyvey.event.events.PacketEvent;
/*    */ import me.alpha432.oyvey.features.modules.Module;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DupeCanceller
/*    */   extends Module
/*    */ {
/* 20 */   private int PacketsCanelled = 0;
/*    */   
/*    */   public DupeCanceller() {
/* 23 */     super("DupeCancel", "dc", Module.Category.MISC, true, false, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 28 */     super.onDisable();
/* 29 */     this.PacketsCanelled = 0;
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send event) {
/* 34 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketInput) {
/* 35 */       event.setCanceled(true);
/*    */     }
/* 37 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer.Position) {
/* 38 */       event.setCanceled(true);
/*    */     }
/* 40 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer.PositionRotation) {
/* 41 */       event.setCanceled(true);
/*    */     }
/* 43 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer.Rotation) {
/* 44 */       event.setCanceled(true);
/*    */     }
/* 46 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayerAbilities) {
/* 47 */       event.setCanceled(true);
/*    */     }
/* 49 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayerDigging) {
/* 50 */       event.setCanceled(true);
/*    */     }
/* 52 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayerTryUseItem) {
/* 53 */       event.setCanceled(true);
/*    */     }
/* 55 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock) {
/* 56 */       event.setCanceled(true);
/*    */     }
/* 58 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketUseEntity) {
/* 59 */       event.setCanceled(true);
/*    */     }
/* 61 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketVehicleMove)
/* 62 */       event.setCanceled(true); 
/*    */   }
/*    */ }


/* Location:              C:\Users\KillAura\Desktop\Incoming 1.0.2 libs.jar!\me\alpha432\oyvey\features\modules\misc\DupeCanceller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */