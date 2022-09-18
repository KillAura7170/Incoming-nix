/*    */ package me.alpha432.oyvey.features.modules.player;
/*    */ 
/*    */ import me.alpha432.oyvey.features.modules.Module;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LiquidInteract
/*    */   extends Module
/*    */ {
/* 10 */   private static LiquidInteract INSTANCE = new LiquidInteract();
/*    */   
/*    */   public LiquidInteract() {
/* 13 */     super("LiquidInteract", "li", Module.Category.PLAYER, false, false, false);
/* 14 */     setInstance();
/*    */   }
/*    */   
/*    */   public static LiquidInteract getInstance() {
/* 18 */     if (INSTANCE == null) {
/* 19 */       INSTANCE = new LiquidInteract();
/*    */     }
/* 21 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 25 */     INSTANCE = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\KillAura\Desktop\Incoming 1.0.2 libs.jar!\me\alpha432\oyvey\features\modules\player\LiquidInteract.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */