/*    */ package me.alpha432.oyvey.features.modules.misc;
/*    */ 
/*    */ import me.alpha432.oyvey.features.modules.Module;
/*    */ import me.alpha432.oyvey.features.setting.Setting;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoHitBox
/*    */   extends Module
/*    */ {
/* 11 */   private static NoHitBox INSTANCE = new NoHitBox();
/* 12 */   public Setting<Boolean> pickaxe = register(new Setting("Pickaxe", Boolean.valueOf(true)));
/* 13 */   public Setting<Boolean> crystal = register(new Setting("Crystal", Boolean.valueOf(true)));
/* 14 */   public Setting<Boolean> gapple = register(new Setting("Gapple", Boolean.valueOf(true)));
/*    */   
/*    */   public NoHitBox() {
/* 17 */     super("NoHitBox", "nhb", Module.Category.MISC, true, false, false);
/* 18 */     setInstance();
/*    */   }
/*    */   
/*    */   public static NoHitBox getINSTANCE() {
/* 22 */     if (INSTANCE == null) {
/* 23 */       INSTANCE = new NoHitBox();
/*    */     }
/* 25 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 29 */     INSTANCE = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\KillAura\Desktop\Incoming 1.0.2 libs.jar!\me\alpha432\oyvey\features\modules\misc\NoHitBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */