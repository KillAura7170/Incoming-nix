/*    */ package me.alpha432.oyvey.features.modules.render;
/*    */ 
/*    */ import me.alpha432.oyvey.features.modules.Module;
/*    */ import me.alpha432.oyvey.features.setting.Setting;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CameraClip
/*    */   extends Module
/*    */ {
/* 11 */   private static CameraClip INSTANCE = new CameraClip();
/* 12 */   public Setting<Boolean> extend = register(new Setting("Extend", Boolean.valueOf(false)));
/* 13 */   public Setting<Double> distance = register(new Setting("Distance", Double.valueOf(10.0D), Double.valueOf(0.0D), Double.valueOf(50.0D), v -> ((Boolean)this.extend.getValue(true)).booleanValue(), "By how much you want to extend the distance."));
/*    */   
/*    */   public CameraClip() {
/* 16 */     super("CameraClip", "Makes your Camera clip.", Module.Category.RENDER, false, false, false);
/* 17 */     setInstance();
/*    */   }
/*    */   
/*    */   public static CameraClip getInstance() {
/* 21 */     if (INSTANCE == null) {
/* 22 */       INSTANCE = new CameraClip();
/*    */     }
/* 24 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 28 */     INSTANCE = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\KillAura\Desktop\Incoming 1.0.2 libs.jar!\me\alpha432\oyvey\features\modules\render\CameraClip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */