/*    */ package me.alpha432.oyvey.features.modules.misc;
/*    */ 
/*    */ import me.alpha432.oyvey.features.modules.Module;
/*    */ import me.alpha432.oyvey.features.setting.Setting;
/*    */ import net.minecraft.network.Packet;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketLogger
/*    */   extends Module
/*    */ {
/*    */   private Packet[] packets;
/* 13 */   public Setting<Boolean> incoming = register(new Setting("Incoming", Boolean.valueOf(true)));
/* 14 */   public Setting<Boolean> outgoing = register(new Setting("Outgoing", Boolean.valueOf(true)));
/* 15 */   public Setting<Boolean> data = register(new Setting("Data", Boolean.valueOf(true)));
/*    */   
/*    */   public PacketLogger() {
/* 18 */     super("PacketLogger", "", Module.Category.MISC, true, false, false);
/*    */   }
/*    */ }


/* Location:              C:\Users\KillAura\Desktop\Incoming 1.0.2 libs.jar!\me\alpha432\oyvey\features\modules\misc\PacketLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */