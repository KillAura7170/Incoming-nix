/*    */ package me.alpha432.oyvey.features.modules.render;
/*    */ 
/*    */ import me.alpha432.oyvey.features.modules.Module;
/*    */ import me.alpha432.oyvey.features.setting.Setting;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SmallShield
/*    */   extends Module
/*    */ {
/* 11 */   private static SmallShield INSTANCE = new SmallShield();
/* 12 */   public Setting<Float> offX = register(new Setting("OffHandX", Float.valueOf(0.0F), Float.valueOf(-1.0F), Float.valueOf(1.0F)));
/* 13 */   public Setting<Float> offY = register(new Setting("OffHandY", Float.valueOf(0.0F), Float.valueOf(-1.0F), Float.valueOf(1.0F)));
/* 14 */   public Setting<Float> mainX = register(new Setting("MainHandX", Float.valueOf(0.0F), Float.valueOf(-1.0F), Float.valueOf(1.0F)));
/* 15 */   public Setting<Float> mainY = register(new Setting("MainHandY", Float.valueOf(0.0F), Float.valueOf(-1.0F), Float.valueOf(1.0F)));
/*    */   
/*    */   public SmallShield() {
/* 18 */     super("SmallShield", "Makes you offhand lower.", Module.Category.RENDER, false, false, false);
/* 19 */     setInstance();
/*    */   }
/*    */   
/*    */   public static SmallShield getINSTANCE() {
/* 23 */     if (INSTANCE == null) {
/* 24 */       INSTANCE = new SmallShield();
/*    */     }
/* 26 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 30 */     INSTANCE = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\KillAura\Desktop\Incoming 1.0.2 libs.jar!\me\alpha432\oyvey\features\modules\render\SmallShield.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */