/*    */ package me.alpha432.oyvey.features.modules.module2b2t;
/*    */ 
/*    */ import me.alpha432.oyvey.features.modules.Module;
/*    */ import me.alpha432.oyvey.features.setting.Setting;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TpsSync
/*    */   extends Module
/*    */ {
/* 11 */   private static TpsSync INSTANCE = new TpsSync();
/* 12 */   public Setting<Boolean> attack = register(new Setting("Attack", Boolean.FALSE));
/* 13 */   public Setting<Boolean> mining = register(new Setting("Mine", Boolean.TRUE));
/*    */   
/*    */   public TpsSync() {
/* 16 */     super("TpsSync", "Syncs your client with the TPS.", Module.Category.MODULE2B2T, true, false, false);
/* 17 */     setInstance();
/*    */   }
/*    */   
/*    */   public static TpsSync getInstance() {
/* 21 */     if (INSTANCE == null) {
/* 22 */       INSTANCE = new TpsSync();
/*    */     }
/* 24 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 28 */     INSTANCE = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\KillAura\Desktop\Incoming 1.0.2 libs.jar!\me\alpha432\oyvey\features\modules\player\TpsSync.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */