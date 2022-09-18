/*    */ package me.alpha432.oyvey.features.modules.player;
/*    */ 
/*    */ import me.alpha432.oyvey.features.modules.Module;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MultiTask
/*    */   extends Module
/*    */ {
/* 10 */   private static MultiTask INSTANCE = new MultiTask();
/*    */   
/*    */   public MultiTask() {
/* 13 */     super("MultiTask", "mt", Module.Category.PLAYER, false, false, false);
/* 14 */     setInstance();
/*    */   }
/*    */   
/*    */   public static MultiTask getInstance() {
/* 18 */     if (INSTANCE == null) {
/* 19 */       INSTANCE = new MultiTask();
/*    */     }
/* 21 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 25 */     INSTANCE = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\KillAura\Desktop\Incoming 1.0.2 libs.jar!\me\alpha432\oyvey\features\modules\player\MultiTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */