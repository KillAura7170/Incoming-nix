/*    */ package me.alpha432.oyvey.features.modules.misc;
/*    */ 
/*    */ import me.alpha432.oyvey.event.events.PacketEvent;
/*    */ import me.alpha432.oyvey.features.modules.Module;
/*    */ import me.alpha432.oyvey.features.setting.Setting;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BetterPortals
/*    */   extends Module
/*    */ {
/* 14 */   private static BetterPortals INSTANCE = new BetterPortals();
/* 15 */   public Setting<Boolean> portalChat = register(new Setting("Chat", Boolean.valueOf(true), "Allows you to chat in portals."));
/* 16 */   public Setting<Boolean> godmode = register(new Setting("Godmode", Boolean.valueOf(false), "Portal Godmode."));
/* 17 */   public Setting<Boolean> fastPortal = register(new Setting("FastPortal", Boolean.valueOf(false)));
/* 18 */   public Setting<Integer> cooldown = register(new Setting("Cooldown", Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(10), v -> ((Boolean)this.fastPortal.getValue(true)).booleanValue(), "Portal cooldown."));
/* 19 */   public Setting<Integer> time = register(new Setting("Time", Integer.valueOf(5), Integer.valueOf(0), Integer.valueOf(80), v -> ((Boolean)this.fastPortal.getValue(true)).booleanValue(), "Time in Portal"));
/*    */   
/*    */   public BetterPortals() {
/* 22 */     super("BetterPortals", "Tweaks for Portals", Module.Category.MISC, true, false, false);
/* 23 */     setInstance();
/*    */   }
/*    */   
/*    */   public static BetterPortals getInstance() {
/* 27 */     if (INSTANCE == null) {
/* 28 */       INSTANCE = new BetterPortals();
/*    */     }
/* 30 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 34 */     INSTANCE = this;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDisplayInfo() {
/* 39 */     if (((Boolean)this.godmode.getValue(true)).booleanValue()) {
/* 40 */       return "Godmode";
/*    */     }
/* 42 */     return null;
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send event) {
/* 47 */     if (event.getStage() == 0 && ((Boolean)this.godmode.getValue(true)).booleanValue() && event.getPacket() instanceof net.minecraft.network.play.client.CPacketConfirmTeleport)
/* 48 */       event.setCanceled(true); 
/*    */   }
/*    */ }


/* Location:              C:\Users\KillAura\Desktop\Incoming 1.0.2 libs.jar!\me\alpha432\oyvey\features\modules\misc\BetterPortals.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */