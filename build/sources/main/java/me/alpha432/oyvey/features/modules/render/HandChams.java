/*    */ package me.alpha432.oyvey.features.modules.render;
/*    */ 
/*    */ import me.alpha432.oyvey.features.modules.Module;
/*    */ import me.alpha432.oyvey.features.setting.Setting;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HandChams
/*    */   extends Module
/*    */ {
/* 11 */   private static HandChams INSTANCE = new HandChams();
/* 12 */   public Setting<RenderMode> mode = register(new Setting("Mode", RenderMode.SOLID));
/* 13 */   public Setting<Integer> red = register(new Setting("Red", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
/* 14 */   public Setting<Integer> green = register(new Setting("Green", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255)));
/* 15 */   public Setting<Integer> blue = register(new Setting("Blue", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255)));
/* 16 */   public Setting<Integer> alpha = register(new Setting("Alpha", Integer.valueOf(240), Integer.valueOf(0), Integer.valueOf(255)));
/*    */   
/*    */   public HandChams() {
/* 19 */     super("HandChams", "Changes your hand color.", Module.Category.RENDER, false, false, false);
/* 20 */     setInstance();
/*    */   }
/*    */   
/*    */   public static HandChams getINSTANCE() {
/* 24 */     if (INSTANCE == null) {
/* 25 */       INSTANCE = new HandChams();
/*    */     }
/* 27 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 31 */     INSTANCE = this;
/*    */   }
/*    */   
/*    */   public enum RenderMode {
/* 35 */     SOLID,
/* 36 */     WIREFRAME;
/*    */   }
/*    */ }


/* Location:              C:\Users\KillAura\Desktop\Incoming 1.0.2 libs.jar!\me\alpha432\oyvey\features\modules\render\HandChams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */